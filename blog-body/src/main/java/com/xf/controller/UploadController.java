package com.xf.controller;

import com.xf.aop.MyLog;
import com.xf.utils.FtpUtil;
import com.xf.utils.QiniuUtils;
import com.xf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.synth.Region;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 图片上传/下载
 */
@RestController
@RequestMapping("upload")
@Slf4j
public class UploadController {

    @Autowired
    private FtpUtil ftpUtil;

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) throws IOException {
        /*
        1、获取上传的文件流 inputStream 以及文件名 getOriginalFilename
        2、调用 FtpUtil 中的函数，将图片上传到图片服务器并返回 https 地址
        3、若返回的是图片地址，则将其插入数据库
         */
        log.info("文件为空？：{}", file.isEmpty());
        InputStream inputStream = file.getInputStream();
        //  原始文件名
        String originalFilename = file.getOriginalFilename();
        //  新文件名：UUID.png
        String fileName = UUID.randomUUID().toString() + "." +
                StringUtils.substringAfterLast(originalFilename, ".");
        String type = file.getContentType();

        String picUrl = ftpUtil.uploadImage(inputStream, fileName, type);

        if (picUrl != null) {
            return Result.success(picUrl);
        }
        return Result.fail(20001, "上传失败！");
    }

    /*
    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        //  原始文件名
        String originalFilename = file.getOriginalFilename();
        //  新文件名：UUID.png
        String fileName = UUID.randomUUID().toString() + "." +
                StringUtils.substringAfterLast(originalFilename, ".");
        //  上传到图片服务器（七牛云）
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(20001, "上传失败！");

    }*/

}
