package HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

class SimpleHttpClient {
    private static final Logger logger = Logger.getLogger("client_logger");
    private static  Scanner scanner;
    private HttpURLConnection con;
    private URL url;

    void connectToServer() {
        try {
            scanner= new Scanner(System.in);
            System.out.println("Welcome!\nLogin to Existing Account? (y/n):");
            String input = scanner.next();
            if (input.equals("n"))
                while(!registerNewUser()){
                    System.out.println("please try again (^.^)");
                }
            //loginExistingUser();

//            String request = "Hello";
//            //con.setRequestProperty("content-length", String.valueOf(test.length()));
//            //System.out.println(String.valueOf(test.length()));
//            // byte[] bytes = test.getBytes();
//            // con.setRequestProperty("Content-length", String.valueOf(bytes.length));
//            // con.setRequestProperty("Content-type", "text/html");
//            OutputStream out = con.getOutputStream();
//            // Declare a listener to this url
//            out.write(request.getBytes());
//            out.close();

//            con.connect();
//            System.out.println("======================================");
//            System.out.println("Connected");
//            System.out.println("======================================");
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            System.out.println(response);
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        } catch (IOException e) {
            logger.log(SEVERE, "url error");
        }

    }

    private void loginExistingUser() throws IOException {
        url = new URL("http://localhost:8080/login");
        optimizeConnection();
        con.setRequestMethod("POST");


    }

    private boolean registerNewUser() throws IOException {
        String username;
        String password;
        scanner= new Scanner(System.in);
        System.out.println("Enter your Username to register");
        username = scanner.nextLine();
        System.out.println("Enter your Password to register");
        password = scanner.nextLine();
        String dataToSend = username + "&" + password;
        url = new URL("http://localhost:8080/login");
        optimizeConnection();
        sendRequest("POST", dataToSend);
        StringBuilder response = getResponse();
        System.out.println(response);
        return response.toString().equals("Registration completed");
    }

    /**
     * optimizing necessary connection details
     *
     * @throws IOException
     */
    private void optimizeConnection() throws IOException {
        con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
    }

    /**
     * Getting a response from the server.
     *
     * @throws IOException
     */
    private StringBuilder getResponse() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    /**
     * Sending a request to the server
     *
     * @param requestMethod
     * @param dataToSend
     * @throws IOException
     */
    private void sendRequest(String requestMethod, String dataToSend) throws IOException {
        con.setRequestMethod(requestMethod);
        OutputStream out = con.getOutputStream();
        out.write(dataToSend.getBytes());
        out.close();
    }


}
