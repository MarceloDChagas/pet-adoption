package util.interfaces;
import java.util.List;
import java.util.Map;

public interface IFileManage {

    <T> List<T> readObjectFile(String filePath);

    <T> void writeObjectFile(String filePath, List<T> objects);

    List<String> readFile(String filePath);

    void writeFile(String filePath, String data);

    Map<String, List<String>> loadFilesToMap(String directoryPath);

    List<String> getAllFileNames(String directoryPath);
}
