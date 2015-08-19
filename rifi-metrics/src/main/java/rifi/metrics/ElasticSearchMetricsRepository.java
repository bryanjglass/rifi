package rifi.metrics;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchMetricsRepository extends ElasticsearchRepository<ElasticSearchMetric, String> {

}
