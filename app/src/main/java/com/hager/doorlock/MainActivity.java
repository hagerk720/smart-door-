package com.hager.doorlock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
 ImageView iv ;
 TextView tv ;
 EditText et_ip;
 EditText et_port ;
 int count = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv_door);
        tv = findViewById(R.id.tv);
        et_ip = findViewById(R.id.et_ip);
        et_port = findViewById(R.id.et_port);

             iv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     count++;
                     if (count%2==0) {
                             sending("open") ;
                             iv.setImageResource(R.drawable.open);
                             tv.setText("Door has opened");
                     }
                     else{
                             sending("close");
                             iv.setImageResource(R.drawable.close);
                             tv.setText("Door is close");


                     }

                 }
             });



    }
    //------------------ sending data TCP\IP ----------------
    public  void sending(final String msg){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket( et_ip.getText().toString(),Integer.parseInt(et_port.getText().toString()));
                    DataOutputStream DOS = new DataOutputStream(s.getOutputStream());
                    DOS.writeBytes(msg);
                    DOS.flush();
                    DOS.close();
                    s.close();
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                } catch (UnknownHostException e){
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        t.start();
    }
}