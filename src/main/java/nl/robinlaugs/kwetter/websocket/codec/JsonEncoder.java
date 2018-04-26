package nl.robinlaugs.kwetter.websocket.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.robinlaugs.kwetter.websocket.adapter.LocalDateTimeAdapter;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.time.LocalDateTime;

public abstract class JsonEncoder<T> implements Encoder.Text<T> {

    private final Gson gson;

    protected JsonEncoder() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @Override
    public String encode(T object) {
        return gson.toJson(object);
    }

    @Override
    public void init(EndpointConfig config) {
        // Unused
    }

    @Override
    public void destroy() {
        // Unused
    }

}
