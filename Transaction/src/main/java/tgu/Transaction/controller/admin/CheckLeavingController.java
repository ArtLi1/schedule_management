package tgu.Transaction.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgu.Transaction.dao.AskforleaveMapper;
import tgu.clwlc.FeignClient.pojo.mysql.Askforleave;
import tgu.Transaction.pojo.approvalData;
import tgu.Transaction.service.AskforleaveService;

import tgu.clwlc.FeignClient.pojo.result;


import java.util.List;

@RestController
@RequestMapping("/checkInfo")
@Api(tags = "事务处理相关接口")
public class CheckLeavingController {

    @Autowired
    private AskforleaveMapper askforleaveMapper;

    @Autowired
    private AskforleaveService askforleaveService;






    @GetMapping("/singlecheck/{uid}")
    @ApiOperation(value = "查询某个用户事务接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="uid",value = "用户id(uid)",required = true),
    })
    public result checkSingleInfo(@PathVariable Long uid){

        QueryWrapper<Askforleave> query = new QueryWrapper<>();
        query.eq("uid",uid);
        List<Askforleave> askforleaves = askforleaveMapper.selectList(query);
        if(askforleaves.isEmpty()){
            return result.fail("无申请记录");
        }



        return result.success(askforleaves);
    }

    @GetMapping("/checkAllInfo")
    @ApiOperation(value = "查询所有已提交事务和处理状态接口")
    public result checkAllInfo(){
        List<Askforleave> askforleaves = askforleaveMapper.selectList(null);

        if(askforleaves == null){
            return result.fail("无申请记录");
        }

        return result.success(askforleaves);
    }

    @PostMapping("/approval")
    @ApiOperation(value = "处理事务接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="approvalData",value = "事务处理数据",required = true),
    })
    public result approval(@RequestBody approvalData approvalData){
        Long id = approvalData.getId();
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
