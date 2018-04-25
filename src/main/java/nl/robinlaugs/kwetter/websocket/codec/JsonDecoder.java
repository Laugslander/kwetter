package nl.robinlaugs.kwetter.websocket.codec;

import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public abstract class JsonDecoder<T> implements Decoder.Text<T> {

    private final Class<T> clazz;
    private final Gson gson;

    public JsonDecoder(Class<T> clazz) {
        this.clazz = clazz;
        gson = new Gson();
    }

    @Override
    public T decode(String json) {
        return gson.fromJson(json, clazz);
    }

    @Override
    public boolean willDecode(String json) {
        return true;
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
