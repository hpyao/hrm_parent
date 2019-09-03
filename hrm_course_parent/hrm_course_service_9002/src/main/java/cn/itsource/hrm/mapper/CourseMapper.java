package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.query.CourseQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yhptest
 * @since 2019-09-03
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> loadListPage(Page<Course> page, @Param("query") CourseQuery query);
}
