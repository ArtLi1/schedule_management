package tgu.clwlc.FeignClient.pojo.secure;

import lombok.Data;
import tgu.clwlc.FeignClient.pojo.mysql.User;


@Data
public class secureUser {
    private long id;
    private long sid;
    private String name;
    private int job;
    private long phone;
    private String email;

    public secureUser(User user) {
        this.id = user.getId();
        this.sid = user.getSid();
        this.name = user.getName();
        this.job = user.getJob();
        this.phone = user.getPhone();
        this.email = user.getEmail();
    }
}
