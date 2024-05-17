package me.fodded.common.data.statistics.storage.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import me.fodded.common.data.config.DatabaseData;
import me.fodded.common.data.statistics.storage.IDataStorage;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoStorage implements IDataStorage {

    protected MongoClient mongoClient;
    protected static final Gson GSON = new GsonBuilder().create();

    @Override
    public final void connect() {
        ConnectionString connectionString = new ConnectionString(DatabaseData.MONGODB_CONNECTION_URL + "?uuidRepresentation=STANDARD");
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        mongoClient = MongoClients.create(clientSettings);
    }
}
