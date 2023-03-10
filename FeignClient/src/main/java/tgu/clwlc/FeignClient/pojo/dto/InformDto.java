package tgu.clwlc.FeignClient.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tgu.clwlc.FeignClient.pojo.mysql.Inform;
import tgu.clwlc.FeignClient.pojo.mysql.Message;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import java.util.List;

@Data
@ApiModel("InformDto")
public class InformDto extends Inform {
    @ApiModelProperty("Message对象")
    private Message message;
    @ApiModelProperty("接收人id数组")
    private List<Long> userIds;
    @ApiModelProperty("此条消息已读人数")
    private Integer readNum;
    @ApiModelProperty("此条消息总接收人数")
    private Integer totalNum;

}
