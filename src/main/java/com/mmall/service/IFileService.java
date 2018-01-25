package com.mmall.service;

import com.mmall.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author junjun
 * @date 2018/1/25
 **/
public interface IFileService {
    String upload(MultipartFile file, String path);
}
