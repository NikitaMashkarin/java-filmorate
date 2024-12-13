package ru.yandex.practicum.filmorate.dataСonversion;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;
import java.time.Duration;

public class CustomDurationSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration duration, JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
        long minutes = duration.toMinutes();
        gen.writeNumber(minutes);
    }
}