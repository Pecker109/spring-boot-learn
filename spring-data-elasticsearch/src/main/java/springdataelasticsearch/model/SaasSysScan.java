package springdataelasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.repository.Repository;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-09-21
 */
@Data
@Document(indexName = "saas_sys_scan",type = "sys_scan")
public class SaasSysScan {
    @Id
    private String id;

    private String domain;

    private String host;

}
