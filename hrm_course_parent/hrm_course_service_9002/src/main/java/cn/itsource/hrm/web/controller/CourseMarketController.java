package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.ICourseMarketService;
import cn.itsource.hrm.domain.CourseMarket;
import cn.itsource.hrm.query.CourseMarketQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseMarket")
public class CourseMarketController {
    @Autowired
    public ICourseMarketService courseMarketService;

    /**
    * 保存和修改公用的
    * @param courseMarket  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseMarket courseMarket){
        try {
            if(courseMarket.getId()!=null){
                courseMarketService.updateById(courseMarket);
            }else{
                courseMarketService.insert(courseMarket);
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
            courseMarketService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CourseMarket get(@PathVariable("id")Long id)
    {
        return courseMarketService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<CourseMarket> list(){

        return courseMarketService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<CourseMarket> json(@RequestBody CourseMarketQuery query)
    {
        Page<CourseMarket> page = new Page<CourseMarket>(query.getPage(),query.getRows());
            page = courseMarketService.selectPage(page);
            return new PageList<CourseMarket>(page.getTotal(),page.getRecords());
    }
}
