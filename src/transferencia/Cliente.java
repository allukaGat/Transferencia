package transferencia;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Cliente {

    public static void main(String[] args) {
    final String HOST = "127.0.0.1";
    final int PUERTO = 5000;
    
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
    int result = fileChooser.showOpenDialog(null);
    if (result != JFileChooser.APPROVE_OPTION) {
        return;
    }
    File selectedFile = fileChooser.getSelectedFile();
    String filename = selectedFile.getAbsolutePath();
    try (Socket sc = new Socket(HOST, PUERTO)) {
        DataInputStream in = new DataInputStream(sc.getInputStream());
        DataOutputStream out = new DataOutputStream(sc.getOutputStream());
        
        out.writeUTF(selectedFile.getName());
        enviarArchivo(sc, selectedFile.getAbsolutePath());
        
        System.out.println("Archivo enviado correctamente");
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
private static void enviarArchivo(Socket socket, String nombreArchivo) throws IOException {
    try (FileInputStream fileInputStream = new FileInputStream(nombreArchivo);
         BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {

        byte[] buffer = new byte[1024];
        int bytesLeidos;

        while ((bytesLeidos = fileInputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesLeidos);
        }

        bos.flush(); 
    }
}
}