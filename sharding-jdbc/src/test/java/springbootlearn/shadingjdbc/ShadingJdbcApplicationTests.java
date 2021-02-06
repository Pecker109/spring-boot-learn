package springbootlearn.shadingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import springbootlearn.shadingjdbc.entity.Course;
import springbootlearn.shadingjdbc.mapper.CourseMapper;

import javax.annotation.Resource;

@SpringBootTest
class ShadingJdbcApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    CourseMapper courseMapper;

    @Test
    void insert() {
        for (int i = 0; i < 10; i++) {
            Course c = new Course();
            c.setCname("课程名称" + i);
            c.setUserId(Long.valueOf("" + (1000 + i)));
            c.setCstatus(String.valueOf(i));
            courseMapper.insert(c);
        }
    }

    @Test
    void query() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("cid");
        wrapper.eq("cid", 1L);
        courseMapper.selectOne(wrapper);
    }

}
