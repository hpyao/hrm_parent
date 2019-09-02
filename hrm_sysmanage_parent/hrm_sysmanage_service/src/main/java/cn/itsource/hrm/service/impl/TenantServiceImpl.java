package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Employee;
import cn.itsource.hrm.domain.Tenant;
import cn.itsource.hrm.mapper.EmployeeMapper;
import cn.itsource.hrm.mapper.TenantMapper;
import cn.itsource.hrm.service.ITenantService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-09-02
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public boolean insert(Tenant tenant) {
        System.out.println(tenant.getId());
        tenant.setRegisterTime(new Date());
        tenant.setState(false);
        //添加机构
        tenantMapper.insert(tenant);
        System.out.println(tenant.getId());
        //添加管理员
        Employee adminUser = tenant.getAdminUser();
        adminUser.setInputTime(new Date()); //输入时间
        adminUser.setState(0); // 正常
        adminUser.setType(true); //是否是租户管理员
        adminUser.setTenantId(tenant.getId());
        employeeMapper.insert(adminUser);
        //添加套餐中间表
        tenantMapper.saveTenantMeals(tenant.getMealsMap());
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {

        //删除机构
        tenantMapper.deleteById(id);
        //删除管理员
        Wrapper<Employee> wapper = new EntityWrapper<>();
        wapper.eq("tenant_id",id);
        employeeMapper.delete(wapper);
        //删除中间表
        tenantMapper.removeTenantMeal(id);
        return true;
    }

    @Override
    public boolean updateById(Tenant tenant) {
        // 修改机构
        tenantMapper.updateById(tenant);
        //修改管理员
        employeeMapper.updateById(tenant.getAdminUser());
        //修改中间表-先删除后添加
        tenantMapper.removeTenantMeal(tenant.getId());
        tenantMapper.saveTenantMeals(tenant.getMealsMap());
        return true;
    }
}
