/**
 * LogRepository - Data access layer for MongoDB operations.
 * Handles saving logs and retrieving analytics data.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.*;

import edu.cmu.tangyiq.model.SearchLog;
public class LogRepository {
    private static final String CONNECTION_STRING = "mongodb+srv://qty20010619_db_user:IbH2o4xnTD6qAF4K@cluster0.xrafh1j.mongodb.net/?appName=Cluster0";
    private static final String DATABASE_NAME = "tavily_search";
    private static final String COLLECTION_NAME = "search_logs";

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    public LogRepository() {
        mongoClient = MongoClients.create(CONNECTION_STRING);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    public void save(SearchLog log) {
        Document doc = new Document()
                .append("timestamp", log.getTimestamp())
                .append("query", log.getQuery())
                .append("deviceModel", log.getDeviceModel())
                .append("tavilyLatency", log.getTavilyLatency())
                .append("resultCount", log.getResultCount())
                .append("status", log.getStatus())
                .append("clientIP", log.getClientIP());
        collection.insertOne(doc);
    }

    public long getTotalSearches() {
        return collection.countDocuments();
    }

    public double getAverageLatency() {
        List<Document> results = collection.aggregate(Arrays.asList(
                Aggregates.group(null, Accumulators.avg("avgLatency", "$tavilyLatency"))
        )).into(new ArrayList<>());

        if (!results.isEmpty() && results.get(0).get("avgLatency") != null) {
            return results.get(0).getDouble("avgLatency");
        }
        return 0.0;
    }

    public List<Map.Entry<String, Long>> getTopQueries(int limit) {
        List<Document> results = collection.aggregate(Arrays.asList(
                Aggregates.group("$query", Accumulators.sum("count", 1)),
                Aggregates.sort(Sorts.descending("count")),
                Aggregates.limit(limit)
        )).into(new ArrayList<>());

        List<Map.Entry<String, Long>> topQueries = new ArrayList<>();
        for (Document doc : results) {
            String query = doc.getString("_id");
            Long count = doc.getInteger("count").longValue();
            topQueries.add(new AbstractMap.SimpleEntry<>(query, count));
        }
        return topQueries;
    }

    public List<SearchLog> getAllLogs() {
        List<SearchLog> logs = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.descending("timestamp"))) {
            SearchLog log = new SearchLog();
            log.setTimestamp(doc.getDate("timestamp"));
            log.setQuery(doc.getString("query"));
            log.setDeviceModel(doc.getString("deviceModel"));
            log.setTavilyLatency(doc.getLong("tavilyLatency"));
            log.setResultCount(doc.getInteger("resultCount"));
            log.setStatus(doc.getString("status"));
            log.setClientIP(doc.getString("clientIP"));
            logs.add(log);
        }
        return logs;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
