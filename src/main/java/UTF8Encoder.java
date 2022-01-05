import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UTF8Encoder {
    public static void main(String[] args) {
        System.out.println(URLEncoder.encode("10.244.0.1/", StandardCharsets.UTF_8));
    }
}
