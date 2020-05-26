package project.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 파일 또는 디렉토리 정보를 저장 및 관리하는 클래스
 */
public class MyPath {
    //파일 정보
    Path path;
    String pathString;
    String directory;
    String fileName;
    String fileType;

    /**
     * 복사 생성자
     *
     * @param other
     * @throws IOException
     */
    public MyPath(MyPath other) throws IOException {
        this(other.path);
    }

    /**
     * 생성자(Path 객체로 파일 정보 초기화)
     *
     * @param path
     * @throws IOException 입력 받은 Path 객체가 잘못된 경우
     */
    public MyPath(Path path) throws IOException {
        this.path = path;
        setFileAttributeString(path);
    }

    /**
     * 생성자(경로 문자열 로 파일 정보 초기화)
     *
     * @param pathString
     * @throws IOException
     */
    public MyPath(String pathString) throws IOException {
        try {
            this.path = Paths.get(pathString);  //끝 문자가 "\"인 경우, 삭제
            setFileAttributeString(path);
        } catch (InvalidPathException w) {
            throw new IOException("MyPath::parsePath ERROR: " +
                    "[ " + path.toString() + " ]는 유효한 파일 경로가 아닙니다.");
        }
    }

    /**
     * Path 객체에서 파일의 정보를 추출하는 함수
     *
     * @param path
     * @throws IOException
     */
    private void setFileAttributeString(Path path) throws IOException {
        Pattern pattern = Pattern.compile("(.*)\\\\(.*$)");
        Matcher matcher = pattern.matcher(path.toString());
        //파일, 디렉토리 중 하나인 경우
        if (matcher.find()) {
            matcher.matches();
            //일단 디렉토리로 간주하고 초기화
            this.pathString = matcher.group(0);
            this.directory = matcher.group(0);
            this.fileName = null;
            this.fileType = null;

            pattern = Pattern.compile("(.*)\\\\(.*\\.(.*$))");
            matcher = pattern.matcher(path.toString());
            //파일인 경우
            if (matcher.find()) {
                matcher.matches();
                this.pathString = matcher.group(0);
                this.directory = matcher.group(1);
                this.fileName = matcher.group(2);
                this.fileType = matcher.group(3);
            }
        }
        //파일, 디렉토리 둘 다 아닌 경우
        else{
            throw new IOException("MyPath::parsePath ERROR: " +
                    "[ " + path.toString() + " ]는 유효한 파일 경로가 아닙니다.");
        }
    }

    /**
     * 파일 또는 디렉토리가 존재하는지 확인하는 함수
     *
     * @return 존재하면 true, 그렇지 않으면 false
     */
    public boolean directoryExists() {
        return new File(directory).exists();
    }

    public boolean fileExists() {
        return new File(pathString).exists();
    }

    /**
     * 디렉토리를 생성하는 함수
     * (exists 함수를 호출하여, 해당 디렉토리가 존재하지 않음을 확인한 후에 이 함수를 호출해야 함.)
     *
     * @return
     */
    public boolean makeDirectory() {
        return new File(directory).mkdirs();
    }

    /**
     * 파일을 삭제하는 함수
     * (exists 함수를 호출하여, 해당 디렉토리가 존재하지 않음을 확인한 후에 이 함수를 호출해야 함.)
     *
     * @return
     */
    public boolean deleteFile() {
        return new File(pathString).delete();
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getPathString() {
        return pathString;
    }

    public void setPathString(String pathString) {
        this.pathString = pathString;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
