package tgu.clwlc.db_access;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("tgu.clwlc.db_access.dao")
@SpringBootApplication
public class dbAccessApplication {
    public static void main(String[] args) {
        SpringApplication.run(dbAccessApplication.class,args);
    }
}
