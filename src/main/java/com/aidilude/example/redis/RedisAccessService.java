package com.aidilude.example.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RedisAccessService {

//    @Cacheable(value = "first_cache", keyGenerator = "keyGenerator")
//    public Book selectBookById(Integer bookId){
//        System.out.println("access database ......");
//        Book book = new Book();
//        book.setId(bookId);
//        book.setName("丰乳肥臀");
//        book.setAuthor("新华社");
//        book.setBirth(new Date());
//        return book;
//    }
//
//    @Cacheable(value = "first_cache", keyGenerator = "keyGenerator")
//    public User selectUserById(Integer userId){
//        System.out.println("access database ......");
//        User user = new User();
//		user.setId(userId);
//		user.setAge(12);
//		user.setName("fucker");
//        return user;
//    }

}
