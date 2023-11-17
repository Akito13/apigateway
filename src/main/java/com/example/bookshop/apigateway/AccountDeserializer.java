package com.example.bookshop.apigateway;

import com.example.bookshop.apigateway.dto.AccountDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class AccountDeserializer extends StdDeserializer<AccountDto> {

    public AccountDeserializer() {
        this(null);
    }

    public AccountDeserializer(Class<?> wc) {
        super(wc);
    }

    @Override
    public AccountDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        AccountDto accountDto = new AccountDto();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);


        return null;
    }
}
