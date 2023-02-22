package tgu.Inform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tgu.clwlc.FeignClient.pojo.mysql.Inform;

@Mapper
public interface InformMapper extends BaseMapper<Inform> {
}
