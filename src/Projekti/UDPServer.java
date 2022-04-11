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

