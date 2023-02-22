package tgu.clwlc.FeignClient.pojo.mysql;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("通知_用户关联信息")
public class Inform implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("Message_id")
    private Long msgId;
    @ApiModelProperty("此条通知的接收人id")
    private Long userId;
    @ApiModelProperty("是否已读，0表示未接收，1未读，2已读")
    private Integer isRead;
}
