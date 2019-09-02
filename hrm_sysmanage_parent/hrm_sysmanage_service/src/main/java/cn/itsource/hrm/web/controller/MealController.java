package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.IMealService;
import cn.itsource.hrm.domain.Meal;
import cn.itsource.hrm.query.MealQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meal")
public class MealController {
    @Autowired
    public IMealService mealService;

    /**
    * 保存和修改公用的
    * @param meal  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Meal meal){
        try {
            if(meal.getId()!=null){
                mealService.updateById(meal);
            }else{
                mealService.insert(meal);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            mealService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Meal get(@PathVariable("id")Long id)
    {
        return mealService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Meal> list(){

        return mealService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Meal> json(@RequestBody MealQuery query)
    {
        Page<Meal> page = new Page<Meal>(query.getPage(),query.getRows());
            page = mealService.selectPage(page);
            return new PageList<Meal>(page.getTotal(),page.getRecords());
    }
}
