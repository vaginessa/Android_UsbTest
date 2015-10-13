package myusb;

public interface MyUsbManagerObserver {

	public void didConnectedUsbDevice();
	
	public void didDisconnectedUsbDevice();
	
	public void didConnectUsbDeviceFailure();
	
	public void didSendToUsbDevice(boolean isSuccess, String sendValue);
	
	public void didFetchFromUsbDevice(boolean isSuccess, String fetchValue);
	
	public void didStartedSyncAllData();
	
	public void didFailureSyncAllData();
	
	public void didFinishedSyncAllData();
}
