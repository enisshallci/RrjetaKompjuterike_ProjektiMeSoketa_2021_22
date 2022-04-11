package Projekti;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPServer {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];

    public UDPServer(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend() {
        while(true) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress innetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String messageFromClient = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Message from client: " + messageFromClient);
                datagramPacket = new DatagramPacket(buffer, buffer.length, innetAddress, port);
                datagramSocket.send(datagramPacket);


                if(messageFromClient.startsWith("le lexo")){
                    String[] rarr = messageFromClient.split(" ",5);

                    String[] rcmd = {"",""};
                    rcmd[0] = rarr[1];
                    rcmd[1]= rarr[2];

                    try {
                        File file = new File("C:\\RrjetaKompjuterike_Projekti\\src\\Projekti\\" +rcmd[1]);
                        file.setReadable(true);
                        Scanner myReader = new Scanner(file);
                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                            System.out.println(data);

                            datagramPacket = new DatagramPacket(buffer, buffer.length, innetAddress, port);
                            datagramSocket.send(datagramPacket);

                        }
else if(messageFromClient.startsWith("le shkruaj")) {

                    String[] warr = messageFromClient.split(" ",5);

                    String[] wcmd = {"",""}; wcmd[0] = warr[1]; wcmd[1]= warr[2];

                    String[] writeMessage = messageFromClient.split("'",2);
                    {
                        try {
                            FileWriter myWriter = new FileWriter("C:\\RrjetaKompjuterike_Projekti\\src\\Projekti\\" +wcmd[1]);
                            myWriter.write(writeMessage[1]);
                            myWriter.close();
                            System.out.println("Successfully wrote to the file. \n");

                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                        }
                    }
                }

            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(4321);
        System.out.println("Server Started....");
        System.out.println("Waiting for Clients");
        UDPServer server = new UDPServer(datagramSocket);
        server.receiveThenSend();

    }
}
