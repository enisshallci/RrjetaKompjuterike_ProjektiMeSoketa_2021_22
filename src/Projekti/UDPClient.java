package Projekti;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient {

    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    public UDPClient(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenReceive() {

        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                String messageToSend = scanner.nextLine();
                buffer = messageToSend.getBytes(StandardCharsets.UTF_8);
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 4321 );
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("The server says you said: " + messageFromServer);


}catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {

        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("192.168.178.128");
        UDPClient client = new UDPClient(datagramSocket, inetAddress);
        System.out.println("Send datagram packets to a server.");
        client.sendThenReceive();
    }

}

