package com.aidilude.example.component;

import com.aidilude.example.property.SystemProperties;
import com.aidilude.example.utils.EncryptUtils;
import com.aidilude.example.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class ApplicationComponent {

    @Resource
    private SystemProperties systemProperties;

    //###########################################功能函数###########################################

    public boolean isLegalAccess(String secret, String timestamp, String key){
        if(StringUtils.isEmpty(secret) || !StringUtils.isTimestamp(timestamp)){
            return false;
        }
        long sub = new Date().getTime() - Long.valueOf(timestamp);
        if(sub > systemProperties.getAccessTimeLimit()) {
            return false;
        }
        if(!secret.equals(EncryptUtils.MD5Encode(timestamp + key))) {
            return false;
        }
        return true;
    }

}