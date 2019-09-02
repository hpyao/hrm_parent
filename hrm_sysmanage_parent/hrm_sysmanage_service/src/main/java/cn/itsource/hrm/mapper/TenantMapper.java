package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Tenant;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yhptest
 * @since 2019-09-02
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 保存机构所对应的套餐的中间表信息
     * @param mealsMap
     */
    void saveTenantMeals(List<Map<String, Long>> mealsMap);

    /**
     * 删除中间表
     * @param id
     */
    void removeTenantMeal(Serializable id);
}
