package tgu.clwlc.db_access.pojo.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("shop")
@Data
public class Shop {
    private long id;
    private String name;
    private String address;
    private double size;
}
