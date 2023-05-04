package Sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket s = null;
        DataOutputStream dout = null;
        try {
            s = new Socket("localhost", 6666);
            dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF(readTerminal());
            dout.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
                dout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readTerminal() {
        Scanner s = new Scanner(System.in);
        System.out.println("Senden(WRITE) oder Empfangen(LIST):");
        String sendenEmpfangen = s.nextLine();
        String rückgabe;
        if (sendenEmpfangen.equals("WRITE")) {
            System.out.println("Dateiname eingeben:");
            String dataname = s.nextLine();
            System.out.println("Text eingeben:");
            String text = s.nextLine();
            s.close();
            StringBuilder sb = new StringBuilder();
            sb.append(sendenEmpfangen).append("#").append(dataname).append("#").append(text);
            rückgabe = sb.toString();
        } else if (sendenEmpfangen.equals("LIST")) {
            s.close();
            rückgabe = sendenEmpfangen;
        } else {
            System.out.println("Falsche Eingabe");
            rückgabe = "";
        }
        return rückgabe;
    }
}
