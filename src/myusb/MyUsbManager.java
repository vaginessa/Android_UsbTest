package myusb;

import java.util.HashSet;

import android.content.Context;

public class MyUsbManager {
	private static final long MY_USB_MANAGER_SEND_DELAY = 2000L;
	private static final long MY_USB_MANAGER_FETCH_DELAY = 1000L;
	
	private static MyUsbManager instance = null;
	
	private Context _context;
	private HashSet<MyUsbManagerObserver> _observers;
	
	private MyUsbManager() {
		
	}
	
	// Public Method #############################################

	public static synchronized MyUsbManager setupInstance(Context context) {
		if (instance == null) {
			instance = new MyUsbManager();
			instance._context = context;
			instance.setupDidLoad();
		}
		
		return instance;
	}
	
	public static MyUsbManager getInstance() {
		return instance;
	}	
	
	public void sendToDevice(String value) {
		for (MyUsbManagerObserver observer : _observers) {
			observer.didSendToUsbDevice(true, value);
		}
		try {
			Thread.sleep(MY_USB_MANAGER_SEND_DELAY);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void fetchFromDevice(int length) {
		for (MyUsbManagerObserver observer : _observers) {
			observer.didFetchFromUsbDevice(true, String.valueOf(length));
		}
		try {
			Thread.sleep(MY_USB_MANAGER_FETCH_DELAY);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connectUsbDevice() {
		
	}
	
	// Observer Method
	
	public HashSet<MyUsbManagerObserver> getObservers() {
		return _observers;
	}
	
	// Private Method ##################################################
	
	private void setupDidLoad() {
		_observers = new HashSet<MyUsbManagerObserver>();
	}
}
