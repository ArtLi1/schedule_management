package tgu.Transaction.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgu.Transaction.dao.AskforleaveMapper;
import tgu.Transaction.pojo.Askforleave;
import tgu.Transaction.pojo.approvalData;
import tgu.Transaction.service.AskforleaveService;
import tgu.clwlc.FeignClient.pojo.result;

import java.util.List;

@RestController
@RequestMapping("/checkInfo")
public class CheckLeavingController {

    @Autowired
    private AskforleaveMapper askforleaveMapper;

    @Autowired
    private AskforleaveService askforleaveService;


    @GetMapping("/singlecheck/{uid}")
    public result checkSingleInfo(@PathVariable int uid){

        QueryWrapper<Askforleave> query = new QueryWrapper<>();
        query.eq("uid",uid);
        List<Askforleave> askforleaves = askforleaveMapper.selectList(query);

        if(askforleaves.isEmpty()){
            return result.fail("无申请记录");
        }

        return result.success(askforleaves);
    }

    @GetMapping("/checkAllInfo")
    public result checkAllInfo(){
        List<Askforleave> askforleaves = askforleaveMapper.selectList(null);

        if(askforleaves == null){
            return result.fail("无申请记录");
        }

        return result.success(askforleaves);
    }

    @PostMapping("/approval")
    public result approval(@RequestBody approvalData approvalData){
        int id = approvalData.getId();
        int status=approvalData.getStatus();

        QueryWrapper<Askforleave> query = new QueryWrapper<>();
        query.eq("id",id);

        Askforleave one = askforleaveService.getOne(query);

        System.out.println(one);
        one.setStatus(status);
        System.out.println(one);
        askforleaveMapper.updateById(one);
        return result.success("审批成功");
    }
}
