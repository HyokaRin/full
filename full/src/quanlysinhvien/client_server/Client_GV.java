/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlysinhvien.client_server;
import java.io.*;
import java.net.*;
import javax.swing.*;
/**
 *
 * @author Thinh Le
 */
public class Client_GV {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new quanlysinhvien.UI_DangNhap().setVisible(true);
        });
    }

    public static boolean login(String username, String password) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
             ObjectInputStream objectInputStream = new ObjectInputStream(input)) {

            // Send login request
            objectOutputStream.writeObject("login");
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);

            // Receive response
            String response = (String) objectInputStream.readObject();
            return response.equals("success");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
