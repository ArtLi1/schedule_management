package tgu.clwlc.FeignClient.pojo.mysql;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 事务表
 */
@Data
@ApiModel("事务数据模型")
public class Askforleave implements Serializable {
    private static final long serialVersionUID = 1L;

    //id类型为Long,可以配合mabatisplus自动生成id
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("请假的开始日期 或 换班事务的原始排班开始时间")
    private Date start;
    @ApiModelProperty("请假的结束日期 或 换班事务的原始排班结束时间")
    private Date end;
    @ApiModelProperty("请假理由")
    private String reason;
    @ApiModelProperty("事务申请状态，0表示未处理，1表示申请通过，2不通过")
    private int status;
    @ApiModelProperty("用户id")
    private Long uid;

    //category为1表示请假事务，2表示换班事务
    @ApiModelProperty("事务类别，1表示请假事务，2表示换班")
    private int category;
    @ApiModelProperty("员工申请的排班开始时间")
    private Date afterStart;
    @ApiModelProperty("员工申请的排班结束时间")
    private Date afterEnd;

//
//    public int getOption() {
//        return option;
//    }
//
//    public void setOption(int option) {
//        this.option = option;
//    }
//
//    public Date getAfterstart() {
//        return afterstart;
//    }
//
//    public void setAfterstart(Date afterstart) {
//        this.afterstart = afterstart;
//    }
//
//    public Date getAfterend() {
//        return afterend;
//    }
//
//    public void setAfterend(Date afterend) {
//        this.afterend = afterend;
//    }
//
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public Date getStart() {
//        return start;
//    }
//
//    public void setStart(Date start) {
//        this.start = start;
//    }
//
//    public Date getEnd() {
//        return end;
//    }
//
//    public void setEnd(Date end) {
//        this.end = end;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public int getUid() {
//        return uid;
//    }
//
//    public void setUid(int uid) {
//        this.uid = uid;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Askforleave{" +
//                "id=" + id +
//                ", start=" + start +
//                ", end=" + end +
//                ", reason='" + reason + '\'' +
//                ", status=" + status +
//                ", uid=" + uid +
//                '}';
//    }
}
