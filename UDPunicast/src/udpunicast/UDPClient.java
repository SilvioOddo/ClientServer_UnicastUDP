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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {

    public static void main(String[] args) {
        // Numero di porta del server a cui connettersi.
        int port = 2000;

        // Indirizzo del server.
        InetAddress serverAddress;

        // Socket UDP per la comunicazione.
        DatagramSocket dSocket;

        // Datagramma UDP con la richiesta da inviare al server.
        DatagramPacket outPacket;

        // Datagramma UDP di risposta ricevuto dal server.
        DatagramPacket inPacket;

        // Buffer per i dati da inviare e ricevere.
        byte[] buffer;

        // Messaggio di richiesta da inviare al server.
        String message = "RICHIESTA DATA E ORA";

        // Messaggio di risposta ricevuto dal server.
        String response;

        try {
            // Ottieni l'indirizzo del server (in questo caso, localhost).
            serverAddress = InetAddress.getLocalHost();
            System.out.println("Indirizzo del server trovato!");

            // Creazione del socket UDP.
            dSocket = new DatagramSocket();

            // Preparazione del datagramma con i dati da inviare.
            outPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, port);

            // Invio dei dati al server.
            dSocket.send(outPacket);

            // Preparazione del buffer per ricevere i dati dal server.
            buffer = new byte[256];

            // Creazione del datagramma UDP per ricevere i dati.
            inPacket = new DatagramPacket(buffer, buffer.length);

            // Ricezione dei dati dal server.
            dSocket.receive(inPacket);

            // Estrazione del messaggio di risposta dal datagramma ricevuto.
            response = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println("Il server ha inviato i dati!");
            System.out.println(response);

            // Chiusura della comunicazione.
            dSocket.close();
            System.out.println("Comunicazione chiusa!");
        } catch (UnknownHostException e) {
            System.err.println("Errore DNS: Impossibile trovare l'host specificato.");
        } catch (SocketException e) {
            System.err.println("Errore Socket: Errore nella creazione del socket.");
        } catch (IOException e) {
            System.err.println("Errore di I/O: Errore durante la comunicazione con il server.");
        }

    }

}