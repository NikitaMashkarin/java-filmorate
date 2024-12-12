package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    @JsonSerialize(using = CustomDurationSerializer.class)
    @JsonDeserialize(using = CustomDurationDeserializer.class)
    private Duration duration;

    private static class CustomDurationDeserializer extends JsonDeserializer<Duration> {
        @Override
        public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            int minutes = p.getIntValue();
            return Duration.ofMinutes(minutes);
        }
    }

    private static class CustomDurationSerializer extends JsonSerializer<Duration> {

        @Override
        public void serialize(Duration duration, JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
            long minutes = duration.toMinutes();
            gen.writeNumber(minutes);
        }
    }
}
