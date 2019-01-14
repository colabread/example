package com.aidilude.example.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss")
@PropertySource("classpath:property/oss.properties")
public class OSSProperties {

    private String endpoint;

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    private String rootFolder;

    private String cusHeadFolder;

    private String postFolder;

    private String commentFolder;

    private String commonFolder;

    private String typeLimit;

    private Long headSizeLimit;

    private Long postSizeLimit;

    private Long commonSizeLimit;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public String getCusHeadFolder() {
        return cusHeadFolder;
    }

    public void setCusHeadFolder(String cusHeadFolder) {
        this.cusHeadFolder = cusHeadFolder;
    }

    public String getPostFolder() {
        return postFolder;
    }

    public void setPostFolder(String postFolder) {
        this.postFolder = postFolder;
    }

    public String getCommentFolder() {
        return commentFolder;
    }

    public void setCommentFolder(String commentFolder) {
        this.commentFolder = commentFolder;
    }

    public String getCommonFolder() {
        return commonFolder;
    }

    public void setCommonFolder(String commonFolder) {
        this.commonFolder = commonFolder;
    }

    public String getTypeLimit() {
        return typeLimit;
    }

    public void setTypeLimit(String typeLimit) {
        this.typeLimit = typeLimit;
    }

    public Long getHeadSizeLimit() {
        return headSizeLimit;
    }

    public void setHeadSizeLimit(Long headSizeLimit) {
        this.headSizeLimit = headSizeLimit;
    }

    public Long getPostSizeLimit() {
        return postSizeLimit;
    }

    public void setPostSizeLimit(Long postSizeLimit) {
        this.postSizeLimit = postSizeLimit;
    }

    public Long getCommonSizeLimit() {
        return commonSizeLimit;
    }

    public void setCommonSizeLimit(Long commonSizeLimit) {
        this.commonSizeLimit = commonSizeLimit;
    }
}