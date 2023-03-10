package tgu.clwlc.db_access.Controller;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.util.DateUtils;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/forecast")
public class ForecastController {
    @Resource
    MongoTemplate mongoTemplate;

    @GetMapping("/{sid}/{date}")
    public List<forecast> getForecastData(@PathVariable(value = "date") String date, @PathVariable(value = "sid") long sid){
        List<forecast> forecasts = mongoTemplate.find(
                new Query(Criteria.where("date").is(date).and("sid").is(sid)), forecast.class);
        return forecasts;
    }

    @PostMapping
    public int addData(@RequestBody forecast forecast){
        mongoTemplate.insert(forecast);
        return 0;
    }

}
