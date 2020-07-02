package springdataelasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-06-28
 */
@Data
@Document(indexName = "xwd_log_visit_domain_ip_stat_20200626")
public class VisitLogModel {

    @Id
    private String id;

    private String srcAddress;

    private String startTime;
}
