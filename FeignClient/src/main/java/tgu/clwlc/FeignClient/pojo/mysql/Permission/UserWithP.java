package tgu.clwlc.FeignClient.pojo.mysql.Permission;

import lombok.Data;

import java.util.List;

@Data
public class UserWithP {
    private Long id;

    private long sid;

    private String name;

    private String email;

    private int job;

    private long phone;

    private String password;

    private List<String> permission;

    private String role;

}
