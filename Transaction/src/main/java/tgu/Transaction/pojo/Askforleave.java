package tgu.Transaction.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Askforleave implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date start;
    private Date end;
    private String reason;
    private int status;
    private Long uid;

    private int category;
    private Date afterStart;
    private Date afterEnd;

//
//    public int getOption() {
//        return option;
//    }
//
//    public void setOption(int option) {
//        this.option = option;
//    }
//
//    public Date getAfterstart() {
//        return afterstart;
//    }
//
//    public void setAfterstart(Date afterstart) {
//        this.afterstart = afterstart;
//    }
//
//    public Date getAfterend() {
//        return afterend;
//    }
//
//    public void setAfterend(Date afterend) {
//        this.afterend = afterend;
//    }
//
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public Date getStart() {
//        return start;
//    }
//
//    public void setStart(Date start) {
//        this.start = start;
//    }
//
//    public Date getEnd() {
//        return end;
//    }
//
//    public void setEnd(Date end) {
//        this.end = end;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public int getUid() {
//        return uid;
//    }
//
//    public void setUid(int uid) {
//        this.uid = uid;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Askforleave{" +
//                "id=" + id +
//                ", start=" + start +
//                ", end=" + end +
//                ", reason='" + reason + '\'' +
//                ", status=" + status +
//                ", uid=" + uid +
//                '}';
//    }
}
