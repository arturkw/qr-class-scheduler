package org.ahk.qrclassscheduler.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String toJson(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    @SneakyThrows
    public <T> T fromString(String s, Class<T> t) {
        return objectMapper.readValue(s, t);
    }

}
