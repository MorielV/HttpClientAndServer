package HttpServer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class SimpleHTTPServer extends Thread {
    public static void main(String args[]) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new myHandler());
        server.createContext("/info", new InfoHandler());
        server.createContext("/get", new GetHandler());
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool()); // creates a concurrent executor
        server.start();
    }

    static class myHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader( t.getRequestBody()));
            String inputLine;
            StringBuilder request = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                request.append(inputLine);
            }
            in.close();
            System.out.println("==========================");
            System.out.println(request);
            System.out.println("==========================");
            String response = "World";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
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