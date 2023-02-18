package tgu.Transaction.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tgu.clwlc.FeignClient.pojo.mysql.Askforleave;

@Mapper
public interface AskforleaveMapper extends BaseMapper<Askforleave> {
}
