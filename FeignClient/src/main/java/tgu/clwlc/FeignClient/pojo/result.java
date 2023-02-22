package tgu.clwlc.FeignClient.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("返回结果")
public class result {
    @ApiModelProperty("状态，true成功，false失败")
    private boolean state;
    @ApiModelProperty("错误信息")
    private String message;
    @ApiModelProperty("数据")
    private Object object;


    public static result State(boolean b){
        result result = new result();
        result.setState(b);
        return result;
    }

    public static result success(Object o){
        result rs = new result();
        rs.setState(true);
        rs.setObject(o);
        return rs;
    }

    public static result fail(String  message){
        result rs = new result();
        rs.setState(false);
        rs.setMessage(message);
        return rs;
    }

}
