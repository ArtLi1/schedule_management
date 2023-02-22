package tgu.Inform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgu.Inform.service.InformService;
import tgu.clwlc.FeignClient.pojo.dto.InformDto;
import tgu.clwlc.FeignClient.pojo.mysql.Inform;
import tgu.clwlc.FeignClient.pojo.mysql.Message;
import tgu.clwlc.FeignClient.pojo.result;

import java.util.List;

@RestController
@RequestMapping("/inform")
@Slf4j
@Api(tags = "通知模块相关接口")
public class InformController {
    @Autowired
    private InformService informService;

    /**
     * 发送通知
     * @return
     */
    @PostMapping
    @ApiOperation(value = "发送通知接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="informDto",value = "需要传通知文本msg，发送者id，发送者姓名，接收人id数组,json格式",required = true)
    })
    public result sendInform(@RequestBody InformDto informDto){
        String msg = informDto.getMessage().getMsg();
        if(msg.isEmpty()){
            return result.fail("通知不能为空");
        }
        informService.saveWithMsg(informDto);
        return result.success("发送成功");
    }

    /**
     * 查看所有已发送通知
     */
    @GetMapping("/getSent")
    @ApiOperation(value = "查看所有已发送通知接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="senderId",value = "需要传发送者id即可",required = true)
    })
    public result getAllSent(Long senderId){
        List<InformDto> allSentInfo = informService.getAllSentInfo(senderId);
        return result.success(allSentInfo);
    }

    /**
     * 接收通知
     * @param userId
     * @return
     */
    @GetMapping("/get")
    @ApiOperation(value = "接收通知接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "需要传此用户的id",required = true)
    })
    public result getInform(Long userId){
        List<Message> message = informService.getMessage(userId);
        return result.success(message);
    }

    /**
     * 确认通知
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation(value = "确认通知接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="msgId",value = "需要传此条通知的id",required = true),
            @ApiImplicitParam(name="userId",value = "需要传此用户的id",required = true),
    })
    public result confirm(@RequestParam Long msgId, @RequestParam Long userId){
        LambdaQueryWrapper<Inform> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Inform::getMsgId,msgId).eq(Inform::getUserId,userId);
        Inform inform = informService.getOne(queryWrapper);
        inform.setIsRead(2);
        informService.updateById(inform);
        return result.success("你已确认");
    }
}
