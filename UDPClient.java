import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
  public static void main(String... args) {
    // host: localhost
    // port: 80
    String host = args[0];
    int port = Integer.valueOf(args[1]);

    System.out.println("UDP: Connecting to " + host + " on port " + port);

    try {
      InetAddress address = InetAddress.getByName(host);
      DatagramSocket socket = new DatagramSocket();
      DatagramPacket request = new DatagramPacket(new byte[512], 1, address, port);
      socket.send(request);
      System.out.println("UDP: Connected to " + host + " on port " + port + "\n");

      byte[] buffer = new byte[512];
      DatagramPacket response = new DatagramPacket(buffer, buffer.length);
      socket.receive(response);

      String quote = new String(buffer, 0, response.getLength());
      System.out.println(quote);
    
      socket.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
