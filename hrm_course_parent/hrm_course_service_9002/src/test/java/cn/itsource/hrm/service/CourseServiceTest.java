//package cn.itsource.hrm.service;
//
//import ch.qos.logback.core.net.SyslogOutputStream;
//import cn.itsource.hrm.Course9002Application;
//import cn.itsource.hrm.domain.Course;
//import cn.itsource.hrm.domain.CourseType;
//import cn.itsource.hrm.query.CourseQuery;
//import cn.itsource.hrm.query.CourseTypeQuery;
//import cn.itsource.hrm.util.PageList;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Course9002Application.class)
//public class CourseServiceTest {
//
//    @Autowired
//    private ICourseService courseService;
//    @Test
//    public void selectListPage() {
//        PageList<Course> pageList = courseService.selectListPage(new CourseQuery());
//        System.out.println(pageList.getTotal());
//        System.out.println(pageList.getRows().size());
//        for (Course course : pageList.getRows()) {
//            System.out.println(course);
//            System.out.println(course.getCourseType());
//            System.out.println("=========================");
//        }
//    }
//}