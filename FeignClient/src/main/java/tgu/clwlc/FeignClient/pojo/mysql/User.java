package tgu.clwlc.FeignClient.pojo.mysql;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("用户")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    //原本id类型是long，改为Long后，可以配合mybatisPlus实现自动生成id
    @ApiModelProperty("主键id")
    private Long id;
    @NotBlank
    @ApiModelProperty("门店id")
    private long sid;
    @NotBlank
    @ApiModelProperty("姓名")
    private String name;
    @Email
    @ApiModelProperty("邮箱")
    private String email;
    @NotBlank
    @ApiModelProperty("职位")
    private int job;

    @ApiModelProperty("手机号码")
    private long phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("权限")
    private int permission;

}
