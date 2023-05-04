package Sockets;

import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket sock = new ServerSocket(6666);
            System.out.println("Echo-Server wartet....");
            int noRequests = 0;
            while (noRequests < 1000) {
                Socket s = sock.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String str = (String) dis.readUTF();
                System.out.println(str);
                FileHandling fh = new FileHandling(str);
                String[] string = str.split("#");
                if (string[0].equals("WRITE")) {
                    fh.safeFile();
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Dateigroeße: ").append(fh.getLength()).append(" bytes").append("\n");
                    out.write(sb.toString());
                    out.flush();
                }
                if (string[0].equals("LIST")) {
                    String result = fh.getList();
                    System.out.println(result);
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                    out.write(result);
                    out.flush();
                }

                noRequests++;
                s.close();
            }
            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
