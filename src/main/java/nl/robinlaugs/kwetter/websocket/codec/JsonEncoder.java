package nl.robinlaugs.kwetter.websocket.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public abstract class JsonEncoder<T> implements Encoder.Text<T> {

    private final Gson gson;

    protected JsonEncoder() {
        gson = new GsonBuilder().create();
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
