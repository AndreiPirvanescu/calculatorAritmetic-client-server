import java.io.*;
import java.net.*;

public class ServerOperatiiAritmetice {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Nu pot asculta pe port: 4444.");
            System.exit(1);
        }
        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Astept cerere conectare de la client ...");
                clientSocket = serverSocket.accept();
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    String operatieString;
                    while ((operatieString = in.readLine()) != null) {
                        String rezultatString = operatie(operatieString);
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println(rezultatString);
                    }
                } catch (IOException e) {
                    System.err.println("Comunicare esuata cu aplicatia client!");
                } finally {
                    if (in != null)
                        in.close();
                    if (out != null)
                        out.close();
                }
            } catch (IOException e) { // clientSocket catch
                System.err.println("Nu pot conecta aplicatia client!");
            } finally {
                if (clientSocket != null)
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                    }
            }
        }
    }

    static String operatie(String operatieString) {
        String[] operanziSiOperator = operatieString.split(" ");
        int a = (Integer.parseInt(operanziSiOperator[0]));
        String operator = operanziSiOperator[1];
        int b = (Integer.parseInt(operanziSiOperator[2]));
        int rezultat = 0;
        if (operator.equals("+")) {
            rezultat = a + b;
        } else {
            if (operator.equals("-")) {
                rezultat = a - b;
            } else {
                if (operator.equals("*")) {
                    rezultat = a * b;
                } else {
                    if (operator.equals("/")) {
                        rezultat = a / b;
                    }
                }
            }
        }
        String rezultatString = Integer.toString(rezultat);
        return rezultatString;
    }
}