package springdataelasticsearch.service;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-06-28
 */
@Service
public class RestClientService {


    @Autowired
    RestHighLevelClient restHighLevelClient;

    @SneakyThrows
    public void get() {
        //High Level Client init
        GetRequest getRequest = new GetRequest("xwd_log_visit_domain_ip_stat_20200626", "_doc", "ktxa8XIB8v7GKWOQX1GT");
        GetResponse getResponse = restHighLevelClient.get(getRequest);
        System.out.println(getResponse.getSourceAsMap());

    }
}
