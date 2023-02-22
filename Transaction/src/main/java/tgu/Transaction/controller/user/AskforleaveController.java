package tgu.Transaction.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgu.Transaction.dao.AskforleaveMapper;
import tgu.clwlc.FeignClient.pojo.mysql.Askforleave;
import tgu.Transaction.service.AskforleaveService;
import tgu.clwlc.FeignClient.pojo.result;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/askforleaving")
@Api(tags = "事务申请相关接口")
public class AskforleaveController {

    @Autowired
    private AskforleaveMapper askforleaveMapper;

    @Autowired
    private AskforleaveService askforleaveService;


    @PostMapping("/apply")
    @ApiOperation(value = "申请事务接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="askforleave",value = "封装有Askforleave类的json数据，其中id不需要，后端会自动生成",required = true),
    })
    public result applyforLeaving(@RequestBody Askforleave askforleave){
        log.info(askforleave.toString());
        askforleaveMapper.insert(askforleave);
        return result.success("申请事务成功");
    }

    @GetMapping("/examine/{id}")
    @ApiOperation(value = "查看已提交的事务申请及处理状态接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "用户id（uid)",required = true),
    })
    public result examine(@PathVariable Long id){
        QueryWrapper<Askforleave> query = new QueryWrapper<>();
        query.eq("uid",id);
        List<Askforleave> askforleaves = askforleaveMapper.selectList(query);

        if(askforleaves==null){
            return result.fail("您没有任何事务申请记录");
        }

        return result.success(askforleaves);
    }

    /**
     * 撤销事务申请
     * @param id
     * @return
     */
    @DeleteMapping()
    @ApiOperation(value = "撤销事务申请接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "此事务的id",required = true),
    })
    public result withdraw(Long id){
        log.info("撤销id：{}",id);
        askforleaveService.removeById(id);
        return result.success("撤销申请成功");
    }

    /**
     * 根据id查询此条事务信息
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询此条事务信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "此事务的id",required = true),
    })
    public result getById(@PathVariable Long id){
        Askforleave askforleave = askforleaveService.getById(id);
        if(askforleave!=null){
            return result.success(askforleave);
        }
        return result.fail("没有查询到对应事务信息");
    }

    /**
     * 根据id修改提交的事务
     * @return
     */
    @PutMapping()
    @ApiOperation(value = "修改提交的事务接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="askforleave",value = "封装有Askforleave类的json数据",required = true),
    })
    public result update(@RequestBody Askforleave askforleave){
        log.info(askforleave.toString());
        askforleaveService.updateById(askforleave);
        return result.success("修改成功");
    }
}
