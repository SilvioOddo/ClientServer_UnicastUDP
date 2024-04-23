/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpunicast;

/**
 *
 * @author Luca Brunori - Silvio Oddo - Gabriele Silla
 */

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class UDPServer {
    public static void main(String[] args) {
        // La porta del server UDP.
        int port = 2000;

        // Socket UDP per gestire la comunicazione.
        DatagramSocket dSocket;

        // Datagramma UDP ricevuto dal client.
        DatagramPacket inPacket;

        // Datagramma UDP di risposta da inviare al client.
        DatagramPacket outPacket;

        // Buffer per il contenuto del messaggio ricevuto.
        byte[] bufferIn, bufferOut;

        // Messaggi in input e output.
        String messageIn, messageOut;

        // Data e ora correnti.
        Date d;

        try {
            // Creazione del socket e associazione alla porta specificata.
            dSocket = new DatagramSocket(port);
            System.out.println("Apertura porta in corso!");

            while (true) {
                System.out.println("Server in ascolto sulla porta " + port + "!\n");
                bufferIn = new byte[256];

                // Creazione di un datagramma UDP per ricevere i dati.
                inPacket = new DatagramPacket(bufferIn, bufferIn.length);

                // Ricezione dei dati dal client. Il thread si blocca finché non arrivano i dati.
                dSocket.receive(inPacket);

                // Recupero dell'indirizzo IP e della porta UDP del client.
                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();

                // Conversione dei byte ricevuti in una stringa.
                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("SONO IL CLIENT " + clientAddress + ":" + clientPort + "> " + messageIn);

                // Preparazione del messaggio di risposta.
                messageOut = "ciao";
                bufferOut = messageOut.getBytes();

                // Creazione di un datagramma UDP per inviare la risposta al client.
                outPacket = new DatagramPacket(bufferOut, bufferOut.length, clientAddress, clientPort);

                // Invio della risposta al client.
                dSocket.send(outPacket);
                System.out.println("Risposta inviata!");
            }
        } catch (BindException e) {
            System.err.println("Errore: Porta già in uso");
        } catch (SocketException e) {
            System.err.println("Errore Socket");
        } catch (IOException e) {
            System.err.println("Errore di I/O");
        }
    }
}