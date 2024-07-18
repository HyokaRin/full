/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlysinhvien.client_server;
import java.io.*;
import java.net.*;
import java.sql.*;
import database.myConnection;
/**
 *
 * @author Thinh Le
 */
public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is listening on port 12345");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ServerThread(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            String username = (String) inputStream.readObject();
            String password = (String) inputStream.readObject();
            System.out.println("Received username: " + username + " and password: " + password);

            boolean isAuthenticated = authenticate(username, password);
            outputStream.writeObject(isAuthenticated);
            System.out.println("Authentication result sent to client");

            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private boolean authenticate(String username, String password) {
        // Kết nối đến database và kiểm tra tài khoản
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.next();
            System.out.println("Authentication result: " + result);
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
