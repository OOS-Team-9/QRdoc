package project.controller.qr_buffimage_generator;

import com.google.zxing.WriterException;
import project.model.information.Information;

import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * 모든 qr buffer image generator의 공통 부분
 */
public interface QrBuffImageGenerator {

    /**
     * QR코드 bit matrix를 만드는 함수
     * @param info              information
     * @param qrWidth           buffer image의 폭
     * @param qrHeight          buffer image의 높이
     * @return                  buffer image
     * @throws WriterException  bit matrix 생성 중 발생할 수 있는 에러
     * @throws IOException
     */
    BufferedImage generate(Information info,
                           int qrWidth,
                           int qrHeight) throws WriterException;
}
