package org.ahk.qrclassscheduler.qrcode;

public interface QRCodeService {
    String encode(String data);

    String decode(String data);
}
