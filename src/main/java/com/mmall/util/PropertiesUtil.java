package com.mmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author junjun
 * @date 2018/1/24
 **/
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties pros;

    static{
        String fileName = "mmall.properties";
        try {
            pros.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"utf-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常", e);
        }
    }

    public static String getProperty(String key){
        String value = pros.getProperty(key.trim());
        if(value == null){
            return null;
        }
        return value.trim();
    }
    public static String getProperty(String key, String defaultValue){
        String value = pros.getProperty(key.trim());
        if(value == null){
            return defaultValue;
        }
        return value.trim();
    }
}
