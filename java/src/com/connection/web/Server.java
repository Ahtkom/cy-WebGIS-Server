package com.connection.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server : Build Web-Server connection via TCP/IP.
 * @apiNote Read I/O stream from clients and provide methods to retreive them.
 */
public class Server {

    private ServerSocket serverSocket;

    private Socket socket;

    private int port = 8080;

    private InputStream inputStream;

    private ByteArrayOutputStream byteArrayOutputStream;


    // Constructor
    public Server() {}

    public Server(int port) {
        setPort(port);
    }
    
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void exec() {
        initialize();
        if (serverSocket != null) {
            while (true) {
                try {
                    String outputString = getOutputString();
                    log(outputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected String getOutputString() {
        String outputString = null;
        try {
            socket = serverSocket.accept();

            inputStream = socket.getInputStream();

            byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;

            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            outputString = byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return outputString;
    }

    protected void log(String string) {

    }

    protected void initialize() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void closeServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("cnaucbuacb");
                e.printStackTrace();
            }
        }
    }

    protected void close() {
        if (byteArrayOutputStream != null) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}