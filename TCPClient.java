import java.net.*;
import java.io.*;

public class TCPClient {
  // private static final Logger LOGGER = Logger.getLogger(TCPClient.class.getName());
  public static void main(String... args) {
    // host: localhost
    // port: 80
    String host = args[0];
    int port = Integer.valueOf(args[1]);
    
    System.out.println("TCP: Connecting to " + host + " on port " + port);

    try {
      Socket sock = new Socket(host, port);
      InputStream in = sock.getInputStream();
      OutputStream out = sock.getOutputStream();

      System.out.println("TCP: Connected to " + host + " on port " + port + "\n");

      int readChar = 0;
      while ((readChar = in.read()) != -1) {
          System.out.write(readChar);
          System.out.flush();
      }

      sock.close();
      in.close();
      out.close();
    }
    catch (IOException ex){
      ex.printStackTrace();
    }

  }
}
