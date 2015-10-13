package com.example.usbtest;

import java.lang.ref.WeakReference;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;

import myusb.MyUsbManager;
import myusb.MyUsbManagerObserver;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private MainUsbManagerObserver _observer;

	private Button _sendButton;
	private Button _fetchButton;
	private EditText _messageText;

	private StringBuffer _sb;
	private ManiHandler _handler;
	
	private boolean _isRunning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_observer = new MainUsbManagerObserver();

		MyUsbManager.setupInstance(MainActivity.this);

		MyUsbManager.getInstance().getObservers().add(_observer);

		_sendButton = (Button) findViewById(R.id.button_send);
		_fetchButton = (Button) findViewById(R.id.button_fetch);
		_messageText = (EditText) findViewById(R.id.edit_text_message);

		_sb = new StringBuffer();
		_handler = new ManiHandler(this);
		_isRunning = false;

		_sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (_isRunning) {
					_sb.append("Syncing ...\n");
					_messageText.setText(_sb.toString());
					return;
				}
				
				Thread tt = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_isRunning = true;
						for (int i = 0; i < 10; i++) {
							MyUsbManager.getInstance().sendToDevice(
									String.valueOf(i));
						}
						_isRunning = false;
					}
				});
				tt.start();
			}
		});

		_fetchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10; i++) {
					System.out.println("test " + i);
				}
			}
		});
	}

	// Use Inner Class for observer

	class MainUsbManagerObserver implements MyUsbManagerObserver {

		@Override
		public void didConnectedUsbDevice() {
			// TODO Auto-generated method stub

		}

		@Override
		public void didDisconnectedUsbDevice() {
			// TODO Auto-generated method stub

		}

		@Override
		public void didConnectUsbDeviceFailure() {
			// TODO Auto-generated method stub

		}

		@Override
		public void didSendToUsbDevice(boolean isSuccess, String sendValue) {
			// TODO Auto-generated method stub
			System.out.println("main observer send " + sendValue);
			_sb.append("main observer send " + sendValue + "\n");
			_handler.sendEmptyMessage(0);
		}

		@Override
		public void didFetchFromUsbDevice(boolean isSuccess, String fetchValue) {
			// TODO Auto-generated method stub
			System.out.println("main observer fetch " + fetchValue);
		}

		@Override
		public void didStartedSyncAllData() {
			// TODO Auto-generated method stub

		}

		@Override
		public void didFailureSyncAllData() {
			// TODO Auto-generated method stub

		}

		@Override
		public void didFinishedSyncAllData() {
			// TODO Auto-generated method stub

		}
	}
	
	static class ManiHandler extends Handler {
		WeakReference<MainActivity> mActivity;
		
		ManiHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}
		
		public void handleMessage(Message msg) {
			MainActivity theActivity = mActivity.get();
			switch(msg.what) {
			case 0: {
				theActivity._messageText.setText(theActivity._sb.toString());
				break;
			}
			}
		}		
	}
}
