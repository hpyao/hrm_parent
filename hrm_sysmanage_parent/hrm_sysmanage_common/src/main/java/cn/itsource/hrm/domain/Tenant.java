package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.util.FileCopyUtils;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yhptest
 * @since 2019-09-02
 */
@TableName("t_tenant")
public class Tenant extends Model<Tenant> {

    private static final long serialVersionUID = 1L;

    @TableField("tenant_type")
    private Long tenantType;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String companyName;
    private String companyNum;
    private Date registerTime;
    private Boolean state;
    private String address;
    private String logo;

    //用来接收或做关联查询,在数据库中没有关联字段
    @TableField(exist = false)
    private Employee adminUser;
    @TableField(exist = false)
    List<Meal> meals = new ArrayList<>();

    public List<Map<String,Long>> getMealsMap(){
        List<Map<String,Long>> maps = new ArrayList<>();
        if (meals.size()>0){
            for (Meal meal : meals) {
                Map<String,Long> map = new HashMap<>();
                map.put("tenantId",this.getId());
                map.put("mealId",meal.getId());
                maps.add(map);
            }
        }

        return maps;
    }


    public Long getTenantType() {
        return tenantType;
    }

    public void setTenantType(Long tenantType) {
        this.tenantType = tenantType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Employee getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(Employee adminUser) {
        this.adminUser = adminUser;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Tenant{" +
        ", tenantType=" + tenantType +
        ", id=" + id +
        ", companyName=" + companyName +
        ", companyNum=" + companyNum +
        ", registerTime=" + registerTime +
        ", state=" + state +
        ", address=" + address +
        ", logo=" + logo +
        "}";
    }
}
