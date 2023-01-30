package tgu.clwlc.FeignClient.API;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import javax.validation.Valid;
import java.util.List;

@FeignClient("dbAccess")
public interface dbAccessApi {


    @PostMapping("/shop")
    public int addShop(Shop shop);

    @GetMapping("/shop/sid/{sid}")
    public Shop getShop(@PathVariable long sid);

    @GetMapping("/forecast/{sid}/{date}")
    public List<forecast> getForecastData(@PathVariable(value = "date") String date, @PathVariable(value = "sid") long sid);

    @PostMapping("/forecast")
    public int addForecastData(@RequestBody forecast forecast);

    @GetMapping("/user/uid/{uid}")
    public User getUser(@PathVariable long uid);

    @GetMapping("/user/sid/{sid}")
    public List<User> getUserList(@PathVariable long sid);

    @PostMapping("/user")
    public int addUser(@Valid @RequestBody User user);


    @GetMapping("/preference/{id}")
    public preferences getPreference(@PathVariable long id);

    @PostMapping("/preference")
    public int addPreference(@Valid @RequestBody preferences preferences);


    @GetMapping("/customRule/{sid}")
    public custom_rules getRules(@PathVariable long sid);


    @PutMapping("/shifts")
    public void addShifts(@RequestBody List<secureShifts> shifts);

}
