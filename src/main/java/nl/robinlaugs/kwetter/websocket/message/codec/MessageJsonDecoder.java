package nl.robinlaugs.kwetter.websocket.message.codec;

import nl.robinlaugs.kwetter.api.dto.MessageDto;
import nl.robinlaugs.kwetter.websocket.codec.JsonDecoder;

public class MessageJsonDecoder extends JsonDecoder<MessageDto> {

    public MessageJsonDecoder() {
        super(MessageDto.class);
    }

}
