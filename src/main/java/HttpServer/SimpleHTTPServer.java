package HttpServer;

import Stock;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class SimpleHTTPServer extends Thread {
    private static final int N_THREADS = 10000;
    private static  final JavaDB db = new JavaDB();
    public static void main(String args[]) throws IOException {



        //////////////////////////////////////////////



        ////////////////////////////////////////////////

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        HttpContext context = server.createContext("/", new myHandler());
        server.createContext("/login", new loginHandler());
        server.createContext("/info", new InfoHandler());
        server.createContext("/get", new GetHandler());
        server.setExecutor(Executors.newFixedThreadPool(N_THREADS)); // creates a concurrent executor
        server.start();
    }

    static class loginHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            StringBuilder request = getRequest(t);
            System.out.println("==========================");
            System.out.println(request);
            System.out.println("==========================");
            int index = request.indexOf("&");
            String username = request.substring(0,index) ;
            String password = request.substring(index+1,request.length());
            System.out.println("USERNAME is :"+username+"PASSWORD IS :"+password);

            if(db.IsUsernameExists(username)){
                String response = "username exists";
                sendResponse(response,t);
            }
            else{
                db.insert(username,password);
                String response = "Registration completed";
                sendResponse(response,t);
            }
        }


    }

    static class myHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            StringBuilder request = getRequest(t);
            System.out.println("==========================");
            System.out.println(request);
            System.out.println("==========================");
            String[] strArray = request.toString().split(",");
            ArrayList<StockState> stateList = new ArrayList<>();
            for (String aStrArray : strArray) {
                String[] nameAndVal = aStrArray.split(":");
                String name = nameAndVal[0];
                double value = Double.parseDouble(nameAndVal[1]);
                Stock stock = new Stock(name);
                StockState state = new StockState(stock, value);
                stateList.add(state);
            }
            int id = db.insert(stateList);
            int index = request.indexOf("&");
            String username = request.substring(0,index) ;
            String password = request.substring(index+1,request.length());
            System.out.println("USERNAME is :"+username+"PASSWORD IS :"+password);

            if(db.IsUsernameExists(username)){
                String response = "username exists";
                sendResponse(response,t);
            }
            else{
                db.insert(username,password);
                String response = "Registration completed";
                sendResponse(response,t);
            }
        }
    }
    private static void sendResponse(String response, HttpExchange t) throws IOException {
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private static StringBuilder getRequest(HttpExchange t) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
        String inputLine;
        StringBuilder request = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            request.append(inputLine);
        }
        in.close();
        return request;
    }

    static class InfoHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "Use /get to download a PDF";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class GetHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            // add the required response header for a PDF file
            Headers h = t.getResponseHeaders();
            h.add("Content-Type", "application/pdf");

            // a PDF (you provide your own!)
            File file = new File("c:/temp/doc.pdf");
            byte[] bytearray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytearray, 0, bytearray.length);

            // ok, we are ready to send the response.
            t.sendResponseHeaders(200, file.length());
            OutputStream os = t.getResponseBody();
            os.write(bytearray, 0, bytearray.length);
            os.close();
        }
    }
}