package com.xiaoming.rabbit_course.controller;

import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.globalException.CustomException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@Validated
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${rabbit.windowsPath}")
    private String windowsPath;
    @Value("${rabbit.linuxPath}")
    private String linuxPath;

    private final List<String> contentTypes= Arrays.asList("image/png","image/jpeg","video/mp4");
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
        String contentType = file.getContentType();
        long count = contentTypes.stream().filter(s -> s.equals(contentType)).count();
        if (count<1) throw new CustomException("不支持的文件类型");
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
        return Result.ok("上传成功",filename);
    }

    /**
     * 图片下载
     * @param name
     * @param response
     */
    @ApiOperation("加载文件")
    @GetMapping("/Download/{name}")
    public void avatarDownload(@ApiParam(value ="要加载的文件名称") @PathVariable String name, HttpServletResponse response) {
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
            Path path= Paths.get(dir + name);
            FileInputStream fileInputStream = new FileInputStream(path.toFile());
            //获取文件类型
            String mimeType= Files.probeContentType(path);
            if (StringUtils.isEmpty(mimeType)){
                throw new CustomException("加载文件失败");
            }
//            输出流，写回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            //设置类型
            response.setContentType(mimeType);
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
            throw new CustomException("加载失败");
        }
    }
}
