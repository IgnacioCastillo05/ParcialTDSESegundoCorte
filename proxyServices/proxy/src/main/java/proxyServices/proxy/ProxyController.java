package proxy.src.main.java.proxyServices.proxy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/proxy")

public class ProxyController {

    private final String serviceURL = System.getenv("MATH_SERVICE_URL") != null ? System.getenv("MATH_SERVICE_URL") : "http://localhost:8081";
    private final String service2URL = System.getenv("MATH_SERVICE_URL") != null ? System.getenv("MATH_SERVICE_URL") : "http://localhost:8082";

    private boolean useFirst = true;

    @GetMapping("/{function}")
    public ResponseEntity<String> proxy(
        @PathVariable String function,
        @RequestParam Map<String, String> params){
            String queryString = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

            String activeURL = useFirst ? serviceURL : service2URL;
            String fallbackURL = useFirst ? service2URL : serviceURL;

            String result = callService (activeURL);
            if (result != null){
                return ResponseEntity.ok(result);
            }

            result = callService (fallbackURL);
            if (result != null){
                useFirst =! useFirst;
                System.out.println("Se cayó. Ahora está activo: " + (useFirst ? serviceURL : service2URL));
                return ResponseEntity.ok(result);
            }
                        return ResponseEntity.status(503).body("Both services are unavailable");

            }
            
            private String callService(String targetURL){
                try {
                    URL obj = new URL(targetURL);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
                    con.setConnectTimeout(5000);
                    con.setReadTimeout(5000);

                    int responseCode = con.getResponseCode();
                    System.out.println("GET Response Code :: " + responseCode);
                    
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        System.out.println(response.toString());


                    }}catch (IOException e) {
                        System.out.println("Error connecting to service: " + e.getMessage());
                    }
                    return null;
            }

}
