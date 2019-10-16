import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
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
            System.out.println("Server started.");
            InetSocketAddress address = new InetSocketAddress(host, port);
            serverSocket.bind(address);
            while (serverSocket.isBound()) {
                System.out.println("Waiting for input.");
                Socket socket = serverSocket.accept();
                this.input = socket.getInputStream();
                this.output = socket.getOutputStream();
                System.out.println("Accepted from " + socket.getInetAddress());
                String recievedLine = ReceiveData();
                System.out.println("Recieved message from client: " + recievedLine);
                HashMap<String, Integer> countedWords = WordCounter.CountWords(recievedLine);
                System.out.println("Counted words: " + countedWords);
                SendMessage(countedWords.toString().getBytes());
                System.out.println("Data sent to client.");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void SendMessage(byte[] msg) throws IOException {
        this.output.write(msg);
        this.output.flush();
    }

    private String ReceiveData() throws IOException {   //Gets data from client
        byte[] buf = new byte[1024 * 32];
        var length = input.read(buf);
        if (length != -1){
            String s = new String(buf, 0, length);
            return s;
        }
        return "Message is not gotten.";
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Server host is: ");
        String host = scan.nextLine();
        System.out.print("Server port is: ");
        int port = scan.nextInt();
        Server server = new Server(host, port);
        server.Listen();
    }
}
