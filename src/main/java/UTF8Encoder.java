import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UTF8Encoder {
    public static void main(String[] args) {
        System.out.println(URLEncoder.encode("action=LOGIN" + null, StandardCharsets.UTF_8));
    }
}
