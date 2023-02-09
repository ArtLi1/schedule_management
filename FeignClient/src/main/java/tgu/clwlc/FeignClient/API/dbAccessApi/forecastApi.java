package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;

import java.util.List;

@FeignClient(name = "dbAccess",contextId = "forecast")
public interface forecastApi {

    @GetMapping("/forecast/{sid}/{date}")
    List<forecast> getForecastData(@PathVariable(value = "date") String date, @PathVariable(value = "sid") long sid);

    @PostMapping("/forecast")
    int addForecastData(@RequestBody forecast forecast);

}
