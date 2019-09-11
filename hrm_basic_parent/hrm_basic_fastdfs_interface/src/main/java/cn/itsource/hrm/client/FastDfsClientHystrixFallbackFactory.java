package cn.itsource.hrm.client;

import cn.itsource.hrm.util.AjaxResult;
import feign.Response;
import feign.hystrix.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Component
public class FastDfsClientHystrixFallbackFactory implements FallbackFactory<FastDfsClient> {
    @Override
    public FastDfsClient create(Throwable throwable) {
        return new FastDfsClient() {
            @Override
            public String upload(MultipartFile file) {
                return null;
            }

            @Override
            public AjaxResult delete(String path) {
                return null;
            }

            @Override
            public Response download(String path) {

                return null;
            }
        };
    }
}
