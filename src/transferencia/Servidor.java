package transferencia;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        ServerSocket server;
        Socket connection;
        DataOutputStream output;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        byte[] receivedData;
        int in;
        String file;

        final int PUERTO = 5000;

        try {
            
            server = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado");

            while (true) {
                
                connection = server.accept();
                System.out.println("Cliente conectado");
              
                receivedData = new byte[1024];
                bis = new BufferedInputStream(connection.getInputStream());
                DataInputStream dis = new DataInputStream(connection.getInputStream());

                
                file = dis.readUTF();
                file = file.substring(file.indexOf('\\') + 1, file.length());
                
                bos = new BufferedOutputStream(new FileOutputStream(file));
          
                while ((in = bis.read(receivedData)) != -1) {
                    bos.write(receivedData, 0, in);
                }
               output = new DataOutputStream(connection.getOutputStream());
                output.writeUTF("Archivo recibido correctamente");

                
                bos.close();
                dis.close();
                connection.close();
                System.out.println("Cliente desconectado");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

