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

    private Long id;

    private long sid;
    @NotBlank
    private String name;
    @Email
    private String email;

    private int job;

    private long phone;

    private String password;

    private int permission;

}
