package org.ahk.qrclassscheduler.qrcode;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevQRCodeServiceImpl implements QRCodeService {
    @SneakyThrows
    public String encode(String data) {
        return data;
    }

    @SneakyThrows
    public String decode(String data) {
        return data;
    }

}
