package tgu.Inform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgu.Inform.common.CustomException;
import tgu.Inform.dao.InformMapper;
import tgu.Inform.dao.MessageMapper;
import tgu.Inform.service.InformService;
import tgu.Inform.service.MessageService;
import tgu.clwlc.FeignClient.pojo.dto.InformDto;
import tgu.clwlc.FeignClient.pojo.mysql.Inform;
import tgu.clwlc.FeignClient.pojo.mysql.Message;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InformServiceImpl extends ServiceImpl<InformMapper, Inform> implements InformService {
    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private InformMapper informMapper;


    /**
     * 发送通知，将msg存入message表，同时在Inform表插入信息，需要操作两张表
     * 操作2张表需要开启事务，用注解@Transactional
     */
    @Override
    @Transactional
    public void saveWithMsg(InformDto informDto) {
        //先将msg插入Message表
        Message message = informDto.getMessage();
        Long id = IdWorker.getId(message);
        log.info("message_id:{}",id);
        message.setId(id);
        message.setSendTime(LocalDateTime.now());
        messageService.save(message);
        //将通知信息插入Inform表
        Inform inform=new Inform();
        inform.setMsgId(id);
        inform.setIsRead(0);
        List<Long> userIds = informDto.getUserIds();
        List<Inform> informs=new ArrayList<>();

        for (Long userId : userIds) {
            Inform inform1=new Inform();
            BeanUtils.copyProperties(inform,inform1);
            inform1.setUserId(userId);
            informs.add(inform1);
        }

        this.saveBatch(informs);

    }

    /**
     * 查看所有已发送通知
     * 涉及的表：message, inform
     * @param senderId
     * @return
     */
    @Override
    @Transactional
    public List<InformDto> getAllSentInfo(Long senderId) {
        //根据senderId查询本人发的所有通知（从message表）
        LambdaQueryWrapper<Message> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getSenderId,senderId);
        List<Message> messages = messageService.list(queryWrapper);
        //从inform表查询这些通知的相关信息
        List<InformDto> informDtos=new ArrayList<>();

        for (Message message : messages) {
            LambdaQueryWrapper<Inform> informQueryWrapper=new LambdaQueryWrapper<>();

            InformDto informDto=new InformDto();
            informDto.setMessage(message);

            Long messageId = message.getId();
            informQueryWrapper.eq(Inform::getMsgId,messageId);
            List<Inform> informs = this.list(informQueryWrapper);

            List<Long> userIds=new ArrayList<>();
            for (Inform inform : informs) {
                userIds.add(inform.getUserId());
            }
            informDto.setUserIds(userIds);

            QueryWrapper<Inform> wrapper=new QueryWrapper<>();
            wrapper.eq("msg_id",messageId);
            Integer totalNum= informMapper.selectCount(wrapper);
            wrapper.eq("is_read",2);
            Integer readNum = informMapper.selectCount(wrapper);
            informDto.setTotalNum(totalNum);
            informDto.setReadNum(readNum);
            informDtos.add(informDto);
        }

        return informDtos;
    }

    /**
     * 接收通知
     * 涉及的表：inform，message
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public List<Message> getMessage(Long userId) {
        //根据userId查询对应的未接收过的Inform(inform表)
        LambdaQueryWrapper<Inform> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Inform::getUserId,userId).eq(Inform::getIsRead,0);
        List<Inform> informs = this.list(queryWrapper);
        if(informs.size()==0){
            throw new CustomException("当前没有新通知");
        }
        List<Long> msgIds=new ArrayList<>();
        for (Inform inform : informs) {
            //获取msgId
            Long msgId = inform.getMsgId();
            msgIds.add(msgId);
            inform.setIsRead(1);
        }
        List<Message> messages = messageMapper.selectBatchIds(msgIds);
        this.updateBatchById(informs);
        return messages;
    }


}
