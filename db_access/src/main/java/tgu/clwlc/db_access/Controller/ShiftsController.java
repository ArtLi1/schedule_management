package tgu.clwlc.db_access.Controller;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {

    @Resource
    MongoTemplate mongoTemplate;



}
