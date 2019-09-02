package cn.itsource.hrm.client;

import cn.itsource.hrm.domain.Employee;
import cn.itsource.hrm.query.EmployeeQuery;
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
public class EmployeeClientHystrixFallbackFactory implements FallbackFactory<EmployeeClient> {

    @Override
    public EmployeeClient create(Throwable throwable) {
        return new EmployeeClient() {
            @Override
            public AjaxResult save(Employee employee) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public Employee get(Long id) {
                return null;
            }

            @Override
            public List<Employee> list() {
                return null;
            }

            @Override
            public PageList<Employee> json(EmployeeQuery query) {
                return null;
            }
        };
    }
}
