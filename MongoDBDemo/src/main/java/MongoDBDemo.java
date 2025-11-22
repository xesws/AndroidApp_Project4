/**
 * MongoDBDemo.java
 *
 * A simple Java application that demonstrates writing and reading data
 * from MongoDB Atlas cloud database.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.util.Scanner;

public class MongoDBDemo {

    // MongoDB Atlas connection string
    private static final String CONNECTION_STRING =
        "mongodb+srv://qty20010619_db_user:fMvkEUTZPMMH5Ke8@cluster0.xrafh1j.mongodb.net/?retryWrites=true&w=majority";

    private static final String DATABASE_NAME = "task1_demo";
    private static final String COLLECTION_NAME = "user_strings";

    public static void main(String[] args) {
        System.out.println("=== MongoDB Atlas Demo ===");
        System.out.println();

        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {

            // Get database and collection
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            System.out.println("Connected to MongoDB Atlas successfully!");
            System.out.println();

            // Prompt user for input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a string to store in the database: ");
            String userInput = scanner.nextLine();

            // Write the string to MongoDB
            Document doc = new Document("userString", userInput)
                    .append("timestamp", System.currentTimeMillis());
            collection.insertOne(doc);
            System.out.println("String written to database successfully!");
            System.out.println();

            // Read all documents from the collection
            System.out.println("All strings stored in the database:");
            System.out.println("-----------------------------------");

            FindIterable<Document> documents = collection.find();
            int count = 0;
            for (Document document : documents) {
                count++;
                String storedString = document.getString("userString");
                System.out.println(count + ". " + storedString);
            }

            if (count == 0) {
                System.out.println("(No documents found)");
            }

            System.out.println();
            System.out.println("Total documents: " + count);
            System.out.println();
            System.out.println("=== Demo Complete ===");

            scanner.close();

        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
