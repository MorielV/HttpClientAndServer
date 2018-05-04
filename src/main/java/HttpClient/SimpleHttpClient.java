package HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import static java.util.logging.Level.SEVERE;

public class SimpleHttpClient {
    private static final Logger logger = Logger.getLogger("client_logger");

    public static void main(String[] args) {
        URL url;
        try {
            url = new URL("http://localhost:8080/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setUseCaches(false);
            String request = "Hello";
            //con.setRequestProperty("content-length", String.valueOf(test.length()));
            //System.out.println(String.valueOf(test.length()));
            // byte[] bytes = test.getBytes();
            // con.setRequestProperty("Content-length", String.valueOf(bytes.length));
            // con.setRequestProperty("Content-type", "text/html");
            OutputStream out = con.getOutputStream();
            // Declare a listener to this url
            out.write(request.getBytes());
            out.close();

//            con.connect();
//            System.out.println("======================================");
//            System.out.println("Connected");
//            System.out.println("======================================");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(response);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        } catch (IOException e) {
            logger.log(SEVERE, "url error");
        }

    }


}
