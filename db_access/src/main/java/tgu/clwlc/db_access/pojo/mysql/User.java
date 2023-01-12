package tgu.clwlc.db_access.pojo.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    private long id;
    private long sid;
    private String name;
    private String email;
    private int job;
    private long phone;
    private String password;

}
