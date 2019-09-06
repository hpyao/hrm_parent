package cn.itsource.hrm.service;

import cn.itsource.hrm.Course9002Application;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.query.CourseTypeQuery;
import cn.itsource.hrm.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Course9002Application.class)
public class ICourseTypeServiceTest {

    @Autowired
    private ICourseTypeService courseTypeService;
    @Test
    public void selectListPage() {
        PageList<CourseType> page = courseTypeService.selectListPage(new CourseTypeQuery());
        System.out.println(page.getTotal());
        System.out.println(page.getRows().size());
        for (CourseType courseType : page.getRows()) {
            System.out.println(courseType);
            System.out.println(courseType.getParent());
            System.out.println("========================");
        }
    }

    @Test
    public void testQueryTreeData()throws Exception{

        List<CourseType> courseTypes = courseTypeService.queryTypeTree(1037L);
        for (CourseType courseType : courseTypes) {
            System.out.println(courseType);
            List<CourseType> children = courseType.getChildren();
            if (null != children){
                for (CourseType child : children) {
                    System.out.println(child);
                }
            }
        }
    }
}