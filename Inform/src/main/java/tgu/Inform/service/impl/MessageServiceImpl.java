package tgu.Inform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tgu.Inform.dao.MessageMapper;
import tgu.Inform.service.MessageService;
import tgu.clwlc.FeignClient.pojo.mysql.Message;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
}
