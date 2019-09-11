package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.cache.CourseTypeCache;
import cn.itsource.hrm.client.PageConfigClient;
import cn.itsource.hrm.client.PagerClient;
//import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.domain.PageConfig;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.query.CourseTypeQuery;
import cn.itsource.hrm.service.ICourseTypeService;
import cn.itsource.hrm.util.PageList;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.internal.util.unsafe.MpmcArrayQueue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sun.security.krb5.Confounder.longValue;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-08-31
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Autowired
    private CourseTypeCache courseTypeCache;

    @Autowired
    private RedisClient redisClient;

    @Override
    public PageList<CourseType> selectListPage(CourseTypeQuery query) {
        Page page = new Page(query.getPage(),query.getRows()); //Page
        List<CourseType> courseTypes = courseTypeMapper.loadListPage(page, query);
        return new PageList<>(page.getTotal(),courseTypes);
    }

    @Override
    public List<CourseType> queryTypeTree(Long pid) { // -1

        List<CourseType> courseTypes = courseTypeCache.getCourseTypes();
        if (courseTypes== null || courseTypes.size()<1){
            System.out.println("db...............");
            //递归
            // return getCourseTypesRecursion(pid);
            //循环
            List<CourseType> courseTypesDb = getCourseTypesLoop(pid);
            if (courseTypesDb==null|| courseTypesDb.size()<1)
                courseTypesDb = new ArrayList<>();
            courseTypeCache.setCourseTypes(courseTypesDb);//[]
            return courseTypesDb;
        }

        System.out.println("cache...............");
        return  courseTypes;

    }

    @Autowired
    private PageConfigClient pageConfigClient;
    @Override
    public void InitCourseSiteIndex() {
        //1 准备模板,并且上传fastdfs
        //2存放数据到redis
        List<CourseType> courseTypes = queryTypeTree(0L);
        String dataKey = "courseTypes";
        redisClient.set(dataKey, JSONArray.toJSONString(courseTypes));
        //3调用静态化接口产生静态页面,并且放入fastdfs
        PageConfig pageConfig = new PageConfig();
        String pageName = "CourseIndex";
        //本来应该通过PageName获取page后设置pageconfig传递,由于数据在查询端,还不如直接传入pageName到那边查询.
        Map<String,String> map = new HashMap<>();
        map.put("dataKey",dataKey);
        map.put("pageName",pageName);
        pageConfigClient.staticPage(map);
        //4往消息队列放一个消息,让pageAgent来下载静态页面
    }


    /**
     * 方案2:循环方案:一次sql
     * @param pid 0
     * @return
     */
    private List<CourseType> getCourseTypesLoop(Long pid) { //0
        List<CourseType> result = new ArrayList<>();
        //1 查询所有类型
        List<CourseType> allTypes = courseTypeMapper.selectList(null);

        //建立id和CourseType的关联关系
        Map<Long,CourseType> allTypesDto = new HashMap<>();
        for (CourseType allType : allTypes) {
            allTypesDto.put(allType.getId(),allType);
        }
        //2 遍历判断是否是第一级  pid为传入id,
        for (CourseType type : allTypes) {
            Long pidTmp = type.getPid();
            //2.1是直接加入返回列表
            if (pidTmp.longValue()== pid.longValue()){
                result.add(type);
            }else{
                //2.2不是要把自己作为父亲儿子
                    //通过pid获取父亲
                    //方案1:遍历所有,通过父亲id来获取父亲
                    /*
                    for (CourseType courseType : allTypes) {
                        if(courseType.getId().longValue() == pidTmp.longValue()){
                            courseType.getChildren().add(type);
                        }

                    }*/
                    //方案2:通过map获取
                    CourseType parent = allTypesDto.get(pidTmp);
                    //获取父亲儿子集合,把自己加进去
                    parent.getChildren().add(type);
            }
        }

        return result;
    }

    /**
     * 方案1:递归,每次都要发送sql效率低下
     * @param pid
     * @return
     */
    private List<CourseType> getCourseTypesRecursion(Long pid) {
        //方案1:递归-自己调用自己,要有出口
        List<CourseType> children = courseTypeMapper
                .selectList(new EntityWrapper<CourseType>().eq("pid", pid));
        //要有出口
        if (children==null || children.size()<1)
        {
            return null;
        }
        for (CourseType child : children) {
            //自己调用自己
            List<CourseType> courseTypes = queryTypeTree(child.getId());
            child.setChildren(courseTypes);
        }
        return children;
    }

    @Override
    public boolean insert(CourseType entity) {
        courseTypeMapper.insert(entity);
        List<CourseType> courseTypes = queryTypeTree(0L);
        courseTypeCache.setCourseTypes(courseTypes);
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        courseTypeMapper.deleteById(id);
        List<CourseType> courseTypes = queryTypeTree(0L);
        courseTypeCache.setCourseTypes(courseTypes);
        return true;
    }

    @Override
    public boolean updateById(CourseType entity) {
        courseTypeMapper.updateById(entity);
        List<CourseType> courseTypes = queryTypeTree(0L);
        courseTypeCache.setCourseTypes(courseTypes);
        return true;
    }
}
