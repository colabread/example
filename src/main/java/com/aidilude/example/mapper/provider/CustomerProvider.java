package com.aidilude.example.mapper.provider;

import com.aidilude.example.po.Customer;
import com.aidilude.example.utils.StringUtils;
import org.apache.ibatis.jdbc.SQL;

public class CustomerProvider {

    public String selectByCondition(Customer customer){
        SQL sql = new SQL();

        sql.SELECT("*");
        sql.FROM("customer");
        if(customer.getId() != null)
            sql.WHERE("id = #{id}");
        if(!StringUtils.isEmpty(customer.getName()))
            sql.WHERE("name = #{name}");
        if(customer.getAge() != null)
            sql.WHERE("age = #{age}");

        return sql.toString();
    }

}
