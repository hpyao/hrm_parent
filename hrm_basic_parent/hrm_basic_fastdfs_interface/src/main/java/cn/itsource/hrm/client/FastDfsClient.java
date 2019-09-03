package cn.itsource.hrm.client;
import cn.itsource.hrm.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//fallbackFactory = CourseTypeClientHystrixFallbackFactory.class
@FeignClient(value = "ZUUL-GATEWAY",configuration = FeignClientsConfiguration.class,
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
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    void download(@RequestParam("path")String path); //直接把流写到response

}
