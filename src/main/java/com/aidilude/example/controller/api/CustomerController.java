package com.aidilude.example.controller.api;

import com.aidilude.example.mapper.CustomerMapper;
import com.aidilude.example.po.Customer;
import com.aidilude.example.utils.FileUtils;
import com.aidilude.example.utils.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Resource
    private CustomerMapper customerMapper;

    @GetMapping("/queryCustomer")
    @ApiOperation(value = "查询用户", notes = "", response = Result.class)
    public Result queryCustomer(@ApiParam(name = "id", value = "用户id", required = false) @RequestParam(required = false) Integer id,
                                @ApiParam(name = "name", value = "用户姓名", required = false) @RequestParam(required = false) String name,
                                @ApiParam(name = "age", value = "用户年龄", required = false) @RequestParam(required = false) Integer age,
                                @ApiParam(name = "pageCount", value = "页数", required = true) @RequestParam Integer pageCount){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setAge(age);
        Integer pageSize = 10;
        Page<?> page = PageHelper.startPage(pageCount, pageSize);
        List<Customer> customers = customerMapper.selectByCondition(customer);
        return Result.returnPagingData(customers, Integer.valueOf(String.valueOf(page.getTotal())), pageSize, pageCount);
    }

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件", notes = "", response = Result.class)
    public Result uploadFile(@ApiParam(name = "multipartFile", value = "待上传的文件", required = true) @RequestParam MultipartFile multipartFile) throws IOException {
        FileUtils.ossUploadFile(multipartFile);
        return Result.ok("上传成功.");
    }

    @GetMapping("/downloadFile")
    @ApiOperation(value = "下载文件", notes = "", response = Result.class)
    public void downloadFile(HttpServletResponse response) throws IOException {
        FileUtils.ossWriteFile(response);
    }

    @DeleteMapping("/deleteFile")
    @ApiOperation(value = "删除文件", notes = "", response = Result.class)
    public Result deleteFile(){
        FileUtils.ossDeleteFile("test/timg.jpg");
        return Result.ok("删除成功.");
    }

}
