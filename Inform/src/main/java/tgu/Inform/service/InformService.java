package tgu.Inform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tgu.clwlc.FeignClient.pojo.dto.InformDto;
import tgu.clwlc.FeignClient.pojo.mysql.Inform;
import tgu.clwlc.FeignClient.pojo.mysql.Message;

import java.util.List;

public interface InformService extends IService<Inform> {
    //发送通知，将msg存入message表，同时在Inform表插入信息，需要操作两张表
    public void saveWithMsg(InformDto informDto);

    //查看所有已发送通知
    public List<InformDto> getAllSentInfo(Long senderId);

    //接收通知
    public List<Message> getMessage(Long userId);



}
