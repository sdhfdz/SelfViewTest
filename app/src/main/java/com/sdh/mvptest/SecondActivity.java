package com.sdh.mvptest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdh.mvptest.bean.TcpSocketClient;

public class SecondActivity extends AppCompatActivity {

    // 服务端地址
    private final static String serverIp = "172.27.35.11";
    // 服务端口号
    private final static int serverPort = 9999;
    // 控件
    private TextView showTv;
    private EditText contentEdt;
    private Button sendBtn;
    // tcp套接字客户端
    private TcpSocketClient mTcpSocketClient;
    // 自定义Handler,用于更新Ui
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        showTv = (TextView) findViewById(R.id.tv_show);
        contentEdt = (EditText) findViewById(R.id.edt_content);
        mTcpSocketClient = new TcpSocketClient(serverIp, serverPort, new TcpSocketClient.TcpSocketListener() {
            @Override
            public void callBackContent(final String content) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (showTv != null)
                            showTv.setText(showTv.getText().toString() + content);
                    }
                });
            }

            @Override
            public void clearInputContent() {

                if (contentEdt != null)
                    contentEdt.setText("");
            }
        });
        mTcpSocketClient.startTcpSocketConnect();
    }

    public void send(View view){

        System.out.println("hahaha");
        String msg = contentEdt.getText().toString().trim();
        mTcpSocketClient.sendMessageByTcpSocket(msg);
    }

    @Override
    protected void onDestroy() {
        if (mTcpSocketClient != null)
            mTcpSocketClient.sendMessageByTcpSocket("exit");

        super.onDestroy();
    }
}
