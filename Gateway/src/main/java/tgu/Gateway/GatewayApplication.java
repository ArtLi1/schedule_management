package tgu.Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@EnableFeignClients(basePackages = "tgu.clwlc.FeignClient.API")
@EnableWebFluxSecurity
@SpringBootApplication(scanBasePackages = {"tgu.Gateway","tgu.clwlc.FeignClient"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }

}
