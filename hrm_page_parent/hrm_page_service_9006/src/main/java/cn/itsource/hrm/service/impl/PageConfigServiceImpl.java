package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.client.FastDfsClient;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.config.RabbitmqConfig;
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
import cn.itsource.hrm.util.ZipUtil_bak;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            //页面静态化
            Pager pager = pagerMapper
                    .selectList(new EntityWrapper<Pager>().eq("name", pageName)).get(0);
            String templateUrl = pager.getTemplateUrl(); //fastdfs地址
            String templateName = pager.getTemplateName();
            String courseTypes =redisClient.get("courseTypes"); //redis数据
            Response response =
                    fastDfsClient.download(templateUrl); //通过fastdfs下载压缩包
            os = new FileOutputStream("D://temp.zip");
            is = response.body().asInputStream();
            IOUtils.copy(is , os); //保存到本地
            ZipUtil.unzip("D://temp.zip","D:/temp/"); // 解压缩

            String templatePath = "D://temp//"+templateName; //模板路径
            String templatePagePath = "D://temp//"+templateName+".html"; //本地静态页面地址
            String staticRoot = "D://temp/";
            List<CourseTypeDto> courseTypeDtos = JSONArray.parseArray(courseTypes,CourseTypeDto.class);
            Map<String, Object> modelMap = new HashMap<>(); //封装两个参数作为一个对象传入进去
            modelMap.put("staticRoot", staticRoot);
            modelMap.put("courseTypes", courseTypeDtos);
            VelocityUtils.staticByTemplate(modelMap,templatePath,templatePagePath); //进行页面静态化
            String fileName = new File(templatePagePath).getName(); // 1.png
            String extName = fileName.substring(fileName.lastIndexOf(".")+1);
            String pageUrl = FastDfsApiOpr.upload(templatePagePath,extName);
            System.out.println(pageUrl);
            //PageConfig 并且进行保存
            PageConfig config = new PageConfig();
            config.setTemplateUrl(templateUrl);
            config.setTemplateName(templateName);
            config.setDataKey(dataKey);
            config.setPhysicalPath(pager.getPhysicalPath());
            config.setDfsType(0L); //0表示fastDfs
            config.setPageUrl(pageUrl);
            config.setPageId(pager.getId());
            pageConfigMapper.insert(config);
            System.out.println("jjjjjjjjjjjjjjjj");
            //往消息队列放入消息

            String routingKey = siteMapper
                    .selectList(new EntityWrapper<Site>().eq("id", pager.getSiteId())).get(0).getSn();
            System.out.println(routingKey);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileSysType",0);
            jsonObject.put("staticPageUrl",pageUrl);
            jsonObject.put("physicalPath",pager.getPhysicalPath());
            System.out.println("jjjjjjjjjjjjjjjj"+jsonObject.toJSONString());
            rabbitTemplate.convertAndSend(
                    RabbitmqConfig.EXCHANGE_DIRECT_INFORM,routingKey,jsonObject.toJSONString());
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


}
