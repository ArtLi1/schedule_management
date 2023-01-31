package tgu.clwlc.db_access.Controller;


import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;
import tgu.clwlc.db_access.Service.shiftsService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {


    @Resource
    shiftsService shiftsService;


    @GetMapping("/{sid}/{date}")
    public secureShifts getShifts(@PathVariable long sid,@PathVariable String date){
        return shiftsService.getShifts(sid,date);
    }

    @PutMapping
    public void addShifts(@RequestBody List<secureShifts> shifts){
      shiftsService.addShifts(shifts);
    }

    @DeleteMapping
    public void delShifts(@RequestBody long id,@RequestBody List<String> list){
        shiftsService.delShifts(id,list);
    }



}
