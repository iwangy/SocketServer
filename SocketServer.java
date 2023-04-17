import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.Random;

public class SocketServer {

  static ExecutorService exec = Executors.newFixedThreadPool(2);
  static int port = 80;
  static String[] quotes = {
    "Life is like a box of chocolates. You never know what you're gonna get. - Forrest Gump \n",
    "My mama always said, 'Life was like a box of chocolates. You never know what you're gonna get.' - Forrest Gump \n",
    "Stupid is as stupid does. - Forrest Gump \n",
    "Run, Forrest, run! - Forrest Gump \n",
    "I'm not a smart man, but I know what love is. - Forrest Gump \n",
    "Life moves pretty fast. If you don't stop and look around once in a while, you could miss it. - Ferris Bueller \n",
    "Hello. My name is Inigo Montoya. You killed my father. Prepare to die. - The Princess Bride \n",
    "As you wish. - The Princess Bride \n",
    "Inconceivable! - The Princess Bride \n",
    "Death cannot stop true love. All it can do is delay it for a while. - The Princess Bride \n"
  };
  static Random rnd = new Random();

  public static void main(String... args) {
    exec.submit(() -> tcp());
    exec.submit(() -> udp());
  }

  public static void handleRequestTCP(Socket socket) {
    int quoteIndex = rnd.nextInt(quotes.length);
    try {
      byte[] quoteBytes = quotes[quoteIndex].getBytes("UTF-8");
      OutputStream out = socket.getOutputStream();
      out.write(quoteBytes);
      System.out.println("Quote Sent");
      out.close();
      socket.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void tcp() {
    try (ServerSocket server = new ServerSocket(port)) {
      Socket socket = null;
      System.out.println("TCP: Server is listening on port " + port);

      while((socket = server.accept()) != null) {
        System.out.println("TCP: Accepted client request");
        final Socket threadSocket = socket;
        handleRequestTCP(threadSocket);
      }
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void udp() {
    try {
      while (true) {
        int quoteIndex = rnd.nextInt(quotes.length);
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket request = new DatagramPacket(new byte[512], 1);
        socket.receive(request);
        System.out.println("UDP: Server has received datagram");

        InetAddress clientAddress = request.getAddress();
        int clientPort = request.getPort();

        String quote = quotes[quoteIndex];
        byte[] buffer = quote.getBytes();

        DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
        System.out.println("UDP: Quote Sent");
        socket.send(response);
        socket.close();
      }
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
  }

}
