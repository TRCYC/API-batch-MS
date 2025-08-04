package com.rcyc.batchsystem.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.rcyc.batchsystem.model.elastic.*;
import com.rcyc.batchsystem.util.Constants;

import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.ReindexRequest;
import co.elastic.clients.elasticsearch.core.ReindexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;

@Service
public class ElasticService {

    private final ElasticsearchClient client;
    private final RescoClient rescoClient;

    public ElasticService(ElasticsearchClient client, RescoClient rescoClient) {
        this.client = client;
        this.rescoClient = rescoClient;
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

    public <T> void bulkInsert(List<T> items, String indexName, Function<T, String> idExtractor) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (T item : items) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(indexName)
                            .id(idExtractor.apply(item))
                            .document(item)));
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

    public void bulkInsertTransfers(List<Transfer> transfers, String indexName) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Transfer transfer : transfers) {
            br.operations(op -> op.index(idx -> idx.index(indexName)
                    .id(String.valueOf(transfer.getRescoItemID()) + String.valueOf(transfer.getVoyageId()))
                    .document(transfer)));
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

    public void bulkInsertSuite(List<Suite> suites, String indexName) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Suite suite : suites) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(indexName)
                            .id(suite.getVoyageId() != null ? suite.getVoyageId().toString() : null)
                            .document(suite)));
        }

        BulkResponse result = client.bulk(br.build());

        if (result.errors()) {
            System.out.println("Bulk insert had errors");
            result.items().forEach(item -> {
                if (item.error() != null) {
                    System.out.println("Error: " + item.error().reason());
                }
            });
        } else {
            System.out.println("Bulk insert for Suite successful. Inserted: " + suites.size());
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
                        .matchAll(m -> m)));
    }

    public void bulkInsertPricing(List<com.rcyc.batchsystem.model.elastic.Pricing> pricings, String indexName)
            throws IOException {
        bulkInsert(pricings, indexName, p -> String.valueOf(p.getCruiseCode() + p.getCategoryCode() + p.getCurrency()));
    }

    public void bulkInsertHotels(List<com.rcyc.batchsystem.model.elastic.Hotel> hotels, String indexName)
            throws IOException {
        bulkInsert(hotels, indexName, h -> String.valueOf(h.getEventId()));
    }

    public void bulkInsertRegions(List<Region> regions, String indexName)
            throws IOException {
        bulkInsert(regions, indexName, r -> r.getRegion_code());
    }

    public void bulkInsertPorts(List<Port> ports, String indexName)
            throws IOException {
        bulkInsert(ports, indexName, p -> String.valueOf(p.getPortId()));
    }

    public void bulkInsertItineraries(List<com.rcyc.batchsystem.model.elastic.Itinerary> itineraries, String indexName)
            throws IOException {
        bulkInsert(itineraries, indexName, i -> String.valueOf(i.getId()));
    }

    public void bulkInsertVoyages(List<com.rcyc.batchsystem.model.elastic.Voyage> voyages, String indexName)
            throws IOException {
        // bulkInsert(voyages, indexName, v -> String.valueOf(v.getEventId()));
    }

    public void bulkInsertExcursionVoyages(List<com.rcyc.batchsystem.model.elastic.ExcursionVoyage> excursionVoyages,
            String indexName)
            throws IOException {
        bulkInsert(excursionVoyages, indexName, v -> String.valueOf(v.getId()));
    }

    public long getDocumentCount(String index) {
        try {
            CountResponse response = client.count(c -> c.index(index));
            return response.count();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private <T> List<T> getAllDocuments(String indexName, Class<T> clazz) throws IOException {
        List<T> results = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(indexName)
                .query(q -> q.matchAll(m -> m)) // match_all query
                .size(1000) // adjust page size as needed
                .build();

        SearchResponse<T> response = client.search(searchRequest, clazz);

        for (Hit<T> hit : response.hits().hits()) {
            results.add(hit.source());
        }

        return results;
    }

    public List<Region> getRegionData() {
        List<Region> results = new ArrayList<>();
        try {
            results = getAllDocuments(Constants.REGION_INDEX, Region.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Region> getTempRegionData() {
        List<Region> results = new ArrayList<>();
        try {
            results = getAllDocuments(Constants.REGION_DEMO, Region.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private void deleteById(String indexName, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest.Builder()
                .index(indexName)
                .id(id)
                .build();

        DeleteResponse deleteResponse = client.delete(deleteRequest);
        System.out.println("Deleted: " + deleteResponse.result());
    }

    public void deleteRegionDocument(String id) {
        try {
            deleteById(Constants.REGION_DEMO, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reindexDemoToLive(String sourceIndex,String destinationIndex) throws IOException {
        ReindexRequest reindexRequest = new ReindexRequest.Builder()
                .source(s -> s.index(sourceIndex))
                .dest(d -> d.index(destinationIndex))
                .build();

        ReindexResponse response = client.reindex(reindexRequest);
        System.out.println("Reindexed docs: " + response.created());
    }
}
