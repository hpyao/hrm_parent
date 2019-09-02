package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.query.CourseTypeQuery;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.service.IService;

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
}
