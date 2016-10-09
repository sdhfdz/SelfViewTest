package com.sdh.mvptest.bean;

import android.icu.util.IslamicCalendar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by sdh on 2016/10/9.
 */
public class TcpSocketClient {
    // 服务端地址
    private String serverIp = "172.27.35.11";
    // 服务端端口号
    private int serverPort = 9999;
    // 套接字
    private Socket mSocket = null;
    // 缓冲区读取
    private BufferedReader in = null;
    // 字符打印流
    private PrintWriter out = null;
    // tcp套接字监听
    private TcpSocketListener mTcpSocketListener;
    // 内容
    private String content = "";


    public TcpSocketClient(TcpSocketListener mTcpSocketListener) {
        this.mTcpSocketListener = mTcpSocketListener;
    }

    public interface TcpSocketListener{

        // 回调内容
        void callBackContent(String content);
        // 清除输入框内容
        void clearInputContent();
    }

    public TcpSocketClient(String serverIp, int serverPort , TcpSocketListener mTcpSocketListener){
        this.serverIp  = serverIp;
        this.serverPort = serverPort;
        this.mTcpSocketListener = mTcpSocketListener;
    }

    public void startTcpSocketConnect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(serverIp, serverPort);
                    in=new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())),true);

                    while (true){
                        if (mSocket.isConnected()){
                            if (mSocket.isInputShutdown()){
                                if ((content=in.readLine())!=null){
                                    content+="\n";
                                    if (mTcpSocketListener!=null){
                                        mTcpSocketListener.callBackContent(content);
                                    }

                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }




    public void sendMessageByTcpSocket(String msg){
        if (mSocket!=null && mSocket.isConnected()){
            if (!mSocket.isOutputShutdown()&& out!=null)
            {
                out.println(msg);
                if (mTcpSocketListener != null)
                    mTcpSocketListener.clearInputContent();
            }

        }
    }
}
