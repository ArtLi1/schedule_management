package tgu.clwlc.FeignClient.pojo.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
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
