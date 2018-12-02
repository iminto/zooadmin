package com.baicai.util;

import java.io.*;
import java.net.Socket;

public class TelnetUtil {
    Socket socket;
    InputStream inputStream;//读命令的流
    PrintStream pStream; //写命令
    String servername;
    int port;

    public TelnetUtil(String serverName, int port){
        this.servername = serverName;
        this.port = port;
    }

    public void connect() {
        try {
            this.socket = new Socket(this.servername, this.port);
            inputStream = socket.getInputStream();
            pStream = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error in creating Socket.");
        }
    }

    public String writeAndRead(String s) {
        if(socket==null){
            connect();
        }
        pStream.print(s); //写命令
        pStream.flush(); //将命令发送到telnet Server
        byte[] b = new byte[1024];
        int size = -1;
        StringBuilder sb = new StringBuilder(300);
        while (true) { //读取Server返回来的数据
            try {
                size = inputStream.read(b);
                if (-1 != size) {
                    sb.append(new String(b, 0, size));
                } else {
                    break;
                }
            } catch (IOException e) {
            }

        }
        return sb.toString();

    }

    public static void main(String[] args) throws IOException {
        TelnetUtil telnet = new TelnetUtil("localhost",2181);
        telnet.connect();
        System.out.println(telnet.writeAndRead("envi"));
        telnet.close();
    }

    public void close(){
        if(pStream!=null){
            pStream.close();
        }
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("关闭远程telenet失败");
            }
        }
    }
}
