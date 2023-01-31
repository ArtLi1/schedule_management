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
import java.util.Date;
import java.util.List;

@FeignClient("dbAccess")
public interface dbAccessApi {


    @PostMapping("/shop")
    int addShop(Shop shop);

    @GetMapping("/shop/sid/{sid}")
    Shop getShop(@PathVariable long sid);

    @GetMapping("/forecast/{sid}/{date}")
    List<forecast> getForecastData(@PathVariable(value = "date") String date, @PathVariable(value = "sid") long sid);

    @PostMapping("/forecast")
    int addForecastData(@RequestBody forecast forecast);

    @GetMapping("/user/uid/{uid}")
    User getUser(@PathVariable long uid);

    @GetMapping("/user/sid/{sid}")
    List<User> getUserList(@PathVariable long sid);

    @PostMapping("/user")
    int addUser(@Valid @RequestBody User user);


    @GetMapping("/preference/{id}")
    preferences getPreference(@PathVariable long id);

    @PostMapping("/preference")
    int addPreference(@Valid @RequestBody preferences preferences);


    @GetMapping("/customRule/{sid}")
    custom_rules getRules(@PathVariable long sid);


    @PutMapping("/shifts")
    void addShifts(@RequestBody List<secureShifts> shifts);

    @DeleteMapping("/shifts")
    void delShifts(@RequestParam long id,@RequestBody List<String> list);

    @GetMapping("/shifts/{sid}/{date}")
    secureShifts getShifts(@PathVariable long sid, @PathVariable String date);


}
