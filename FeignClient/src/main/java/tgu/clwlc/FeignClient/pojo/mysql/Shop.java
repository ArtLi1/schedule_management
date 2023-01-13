package tgu.clwlc.FeignClient.pojo.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shop {
    private long id;
    private String name;
    private String address;
    private double size;
}
