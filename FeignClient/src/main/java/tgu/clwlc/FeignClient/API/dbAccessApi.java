package tgu.clwlc.FeignClient.API;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;

@FeignClient("dbAccess")
public interface dbAccessApi {


    @PostMapping("/shop")
    public int addShop(Shop shop);

}
