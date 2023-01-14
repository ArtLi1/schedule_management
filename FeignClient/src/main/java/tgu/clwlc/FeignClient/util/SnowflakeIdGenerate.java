package tgu.clwlc.FeignClient.util;

public class SnowflakeIdGenerate {

    private final long TIMESTAMP = 1673606192615L;

    private final int WorkIdLen = 10;

    private final int SequenceLen = 12;

    private final int TimeStampLen = SequenceLen+WorkIdLen;

    private int sequence = 0;

    private long lastTime = 0L;

    private final long WorkId;


    public SnowflakeIdGenerate(long workId) {
        if(workId >= 1024){
            throw new RuntimeException("WorkId is too large");
        }
        this.WorkId = workId;
    }

    public long nextId(){
        long now = System.currentTimeMillis();
        if(lastTime!=now){
            lastTime = now;
            sequence = 0;
        }
        sequence++;
        return (now-TIMESTAMP) << TimeStampLen |
                WorkId << SequenceLen |
                sequence;
    }
}
