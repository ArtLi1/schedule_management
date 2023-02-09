package tgu.clwlc.FeignClient.pojo.secure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgu.clwlc.FeignClient.pojo.mysql.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class shiftsUser {
    private long id;
    private long sid;
    private String name;


    public shiftsUser(User user) {
        id = user.getId();
        sid = user.getSid();
        name = user.getName();
    }
}
