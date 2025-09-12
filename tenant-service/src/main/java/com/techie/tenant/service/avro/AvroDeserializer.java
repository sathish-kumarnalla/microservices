package com.techie.tenant.service.avro;

import com.techie.event.model.events.UserCreatedEvent;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AvroDeserializer {

    public UserCreatedEvent deserialize(byte[] data) {
        try {
            DatumReader<UserCreatedEvent> reader = new SpecificDatumReader<>(UserCreatedEvent.class);
            Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            return reader.read(null, decoder);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize Avro message", e);
        }
    }
}
