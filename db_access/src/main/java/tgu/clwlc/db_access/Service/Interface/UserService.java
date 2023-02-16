package tgu.clwlc.db_access.Service.Interface;

import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import java.util.List;

public interface UserService {

    User getUser(String username);
    User getUser(long uid);

    List<User> getUserList(long sid);

    boolean addUser(User user);

    boolean modifyUser(User user);

    boolean removeUser(long uid);
}
