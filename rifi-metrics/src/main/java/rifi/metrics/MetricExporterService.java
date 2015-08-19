package rifi.metrics;

import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.export.Exporter;
import org.springframework.boot.actuate.metrics.reader.MetricRegistryMetricReader;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MetricExporterService implements Exporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricExporterService.class);
    private final MetricRegistry repository;

    @Autowired
    ElasticSearchMetricsRepository elasticSearchMetricsRepository;

    MetricRegistryMetricReader reader;

    @Autowired
    public MetricExporterService(MetricRegistry repository) {
        this.repository = repository;
        this.reader = new MetricRegistryMetricReader(repository);
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 60000)
    @Override
    public void export() {
//        for(Map.Entry<String, com.codahale.metrics.Metric> metricEntry : repository.getMetrics().entrySet()) {
//            com.codahale.metrics.Metric metric = metricEntry.getValue();
//            metric
//            LOGGER.info("Saving metric [{}={}]", metric.getName(), metric.getValue());
//            elasticSearchMetricsRepository.save(new ElasticSearchMetric(metric.getTimestamp(), metric.getName(), metric.getValue()));
//            repository.reset(metric.getName());
//        }
        for(Metric metric : reader.findAll()) {
            LOGGER.info("Saving metric [{}={}]", metric.getName(), metric.getValue());
            elasticSearchMetricsRepository.save(new ElasticSearchMetric(metric.getTimestamp(), metric.getName(), metric.getValue()));
//            reader.
//            repository.reset(metric.getName());
        }
    }
}
