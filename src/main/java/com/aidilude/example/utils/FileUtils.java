package com.aidilude.example.utils;

import com.aidilude.example.listener.PutObjectProgressListener;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

    //###########################################配置项###########################################

    private static final String fileTypeLimit = "image/jpeg|image/png";

    private static final String fileSizeLimit = "512000";   //500KB

    private static final String endpoint = "http://oss-cn-hongkong.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static final String accessKeyId = "LTAICsj0vpoXP8EW";

    private static final String accessKeySecret = "CPkRUeUe191Nc3dBPR0oCwSQaWa4PI";

    private static final String bucketName = "newappaz";

    //###########################################验证函数###########################################

    public static boolean isEmpty(MultipartFile multipartFile){
        if(multipartFile == null)
            return true;
        if(multipartFile.isEmpty())
            return true;
        return false;
    }

    public static boolean isCorrectFileType(MultipartFile multipartFile){
        if(!fileTypeLimit.contains(multipartFile.getContentType()))
            return false;
        return true;
    }

    public static boolean isCorrectFileSize(MultipartFile multipartFile){
        if(multipartFile.getSize() > Long.valueOf(fileSizeLimit))
            return false;
        return true;
    }

    //###########################################功能函数###########################################

    /**
     * 获取文件的后缀名
     * @param multipartFile
     * @return
     */
    public static String getSuffix(MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 生成随机的文件名(uuid)
     * @param multipartFile
     * @return
     */
    public static String createRandomFileName(MultipartFile multipartFile){
        return StringUtils.createUUID() + "." + getSuffix(multipartFile);
    }

    /**
     * 上传文件
     * @param multipartFile
     * @param path 路径
     * @return
     * @throws IOException
     */
    public static String upload(MultipartFile multipartFile, String path) throws IOException {
        String fileName = createRandomFileName(multipartFile);
        File file = new File(path, fileName);
//        if(!file.getParentFile().exists()){
//            file.getParentFile().mkdir();
//        }
        multipartFile.transferTo(file);
        return fileName;
    }

    /**
     * 获取图片的尺寸（长、宽）
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static Map<String, Object> getImgSize(MultipartFile multipartFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        Map<String, Object> result = new HashMap<>();
        result.put("width", bufferedImage.getWidth());
        result.put("height", bufferedImage.getHeight());
        return result;
    }

    /**
     * 返回图片流
     * @param bufferedImage
     * @param suffix
     * @param outputStream
     * @throws IOException
     */
    public static void writeImg(BufferedImage bufferedImage, String suffix, OutputStream outputStream) throws IOException {
        ImageIO.write(bufferedImage, suffix, outputStream);
        outputStream.close();
    }

    public static void ossUploadFile(MultipartFile multipartFile) throws IOException {
        String objectName = "test/" + multipartFile.getOriginalFilename();
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        //不带进度条的上传
//        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(multipartFile.getBytes()));

        try {
            // 带进度条的上传
            ossClient.putObject(new PutObjectRequest(bucketName, objectName, multipartFile.getInputStream()).<PutObjectRequest>withProgressListener(new PutObjectProgressListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void ossWriteFile(HttpServletResponse response) throws IOException {
        String objectName = "test/timg.jpg";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);

        // 读取文件内容。
        InputStream inputStream = ossObject.getObjectContent();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);
        outputStream.close();
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void ossDeleteFile(String objectName){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 删除文件
        ossClient.deleteObject(bucketName, objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void main(String[] args) {

    }

}
