package com.alibaba.nacos.example.spring.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


//FeignConfig局部配置
//@FeignClient(value = "mall-order",path = "/order",configuration = FeignConfig.class)
@FeignClient(value = "nacos-privoder", path = "/provider/order")
public interface OrderFeignService {

    @RequestMapping("/findOrderByUserId/{userId}")
    String findOrderByUserId(@PathVariable("userId") Integer userId);

}
