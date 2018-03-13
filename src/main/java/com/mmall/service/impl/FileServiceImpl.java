package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author junjun
 * @date 2018/1/25
 **/
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //截取拓展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //不写toString也行
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            //设置写权限
            fileDir.setWritable(true);
            //设置新建路径（含父类路径）
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);

        try {
            //文件上传成功
            file.transferTo(targetFile);
            //文件上传到ftp服务器成功
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }

        return targetFile.getName();
    }
}
