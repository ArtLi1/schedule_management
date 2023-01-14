package tgu.Inform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "tgu.clwlc.FeignClient.API")
@SpringBootApplication
public class InformApplication {
    public static void main(String[] args) {
        SpringApplication.run(InformApplication.class,args);
    }
}
