package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.domain.CourseDetail;
import cn.itsource.hrm.mapper.CourseDetailMapper;
import cn.itsource.hrm.mapper.CourseMapper;
import cn.itsource.hrm.query.CourseQuery;
import cn.itsource.hrm.service.ICourseService;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-09-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper mapper;
    @Autowired
    private CourseDetailMapper detailMapper;
    @Override
    public PageList<Course> selectListPage(CourseQuery query) {
        Page<Course> page = new Page<>(query.getPage(),query.getRows());
        List<Course> rows =  mapper.loadListPage(page,query);
        return new PageList<>(page.getTotal(),rows);
    }

    @Override
    public boolean insert(Course entity) {
        //课程表
        entity.setStatus(0); // tenantId tenantName userId userName
        mapper.insert(entity);
        //课程详情
        entity.getDetail().setCourseId(entity.getId());
        detailMapper.insert(entity.getDetail());
        return true;
    }
}
