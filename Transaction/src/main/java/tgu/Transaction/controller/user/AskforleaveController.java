package tgu.Transaction.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgu.Transaction.dao.AskforleaveMapper;
import tgu.Transaction.pojo.Askforleave;
import tgu.Transaction.service.AskforleaveService;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/askforleaving")
public class AskforleaveController {

    @Autowired
    private AskforleaveMapper askforleaveMapper;


    @PostMapping("/apply")
    public result applyforLeaving(@RequestBody Askforleave askforleave){
            log.info(askforleave.toString());
            askforleaveMapper.insert(askforleave);
        return result.success("申请事务成功");
    }

    @GetMapping("/examine/{id}")
    public result examine(@PathVariable int id){
        QueryWrapper<Askforleave> query = new QueryWrapper<>();
        query.eq("uid",id);
        List<Askforleave> askforleaves = askforleaveMapper.selectList(query);

        if(askforleaves==null){
            return result.fail("您没有任何事务申请记录");
        }

        return result.success(askforleaves);
    }
}
