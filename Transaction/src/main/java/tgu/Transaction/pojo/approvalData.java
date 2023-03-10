package tgu.Transaction.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("事务处理数据")
public class approvalData {
    @ApiModelProperty("此事务id")
    private Long id;
    @ApiModelProperty("此事务处理状态：0 or 1 or 2")
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
