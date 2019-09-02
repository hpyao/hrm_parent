package cn.itsource.hrm.client;

import cn.itsource.hrm.domain.Role;
import cn.itsource.hrm.query.RoleQuery;
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
public class RoleClientHystrixFallbackFactory implements FallbackFactory<RoleClient> {

    @Override
    public RoleClient create(Throwable throwable) {
        return new RoleClient() {
            @Override
            public AjaxResult save(Role role) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public Role get(Long id) {
                return null;
            }

            @Override
            public List<Role> list() {
                return null;
            }

            @Override
            public PageList<Role> json(RoleQuery query) {
                return null;
            }
        };
    }
}
