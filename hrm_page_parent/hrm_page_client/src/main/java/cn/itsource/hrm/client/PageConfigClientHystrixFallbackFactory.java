package cn.itsource.hrm.client;

import cn.itsource.hrm.domain.PageConfig;
import cn.itsource.hrm.query.PageConfigQuery;
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
public class PageConfigClientHystrixFallbackFactory implements FallbackFactory<PageConfigClient> {

    @Override
    public PageConfigClient create(Throwable throwable) {
        return new PageConfigClient() {
            @Override
            public AjaxResult save(PageConfig pageConfig) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public PageConfig get(Long id) {
                return null;
            }

            @Override
            public List<PageConfig> list() {
                return null;
            }

            @Override
            public PageList<PageConfig> json(PageConfigQuery query) {
                return null;
            }
        };
    }
}
