package cn.itsource.hrm.handler;

import cn.itsource.hrm.client.FastDfsClient;
import cn.itsource.hrm.config.RabbitmqConfig;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class StaticPageDownloadHandler {

    @Autowired
    private FastDfsClient fastDfsClient;

    @RabbitListener(queues = RabbitmqConfig.QUEUE_INFORM_PAGESTATIC)
    public void receiveHomeSite(String msg, Message message, Channel channel) {
        //msg -fileSysType,staticPageUrl,physicalPath
        JSONObject jsonObject = JSONObject.parseObject(msg);
        Integer fileSysType = jsonObject.getInteger("fileSysType");
        String staticPageUrl = jsonObject.getString("staticPageUrl");
        String physicalPath = jsonObject.getString("physicalPath");

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
        //@TODO 测试
//        HttpServletResponse response = fastDfsClient.download(staticPageUrl);
//        try {
//            ServletOutputStream os = response.getOutputStream();
//            System.out.println(os);
//            IOUtils.copy(new FileInputStream(new File(physicalPath)),os);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
