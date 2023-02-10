package tgu.clwlc.FeignClient.pojo.mysql;

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
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    //原本id类型是long，改为Long后，可以配合mybatisPlus实现自动生成id
    private Long id;
    @NotBlank
    private long sid;
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private int job;

    private long phone;

    private String password;

    private int permission;

}
