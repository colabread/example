package com.aidilude.example.mapper;

import com.aidilude.example.mapper.provider.CustomerProvider;
import com.aidilude.example.po.Customer;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CustomerMapper {

    @SelectProvider(type = CustomerProvider.class, method = "selectByCondition")
    public List<Customer> selectByCondition(Customer customer);

}