package cn.itsource.hrm.cache;

import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.domain.CourseType;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseTypeCache {
    @Autowired
    private RedisClient redisClient;

    private static  final  String TYPETREEDATA_IN_REDIS = "typetreedata_in_redis";
    /**
     * 从redis获取数据
     * @return
     */
    public List<CourseType> getCourseTypes() {
        String redisData = redisClient.get(TYPETREEDATA_IN_REDIS);
       return JSONArray.parseArray(redisData,CourseType.class);
    }

    /**
     * 设置数据到redis
     * @param courseTypesDb
     */
    public void setCourseTypes(List<CourseType> courseTypesDb) {
        String jsonStr = JSONArray.toJSONString(courseTypesDb);
        redisClient.set(TYPETREEDATA_IN_REDIS,jsonStr);
    }
}
