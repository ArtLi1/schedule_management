package tgu.clwlc.FeignClient.pojo.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private long id;
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

}
