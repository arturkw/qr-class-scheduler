package org.ahk.qrclassscheduler.qrcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class QRCodeServiceImplTest {

    @Autowired
    QRCodeService qrCodeService;

    @Test
    @DisplayName("encode and decode methods are reversible")
    void decode_encode() {
        String data = "some date 123";
        String encoded = qrCodeService.encode(data);
        String decodedData = qrCodeService.decode(encoded);
        assertEquals(data, decodedData);
    }

}