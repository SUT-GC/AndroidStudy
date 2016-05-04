package igouc.com.webtest.webUtil;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Created by gouchao on 16-5-3.
 */
public class ClientThread extends Thread {
	private Socket socket;
	private Handler handler;
	public Handler vHandler;
	private BufferedReader bufferedReader = null;
	private OutputStream outputStream = null;
	public ClientThread(Handler handler){
		this.handler = handler;
	}

	@Override
	public void run() {
		Log.i("aclientThread","启动了ClientThread的线程");
		try {
			socket = new Socket("163.44.165.23",20000);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = socket.getOutputStream();
			Log.i("aclientThread","获取了Socket的输出流"+socket);
			new Thread(){
				@Override
				public void run() {
					Log.i("aclientThread","启动了ClientThread的线程中的线程.....");
					String count = null;
					try {
						while((count = bufferedReader.readLine()) != null){
							Message message = new Message();
							message.what = 123;
							message.obj = count;
							Log.i("aclientThread","收到的将要显示的消息是"+message.obj);
							handler.sendMessage(message);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();

			Looper.prepare();
			vHandler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 456){
						PrintStream ps = new PrintStream(outputStream);
						ps.println(msg.obj);
					}
				}
			};
			Looper.loop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
