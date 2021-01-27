package springbootlearn.shadingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("springbootlearn.shadingjdbc.mapper")
public class ShadingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShadingJdbcApplication.class, args);
        System.out.println(".....................................................");
        System.out.println("....................start success....................");
        System.out.println(".....................................................");
    }

}
