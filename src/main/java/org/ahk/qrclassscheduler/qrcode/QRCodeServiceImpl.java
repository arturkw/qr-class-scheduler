package org.ahk.qrclassscheduler.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
@Profile("prd")
public class QRCodeServiceImpl implements QRCodeService {
    private static final String X = "X";
    private static final String O = "O";

    @SneakyThrows
    public String encode(String data) {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        return bitMatrix.toString(X, O);
    }

    @SneakyThrows
    public String decode(String data) {
        BitMatrix matrix = BitMatrix.parse(data, X, O);
        QRCodeReader reader = new QRCodeReader();
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
        Result decode = reader.decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage))));
        return decode.getText();
    }

}
