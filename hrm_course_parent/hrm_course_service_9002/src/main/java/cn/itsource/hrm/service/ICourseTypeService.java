package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.query.CourseTypeQuery;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author yhptest
 * @since 2019-08-31
 */
public interface ICourseTypeService extends IService<CourseType> {

    /**
     * 高级查询+分页+关联查询
     * @param query
     * @return
     */
    PageList<CourseType> selectListPage(CourseTypeQuery query);


    /**
     * 通过父亲id获取儿子,及其儿子的儿子等子子孙孙
     * @param pid
     * @return
     */
    List<CourseType> queryTypeTree(Long pid);

    /**
     *初始化课程站点主页
     */
    void InitCourseSiteIndex();
}
