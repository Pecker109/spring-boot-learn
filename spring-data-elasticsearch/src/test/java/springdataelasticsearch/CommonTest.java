package springdataelasticsearch;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-06-29
 */
public class CommonTest {

    @SneakyThrows
    public static void main(String[] args) {
        //High Level Client init
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("10.50.26.247",19202)));
        GetRequest getRequest = new GetRequest("xwd_log_visit_domain_ip_stat_20200626", "_doc", "ktxa8XIB8v7GKWOQX1GT");
        GetResponse getResponse = client.get(getRequest);
        System.out.println(getResponse.getSourceAsMap());


    }
}
