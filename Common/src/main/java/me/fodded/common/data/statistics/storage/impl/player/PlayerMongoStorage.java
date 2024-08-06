package me.fodded.common.data.statistics.storage.impl.player;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.fodded.common.data.statistics.impl.player.AbstractPlayerData;
import me.fodded.common.data.statistics.storage.impl.MongoStorage;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerMongoStorage<T extends AbstractPlayerData> extends MongoStorage implements IPlayerDataStorage<T> {

    private MongoCollection<Document> playersCollection;
    private static final ReplaceOptions UPSERT = new ReplaceOptions().upsert(true);

    @Override
    public void initialize() {
        this.connect();
        this.playersCollection = mongoClient
                .getDatabase(System.getenv("STATISTICS_DATABASE_NAME "))
                .getCollection("playerStatistics");
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<T> loadData(UUID uuid, T playerDataClass) {
        return CompletableFuture.supplyAsync(() -> {
            Document foundDocument = playersCollection.find(Filters.eq("uuid", uuid.toString())).first();
            if (foundDocument == null) {
                return playerDataClass;
            }

            return (T) GSON.fromJson(foundDocument.toJson(), playerDataClass.getClass());
        });
    }

    @Override
    public CompletableFuture<Void> saveData(T playerDataClass) {
        return CompletableFuture.runAsync(() -> {
            String json = GSON.toJson(playerDataClass);
            playersCollection.replaceOne(Filters.eq("uuid", playerDataClass.getUuid().toString()), Document.parse(json), UPSERT);
        });
    }
}
