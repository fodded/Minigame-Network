package me.fodded.common.data.storage.impl.player;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import me.fodded.common.data.constants.DatabaseData;
import me.fodded.common.data.impl.player.AbstractPlayerData;
import me.fodded.common.data.storage.impl.MongoStorage;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerMongoStorage<T extends AbstractPlayerData> extends MongoStorage implements IPlayerDataStorage<T> {

    private MongoCollection<Document> playersCollection;

    @Override
    public void initialize() {
        this.connect();
        this.playersCollection = mongoClient.getDatabase(DatabaseData.STATISTICS_DATABASE).getCollection("playerStatistics");
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<T> loadData(UUID uuid, T playerDataClass) {
        return (CompletableFuture<T>) CompletableFuture.supplyAsync(() -> {
            Document foundDocument = playersCollection.find(Filters.eq("uuid", uuid.toString())).first();
            if(foundDocument == null) {
                return playerDataClass;
            }

            return GSON.fromJson(foundDocument.toJson(), playerDataClass.getClass());
        });
    }

    @Override
    public CompletableFuture<Void> saveData(T playerDataClass) {
        return CompletableFuture.runAsync(() -> {
            String json = GSON.toJson(playerDataClass);
            Document foundPlayerDocument = playersCollection.find(new Document("uuid", playerDataClass.getUuid().toString())).first();
            if(foundPlayerDocument == null) {
                playersCollection.insertOne(Document.parse(json));
                return;
            }

            playersCollection.replaceOne(foundPlayerDocument, Document.parse(json));
        });
    }
}
