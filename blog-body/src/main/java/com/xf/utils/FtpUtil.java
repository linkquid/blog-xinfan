package com.xf.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author Max
 */
@Component
@PropertySource("classpath:ftpResource.properties")
@Slf4j
public class FtpUtil {
    /**
     * FTP_ADDRESS: ftp 服务器ip地址
     * FTP_PORT: ftp 服务器port，默认是21
     * FTP_USERNAME: ftp 服务器用户名
     * FTP_PASSWORD: ftp 服务器密码
     * FTP_BASE_PATH: ftp 服务器存储图片的绝对路径
     * IMAGE_BASE_URL: ftp 服务器外网访问图片路径
     */
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    /**
     * 上传图片
     * @param inputStream 输入流
     * @param name 文件名
     * @return 图片 url
     * @throws IOException IO异常
     */

    public String uploadImage(InputStream inputStream, String name, String type) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            System.out.println(FTP_ADDRESS);
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);

//            ftpClient.setControlEncoding(type);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(FTP_BASE_PATH);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            log.info("存储图片：{}", name);
            boolean isSucceed = ftpClient.storeFile(name, inputStream);
            if (isSucceed){
                log.info("图片上传成功！");
                return IMAGE_BASE_URL + name;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ftpClient.logout();
        }
        return IMAGE_BASE_URL + "error";
    }
}
