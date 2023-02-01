package tgu.clwlc.account.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private int job;
    private Long sid;
    private String password;
    private Long phone;
    private int permission;
}
