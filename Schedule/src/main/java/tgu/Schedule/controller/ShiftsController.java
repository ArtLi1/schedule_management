package tgu.Schedule.controller;


import org.springframework.web.bind.annotation.*;
import tgu.Schedule.service.Interface.shiftsService;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;

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
