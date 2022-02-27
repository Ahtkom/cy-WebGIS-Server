package com.connection.web;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    InetAddress serverIP;
    int serverPort;

    Socket socket;
    OutputStream outputStream;

}
