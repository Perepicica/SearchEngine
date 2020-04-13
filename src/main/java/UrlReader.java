import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlReader {
    static List<String> selecthttp() throws IOException {
        List<String> https = new ArrayList<>();
        File file = new File("src/main/resources/public/files/urls.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            https.add(line);
        }
        bufferedReader.close();
        fileReader.close();
        return https;
    }
}
