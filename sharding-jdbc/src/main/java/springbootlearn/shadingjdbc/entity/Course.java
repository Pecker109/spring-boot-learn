package springbootlearn.shadingjdbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-27
 */
@Data
@TableName("course")
public class Course {
    private Long id;
    private String name;
    private Long userId;
    private String status;
}
