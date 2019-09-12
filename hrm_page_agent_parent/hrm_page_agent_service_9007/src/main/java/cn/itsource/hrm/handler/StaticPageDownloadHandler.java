package cn.itsource.hrm.handler;

import cn.itsource.hrm.client.FastDfsClient;
import cn.itsource.hrm.config.RabbitmqConstants;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Component
public class StaticPageDownloadHandler {

    @Autowired
    private FastDfsClient fastDfsClient;

    @RabbitListener(queues = RabbitmqConstants.QUEUE_INFORM_PAGESTATIC)
    public void receiveHomeSite(String msg, Message message, Channel channel) {
        //msg -fileSysType,staticPageUrl,physicalPath
        JSONObject jsonObject = JSONObject.parseObject(msg);
        Integer fileSysType = jsonObject.getInteger("fileSysType");
        String staticPageUrl = jsonObject.getString("staticPageUrl");
        String physicalPath = jsonObject.getString("physicalPath");
        System.out.println(staticPageUrl);
        System.out.println(physicalPath);

        switch (fileSysType) {
            case 0: //fastdfs
                fastDfsDownloadAndCopy(staticPageUrl, physicalPath);
                break;
            case 1: //hdfs
                hdfsDownloadAndCopy(staticPageUrl, physicalPath);
                break;
            default:
                break;
        }
    }
    /**
     * 通过fastdfs下载文件,并且拷贝到特定的目录
     * @param staticPageUrl
     * @param physicalPath
     */
    private void hdfsDownloadAndCopy(String staticPageUrl, String physicalPath) {

        //@TODO
    }

    /**
     * 通过fastdfs下载文件,并且拷贝到特定的目录
     * @param staticPageUrl
     * @param physicalPath
     */
    private void fastDfsDownloadAndCopy(String staticPageUrl, String physicalPath) {
        Response response = fastDfsClient.download(staticPageUrl);
        InputStream is = null;
        OutputStream os = null;
        try {
            is  = response.body().asInputStream();
            os = new FileOutputStream(physicalPath);
            IOUtils.copy(is,os);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
