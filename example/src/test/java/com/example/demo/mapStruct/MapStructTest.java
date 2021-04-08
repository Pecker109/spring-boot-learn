package com.example.demo.mapStruct;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-03-22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapStructTest {
    @Resource
    private UserMapping userMapping;

    @Resource
    private BaseMapping baseMapping;

    @Test
    public void tetDomain2DTO() {
        User user = new User()
                .setId(1L)
                .setUsername("zhangsan")
                .setSex(1)
                .setPassword("abc123")
                .setCreateTime(LocalDateTime.now())
                .setBirthday(LocalDate.of(1999, 9, 27))
                .setConfig("[{\"field1\":\"Test Field1\",\"field2\":500}]");
        UserVO userVo = userMapping.sourceToTarget(user);
        log.info("User: {}", user);
        //        User: User(id=1, username=zhangsan, password=abc123, sex=1, birthday=1999-09-27, createTime=2020-01-17T17:46:20.316, config=[{"field1":"Test Field1","field2":500}])
        log.info("UserVo: {}", userVo);
        //        UserVo: UserVo(id=1, username=zhangsan, gender=1, birthday=1999-09-27, createTime=2020-01-17 17:46:20, config=[UserVo.UserConfig(field1=Test Field1, field2=500)])
    }

    @Test
    public void testDTO2Domain() {
        UserVO.UserConfig userConfig = new UserVO.UserConfig();
        userConfig.setField1("Test Field1");
        userConfig.setField2(500);

        UserVO userVo = new UserVO()
                .setId(1L)
                .setUsername("zhangsan")
                .setGender(2)
                .setCreateTime("2020-01-18 15:32:54")
                .setBirthday(LocalDate.of(1999, 9, 27))
                .setConfig(Collections.singletonList(userConfig));
        User user = userMapping.targetToSource(userVo);
        log.info("UserVo: {}", userVo);
        //        UserVo: UserVo(id=1, username=zhangsan, gender=2, birthday=1999-09-27, createTime=2020-01-18 15:32:54, config=[UserVo.UserConfig(field1=Test Field1, field2=500)])
        log.info("User: {}", user);
        //        User: User(id=1, username=zhangsan, password=null, sex=2, birthday=1999-09-27, createTime=2020-01-18T15:32:54, config=[{"field1":"Test Field1","field2":500}])
    }

    @Test
    public void testBaseMapping() {
        UserVO.UserConfig userConfig = new UserVO.UserConfig();
        userConfig.setField1("Test Field1");
        userConfig.setField2(500);

        UserVO userVo = new UserVO()
                .setId(1L)
                .setUsername("zhangsan")
                .setGender(2)
                .setCreateTime("2020-01-18 15:32:54")
                .setBirthday(LocalDate.of(1999, 9, 27))
                .setConfig(Collections.singletonList(userConfig));

        User user = (User) baseMapping.targetToSource(userVo);
        System.out.println(JSON.toJSONString(user));
    }
}
