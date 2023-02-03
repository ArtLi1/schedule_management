package tgu.Schedule.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.Schedule.service.Interface.shiftsService;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {


    @Resource
    shiftsService shiftsService;

    @GetMapping("{sid}/{date}")
    public result getShifts(@PathVariable String date, @PathVariable long sid){
        return shiftsService.getShifts(date,sid);
    }

}
