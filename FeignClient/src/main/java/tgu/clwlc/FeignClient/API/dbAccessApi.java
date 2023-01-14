package tgu.clwlc.FeignClient.API;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import javax.validation.Valid;
import java.util.List;

@FeignClient("dbAccess")
public interface dbAccessApi {


    @PostMapping("/shop")
    public int addShop(Shop shop);

    @GetMapping("/forecast")
    public List<forecast> getForecastData(@RequestParam(value = "date") String date, @RequestParam(value = "sid") long sid);

    @PostMapping("/forecast")
    public int addForecastData(@RequestBody forecast forecast);

    @PostMapping("/user")
    public int addUser(@Valid @RequestBody User user);

}
