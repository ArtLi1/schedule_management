package tgu.Transaction.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgu.Transaction.dao.AskforleaveMapper;
import tgu.Transaction.pojo.Askforleave;
import tgu.Transaction.service.AskforleaveService;
import tgu.clwlc.FeignClient.pojo.result;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Transaction")
public class AskforleaveController {

    @Autowired
    private AskforleaveMapper askforleaveMapper;

    @Autowired
    private AskforleaveService askforleaveService;

    @PostMapping("/askforleaving")
    public result applyforLeaving(@RequestBody Askforleave askforleave){
            log.info(askforleave.toString());
            askforleaveMapper.insert(askforleave);
        return result.success("申请事务成功");
    }

    @GetMapping("/checkInfo/{id}")
    public result checkSingleInfo(@PathVariable int id){
        Askforleave askforleave = askforleaveMapper.selectById(id);
        if(askforleave == null){
            return result.fail("无申请记录");
        }

        return result.success(askforleave);
    }

    @GetMapping("/checkAllInfo")
    public result checkAllInfo(){
        List<Askforleave> askforleaves = askforleaveMapper.selectList(null);

        if(askforleaves == null){
            return result.fail("无申请记录");
        }

        return result.success(askforleaves);
    }

}
