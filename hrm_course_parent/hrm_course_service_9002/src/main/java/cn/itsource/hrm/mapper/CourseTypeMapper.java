package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.query.CourseTypeQuery;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程目录 Mapper 接口
 * </p>
 *
 * @author yhptest
 * @since 2019-08-31
 */
public interface CourseTypeMapper extends BaseMapper<CourseType> {

    /**
     * @param page
     * @param query
     * @return
     */
   List<CourseType> loadListPage(Pagination page,
                                 @Param("query") CourseTypeQuery query);
}
