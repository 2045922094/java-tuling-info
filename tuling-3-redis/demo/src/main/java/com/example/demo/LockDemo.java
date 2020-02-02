package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 单士隆 2020/2/1.
 * @date 2020/2/1 13:08
 */
@RestController
public class LockDemo {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * java 自带锁 集群环境测试
     * @return
     */
    @RequestMapping("/xiadan1")
    public String xiadan1() {
        synchronized (this){
            int stockNum = Integer.parseInt(stringRedisTemplate.opsForValue().get("stockNum"));
            if (stockNum > 0) {
                int newStock = stockNum - 1;
                int num = 256;
                stringRedisTemplate.opsForValue().set("stockNum", newStock + "");
                System.out.println("下单成功，剩余库存:" + newStock + "");
            } else {
                System.out.println("下单失败，库存不足");
            }
        }
        return "end";
    }
}
