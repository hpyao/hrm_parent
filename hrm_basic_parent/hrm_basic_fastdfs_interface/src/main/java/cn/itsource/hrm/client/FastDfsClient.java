package cn.itsource.hrm.client;
import cn.itsource.hrm.util.AjaxResult;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//fallbackFactory = CourseTypeClientHystrixFallbackFactory.class
@FeignClient(value = "HRM-FASTDFS",configuration = FeignClientsConfiguration.class,
        fallbackFactory = FastDfsClientHystrixFallbackFactory.class
        )
@RequestMapping("/fastdfs")
public interface FastDfsClient {
    @RequestMapping(value="/upload",method= RequestMethod.POST)
    String upload(@RequestBody MultipartFile file);

    /**
     * 删除对象信息
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.DELETE)
    AjaxResult delete(@RequestParam("path") String path);

    //获取用户
    @RequestMapping(value = "/download",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response download(@RequestParam("path")String path); //直接把流写到response

}
