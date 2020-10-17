package springdataelasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springdataelasticsearch.service.RestClientService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    RestClientService restClientService;

    @Test
    public void contextLoads() {
        restClientService.get();
    }

	@Test
	public void agentLogin() {

	}

}
