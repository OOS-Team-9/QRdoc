package project.controller.bitmatrix_generator;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import project.model.information.Information;

import java.io.IOException;


/**
 * 모든 bit matrix generator의 공통 분모
 */
public abstract class BitMTgenerator {

    /**
     * QR코드 bit matrix를 만드는 함수
     * @param info              변환할 문자열 정보
     * @param qrWidth           QR코드 가로 길이
     * @param qrHeight          QR코드 세로 길이
     * @return                  bitmatrix 객체
     * @throws WriterException
     * @throws IOException
     */
    abstract BitMatrix generate(Information info, int qrWidth, int qrHeight)throws WriterException;
}
