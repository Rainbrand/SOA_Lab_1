import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public int port;
    public String host;
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    Server(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void Listen(){
        try {
            ServerSocket serverSocket = new ServerSocket();
            System.out.println("Server started, listening.");
            InetSocketAddress address = new InetSocketAddress(host, port);
            serverSocket.bind(address);
            while (serverSocket.isBound()) {
                Socket socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();
                System.out.println("Accepted from " + socket.getInetAddress());
                String line = ReceiveData();
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String ReceiveData() throws IOException {
        byte[] buf = new byte[24];
        var length = input.read(buf);
        String s = new String(buf, 0, length);
        return s;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Host is: ");
        String host = scan.nextLine();
        System.out.print("Port is: ");
        int port = scan.nextInt();
        Server server = new Server(host, port);
        server.Listen();
    }
}
