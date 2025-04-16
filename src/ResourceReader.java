import java.io.InputStream;
import java.io.IOException;

public class ResourceReader {
    public static String readResourceToString(String resourcePath) throws IOException {
        InputStream inputStream = ResourceReader.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + resourcePath);
        }
        StringBuffer content = new StringBuffer();
        try {
            int ch;
            while ((ch = inputStream.read()) != -1) {
                content.append((char) ch);
            }
            return content.toString();
        } catch (IOException e) {
            throw new IOException("Error reading resource: " + resourcePath);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ignored) {}
        }
    }
}