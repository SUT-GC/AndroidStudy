package igouc.com.webtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

import igouc.com.webtest.webUtil.ClientThread;

public class MainActivity extends AppCompatActivity {

	private Handler handler;
	EditText input;
	TextView show;
	Button send;
	private ClientThread  clientThread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		show = (TextView) findViewById(R.id.showinfo);
		send = (Button) findViewById(R.id.send);

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 123){
					Log.i("main","接收到服务器发来的消息"+msg.obj);
					show.append(msg.obj+""+'\n');
				}
			}
		};
		clientThread = new ClientThread(handler);
		clientThread.start();
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 456;
				msg.obj = input.getText().toString();
				Log.i("main","发送出去的消息"+msg.obj);
				clientThread.vHandler.sendMessage(msg);
				input.setText("");
			}
		});
	}
}
