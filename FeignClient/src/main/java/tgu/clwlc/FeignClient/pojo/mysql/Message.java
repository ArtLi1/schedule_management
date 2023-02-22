package tgu.clwlc.FeignClient.pojo.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("通知消息")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("通知文本")
    private String msg;
    @ApiModelProperty("发通知的人的id")
    private Long senderId;
    @ApiModelProperty("发送者姓名")
    private String senderName;
    @ApiModelProperty("通知发送时间")
    private LocalDateTime sendTime;

}
