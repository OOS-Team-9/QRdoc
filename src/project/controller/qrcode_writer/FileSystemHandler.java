package project.controller.qrcode_writer;

import java.io.File;

public abstract class FileSystemHandler {

    /**
     * 디렉토리가 존재하는지 확인하는 함수
     * @param directory
     * @return
     */
    protected static boolean isDirectoryExisted(String directory) {
        return new File(directory).exists();
    }

    /**
     * 디렉토리를 생성하는 함수
     * (isDirectoryExisted 함수로 디렉토리 존재하지 않음을 확인한 후에 호출!)
     * @param directory
     * @return
     */
    protected static boolean makeDirectory(String directory) {
        return new File(directory).mkdirs();
    }

    /**
     * 파일이 존재하는지 확인하는 함수
     * @param directory
     * @param fileName
     * @return
     */
    protected static boolean isFileExisted(String directory, String fileName) {
        return new File(directory + fileName).exists();
    }

    /**
     * 파일을 삭제하는 함수
     * (isFileExisted 함수로 파일이 존재하지 않음을 확인한 후에 호출!)
     * @param directory
     * @param fileName
     * @return
     */
    protected static boolean deleteFile(String directory, String fileName) {
        return new File(directory + fileName).delete();
    }
}
