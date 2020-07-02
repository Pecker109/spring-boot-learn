package springdataelasticsearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springdataelasticsearch.service.RestClientService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	RestClientService restClientService;

	@Test
	void contextLoads() {
		restClientService.get();
	}

}
