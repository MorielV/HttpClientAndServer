package HttpClient;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

class SimpleHttpClient {
    private static Logger logger;
    private static Scanner scanner;
    private HttpURLConnection con;
    private URL url;


    SimpleHttpClient() {
        logger = Logger.getLogger("client_logger");
    }

    void connectToServer(){
        boolean tryAgain = true;
        while (tryAgain) {
            tryAgain = false;
            scanner = new Scanner(System.in);
            System.out.println("Welcome!\nWhat you wish to do:(choose between 1 to 4) \n" +
                    "1. Register your stock portfolio\n" +
                    "2. Update your stock portfolio\n" +
                    "3. Check your current portfolio value\n" +
                    "4. Ask for simple buying recommendations");
            String input = scanner.next();
            switch (input) {
                case "1":
                    int clientId = handleRegister();   //clientId is the response from the server
                    System.out.println("Registration Complete, your Client ID is" + clientId);
                    break;
                case "2": //TODO:
                    break;
                case "3"://TODO:
                    break;
                case "4"://TODO:
                    break;
                default:
                    tryAgain = true;
                    System.out.println("Wrong input , please try again (^.^)");
            }
        }
//        if (input.equals("n"))
//            while (!registerNewUser()) {
//                System.out.println("please try again (^.^)");
//            }
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
    }

    private int handleRegister() {
        scanner = new Scanner(System.in);
        System.out.println("Enter All your stocks states(name:value) separated by comma\n" +
                "(for example - Fyber:12.5,Teva:3.2, ...)");

        String dataToSend = scanner.nextLine();
//        scanner = new Scanner(System.in);
//        System.out.println("Enter your Username to register");
//        username = scanner.nextLine();
//        System.out.println("Enter your Password to register");
//        password = scanner.nextLine();
//        String dataToSend = username + "&" + password;
        try {
            url = new URL("http://localhost:8080/");
            optimizeConnection();
            sendRequest("POST", dataToSend);
            StringBuilder response = getResponse();
            System.out.println(response);
            return Integer.parseInt(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
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
        System.out.println("receiving data!!");
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
        System.out.println("sending data!!");
        con.setRequestMethod(requestMethod);
        OutputStream out = con.getOutputStream();
        out.write(dataToSend.getBytes());
        out.close();
    }


}
