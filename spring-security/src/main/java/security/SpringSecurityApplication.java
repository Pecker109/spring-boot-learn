package security;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2022-03-08
 */
@SpringBootApplication
@MapperScan("security.mapper")
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
        System.out.println(".................................");
        System.out.println("..........start success..........");
        System.out.println(".................................");
    }
}
