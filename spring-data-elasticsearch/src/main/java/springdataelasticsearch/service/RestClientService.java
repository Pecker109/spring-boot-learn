package springdataelasticsearch.service;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Scheduled;
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
        QueryBuilder queryBuilder = QueryBuilders.boolQuery();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder).indices("saas_sys_scan").types("sys_scan");
        restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        //High Level Client init
//        GetRequest getRequest = new GetRequest("saas_sys_scan", "sys_scan", "acffa390a2137c1f281349790b65f8de");
//        GetResponse getResponse = restHighLevelClient.get(getRequest);
//        System.out.println(getResponse.getSourceAsMap());

    }
}
