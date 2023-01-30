package tgu.clwlc.db_access.Controller;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.shifts;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {

    @Resource
    MongoTemplate mongoTemplate;

    @PutMapping
    public void addShifts(@RequestBody List<secureShifts> shifts){
        for (secureShifts shift : shifts) {
            mongoTemplate.insert(shift);
        }
    }



}
