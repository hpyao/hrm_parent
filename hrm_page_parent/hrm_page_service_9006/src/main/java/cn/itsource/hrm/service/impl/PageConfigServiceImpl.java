package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.client.FastDfsClient;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.config.RabbitmqConstants;
import cn.itsource.hrm.domain.PageConfig;
import cn.itsource.hrm.domain.Pager;
import cn.itsource.hrm.domain.Site;
import cn.itsource.hrm.dto.CourseTypeDto;
import cn.itsource.hrm.mapper.PageConfigMapper;
import cn.itsource.hrm.mapper.PagerMapper;
import cn.itsource.hrm.mapper.SiteMapper;
import cn.itsource.hrm.service.IPageConfigService;
import cn.itsource.hrm.util.FastDfsApiOpr;
import cn.itsource.hrm.util.VelocityUtils;
import cn.itsource.hrm.util.ZipUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import feign.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-09-08
 */
@Service
public class PageConfigServiceImpl extends ServiceImpl<PageConfigMapper, PageConfig> implements IPageConfigService {

    @Autowired
    private PagerMapper pagerMapper;
    @Autowired
    private PageConfigMapper pageConfigMapper;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private FastDfsClient fastDfsClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SiteMapper siteMapper;

    @Override
    public void staticPage(String dataKey, String pageName) {
        FileOutputStream os = null;
        InputStream is = null;
        try {
            //一 页面静态化
            Pager pager = pagerMapper
                    .selectList(new EntityWrapper<Pager>().eq("name", pageName)).get(0);
            String templateUrl = pager.getTemplateUrl(); //fastdfs地址 zip包的
            String templateName = pager.getTemplateName(); //要执行模板文件

            //1.1 下载fastdfs上面压缩包
            Response response =
                    fastDfsClient.download(templateUrl); //通过fastdfs下载压缩包
            is = response.body().asInputStream();

            //1.2 所有静态化中间数据都写入临时目录
            //1)跨操作系统
            //2 操作系统会自动维护,不用删除
            String tmpdir=System.getProperty("java.io.tmpdir");
            System.out.println(tmpdir+"jjjjj.........");
            String zipPath = tmpdir+"/temp.zip"; //要下载zip包路径
            String unZipPath = tmpdir + "/temp/"; //解压到路径
            os = new FileOutputStream(zipPath);
            IOUtils.copy(is , os); //保存到本地

            ZipUtil.unzip(zipPath,unZipPath); // 解压缩

            //1.3 获取到模板
            String templatePath = unZipPath+"/"+templateName; //模板路径 temp/home.vm
            System.out.println(templatePath+"zz.........");
            //2 生成静态页面的路劲
            String templatePagePath = templatePath+".html"; //本地静态页面地址
            System.out.println(templatePagePath+"xxx.........");



            //3 生成数据
            String courseTypes =redisClient.get("courseTypes"); //redis数据
            List<CourseTypeDto> courseTypeDtos = JSONArray.parseArray(courseTypes,CourseTypeDto.class);
            Map<String, Object> modelMap = new HashMap<>(); //封装两个参数作为一个对象传入进去
            modelMap.put("staticRoot", unZipPath);
            modelMap.put("courseTypes", courseTypeDtos);


            //4 做页面静态化
            VelocityUtils.staticByTemplate(modelMap,templatePath,templatePagePath); //进行页面静态化

            // 5 传递到fastdfs
            String pageUrl = fastDfsClient.upload(
                    new CommonsMultipartFile(createFileItem(new File(templatePagePath),"file")));
            //二 PageConfig 并且进行保存
            PageConfig config = new PageConfig();
            config.setTemplateUrl(templateUrl);
            config.setTemplateName(templateName);
            config.setDataKey(dataKey);
            config.setPhysicalPath(pager.getPhysicalPath());
            config.setDfsType(0L); //0表示fastDfs
            config.setPageUrl(pageUrl);
            config.setPageId(pager.getId());
            pageConfigMapper.insert(config);
            //三 往消息队列放入消息
            String routingKey = siteMapper
                    .selectList(new EntityWrapper<Site>().eq("id", pager.getSiteId())).get(0).getSn();
            System.out.println(routingKey+"ddh......");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileSysType",0);
            jsonObject.put("staticPageUrl",pageUrl);
            jsonObject.put("physicalPath",pager.getPhysicalPath());
            System.out.println(jsonObject.toJSONString()+"dbl.....");
            rabbitTemplate.convertAndSend(
                    RabbitmqConstants.EXCHANGE_DIRECT_INFORM,routingKey,jsonObject.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
创建FileItem
 */
    private FileItem createFileItem(File file,String filedName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(filedName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }


}
