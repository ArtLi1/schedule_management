package tgu.clwlc.db_access.pojo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class shift {
    private float begin;
    private float end;
    private long uid;
}
