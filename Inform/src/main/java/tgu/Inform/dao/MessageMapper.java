package tgu.Inform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tgu.clwlc.FeignClient.pojo.mysql.Message;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
