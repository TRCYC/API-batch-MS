package com.rcyc.batchsystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.elastic.Port;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class ElasticService {

    private final ElasticsearchClient client;
    private final RescoClient rescoClient;

    public ElasticService(ElasticsearchClient client, RescoClient rescoClient) {
        this.client = client;
        this.rescoClient = rescoClient;
    }

    public void getRegionData() {
        try {
            SearchResponse<Object> response = client.search(s -> s
                    .index("region")
                    .query(q -> q
                            .matchAll(m -> m)),
                    Object.class);

            response.hits().hits().forEach(hit -> {
                System.out.println("Found: " + hit.source());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRegionData(Region region) {
        try {
            IndexResponse response = client
                    .index(i -> i.index("region").id(region.getPortCode() + "_new").document(region));
            System.out.println(response.id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bulkInsertRegions(List<Region> regions, String indexName)
            throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Region region : regions) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(indexName)
                            .id(region.getRegion_code()) // optional, can be auto-generated
                            .document(region)));
        }

        BulkResponse result = client.bulk(br.build());

        if (result.errors()) {
            System.out.println("Bulk had errors");
            result.items().forEach(item -> {
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            });
        } else {
            System.out.println("Bulk insert successful");
        }
    }

    public void bulkInsertPorts(List<Port> ports, String indexName)
            throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Port port : ports) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(indexName)
                            .id(String.valueOf(port.getPortId())) // optional, can be auto-generated
                            .document(port)));
        }

        BulkResponse result = client.bulk(br.build());

        if (result.errors()) {
            System.out.println("Bulk had errors");
            result.items().forEach(item -> {
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            });
        } else {
            System.out.println("Bulk insert successful");
        }
    }

    public void createTempIndex(String indexName) throws Exception {
        if (!indexExists(indexName)) {
            createNewIndex(indexName);
        }
    }

    public boolean indexExists(String indexName) throws IOException {
        return client.indices().exists(e -> e.index(indexName)).value();
    }

    public void createNewIndex(String indexName) throws IOException {
        client.indices().create(c -> c
                .index(indexName)
                .settings(s -> s.numberOfShards("1").numberOfReplicas("1")));
 
    }

    public void truncateIndexData(String indexName) throws IOException {
    client.deleteByQuery(dq -> dq
        .index(indexName)
        .query(q -> q
            .matchAll(m -> m)
        )
    );
}
}
