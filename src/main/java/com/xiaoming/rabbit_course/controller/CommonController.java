package com.xiaoming.rabbit_course.controller;

import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.globalException.CustomException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${rabbit.windowsPath}")
    private String windowsPath;
    @Value("${rabbit.linuxPath}")
    private String linuxPath;

    /**
     * 头像上传
     *
     * @param file
     * @return
     */
    @ApiOperation("上传文件 成功会返回文件名")
    @PostMapping("/Upload")
    public Result<String> avatarUpload(@ApiParam("要上传的文件") MultipartFile file) {
        if(file.isEmpty()){
            throw new CustomException("上传的文件为空");
        }
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件格式
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名,防止文件名称重复造成文件覆盖
        String filename = UUID.randomUUID().toString()+substring;

        File dir = null;
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("windows")) {
            dir = new File(windowsPath);
        } else {
            dir = new File(linuxPath);
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(dir + filename));
        } catch (IOException e) {
            throw new CustomException("上传失败");
        }
        return Result.ok(filename);
    }

    /**
     * 图片下载
     * @param name
     * @param response
     */
    @ApiOperation("加载图片")
    @GetMapping("/avatarDownload")
    public void avatarDownload(@ApiParam("要加载的图片名称") String name, HttpServletResponse response) {
        try {
            //输入流读取文件
            File dir = null;
            String os = System.getProperty("os.name");
            if (os != null && os.toLowerCase().startsWith("windows")) {
                dir = new File(windowsPath);
            } else {
                dir = new File(linuxPath);
            }
            //输入流读取文件
            FileInputStream fileInputStream = new FileInputStream(dir + name);

//            输出流，写回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("img/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();

//        关闭资源
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
