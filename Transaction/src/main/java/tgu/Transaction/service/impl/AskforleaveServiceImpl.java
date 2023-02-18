package tgu.Transaction.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tgu.Transaction.dao.AskforleaveMapper;
import tgu.clwlc.FeignClient.pojo.mysql.Askforleave;
import tgu.Transaction.service.AskforleaveService;

@Service
public class AskforleaveServiceImpl extends ServiceImpl<AskforleaveMapper, Askforleave> implements AskforleaveService {

}
