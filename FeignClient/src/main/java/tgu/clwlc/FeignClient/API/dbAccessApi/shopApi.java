package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;

@FeignClient(name = "dbAccess",contextId = "shop")
public interface shopApi {
    @PostMapping("/shop")
    int addShop(Shop shop);

    @GetMapping("/shop/sid/{sid}")
    Shop getShop(@PathVariable long sid);
}
