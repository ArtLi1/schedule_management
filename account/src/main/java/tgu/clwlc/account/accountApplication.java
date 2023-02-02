package tgu.clwlc.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "tgu.clwlc.FeignClient.API")
@SpringBootApplication
public class accountApplication {
    public static void main(String[] args) {
        SpringApplication.run(accountApplication.class,args);
    }
}
