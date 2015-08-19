package rifi.metrics;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "system_metrics", type = "metric", shards = 2, replicas = 0, refreshInterval = "-1")
public class ElasticSearchMetric implements Serializable {
    @Id
    String id;
    Date timestamp;
    String name;
    Number value;

    public ElasticSearchMetric(Date timestamp, String name, Number value) {
        this.id = timestamp.getTime() + name;
        this.timestamp = timestamp;
        this.name = name;
        this.value = value;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }
}
