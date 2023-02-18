package tgu.Transaction.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class AskforleaveController {

    @Autowired
    private AskforleaveMapper askforleaveMapper;

    @Autowired
    private AskforleaveService askforleaveService;


    @PostMapping("/apply")
    public result applyforLeaving(@RequestBody Askforleave askforleave){
        log.info(askforleave.toString());
        askforleaveMapper.insert(askforleave);
        return result.success("申请事务成功");
    }

    @GetMapping("/examine/{id}")
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
    public result update(@RequestBody Askforleave askforleave){
        log.info(askforleave.toString());
        askforleaveService.updateById(askforleave);
        return result.success("修改成功");
    }
}
