import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientOperanziOperator {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            socket = new Socket("127.0.0.1", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Nu pot realiza conexiunea la 127.0.0.1 !");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Nu pot realiza conexiuni I/O cu 127.0.0.1 !");
            System.exit(1);
        }
        Scanner consola = new Scanner(System.in);
        boolean seContinuaCitirea = true;
        while(seContinuaCitirea) {
            System.out.print("Introdu operatia: ");
            String primulCuvant = consola.next();
            if(primulCuvant.equals("exit")) {
                seContinuaCitirea = false;
                continue;
            }
            String operator = consola.next();
            String alTreileaCuvant = consola.next();
            boolean ok = false;
            while(ok == false) {
                try{
                    ok = true;
                    int primulNumar = Integer.parseInt(primulCuvant);
                    int alDoileaNumar = Integer.parseInt(alTreileaCuvant);
                    if(operator.equals("+") == false && operator.equals("-") == false &&
                    operator.equals("*") == false && operator.equals("/") == false) {
                        throw new OperatorGresitExceptie();
                    }
                } catch (NumberFormatException e) {
                    ok = false;
                    System.out.print("Introdu din nou operatia cu operanzi int: ");
                    consola = new Scanner(System.in);
                    primulCuvant = consola.next();   
                    operator = consola.next();
                    alTreileaCuvant = consola.next();
                } catch (OperatorGresitExceptie e) {
                    ok = false;
                    System.out.print("Introdu din nou operatia cu alt operator: ");
                    consola = new Scanner(System.in);
                    primulCuvant = consola.next();
                    operator = consola.next();
                    alTreileaCuvant = consola.next();
                }
            }
            String operatie = primulCuvant + " " + operator + " " + alTreileaCuvant;
            out.println(operatie);
            String rezultat = in.readLine();
            System.out.println("Operatie: " + operatie);
            System.out.println("Rezultat: " + rezultat);
        }
        out.close();
        in.close();
        socket.close();
        consola.close();
    }
}