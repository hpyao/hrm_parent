package cn.itsource.hrm.client;

import cn.itsource.hrm.domain.CourseDetail;
import cn.itsource.hrm.query.CourseDetailQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yaohuaipeng
 * @date 2018/10/8-16:18
 */
@Component
public class CourseDetailClientHystrixFallbackFactory implements FallbackFactory<CourseDetailClient> {

    @Override
    public CourseDetailClient create(Throwable throwable) {
        return new CourseDetailClient() {
            @Override
            public AjaxResult save(CourseDetail courseDetail) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public CourseDetail get(Long id) {
                return null;
            }

            @Override
            public List<CourseDetail> list() {
                return null;
            }

            @Override
            public PageList<CourseDetail> json(CourseDetailQuery query) {
                return null;
            }
        };
    }
}
