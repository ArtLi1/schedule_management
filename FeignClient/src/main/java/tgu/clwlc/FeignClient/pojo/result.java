package tgu.clwlc.FeignClient.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class result {
    private boolean state;
    private String message;
    private Object object;


    public static result State(boolean b){
        result result = new result();
        result.setState(b);
        return result;
    }

    public static result success(Object o){
        result rs = new result();
        rs.setState(true);
        rs.setObject(o);
        return rs;
    }

    public static result fail(String  message){
        result rs = new result();
        rs.setState(false);
        rs.setMessage(message);
        return rs;
    }

}
