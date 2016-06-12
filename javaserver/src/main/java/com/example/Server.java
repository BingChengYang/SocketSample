package com.example;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import java.awt.Dimension;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.text.SimpleDateFormat;

import javax.swing.*;
/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable {
    private Thread thread;
    private ServerSocket servSock;
    private JTextArea textArea;
    public Server(){
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setPreferredSize(new Dimension(500,300));
        JScrollPane scrollPane = new JScrollPane(this.textArea);


        this.add(scrollPane);

        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waitting to connect......");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
        setVisible(true);
    }

    @Override
    public void run(){
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();

                System.out.println("Connected!!");

                // Transfer data
                byte[] b = new byte[1024];
                int length;

                length = in.read(b);
                String s = new String(b);
                this.textArea.append("The result from App is" + s +"\n");
                //System.out.println("The result from App is" + s);

            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
