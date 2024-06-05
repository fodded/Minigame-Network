package me.fodded.common.data.statistics.storage.impl.player;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UUIDMongoCodec implements Codec<UUID> {

    @Override
    public void encode(BsonWriter writer, UUID uuid, EncoderContext encoderContext) {
        if (uuid != null) {
            writer.writeString(uuid.toString());
        }
    }

    @Override
    public UUID decode(BsonReader reader, DecoderContext decoderContext) {
        return UUID.fromString(reader.readString());
    }

    @Override
    public Class<UUID> getEncoderClass() {
        return UUID.class;
    }
}