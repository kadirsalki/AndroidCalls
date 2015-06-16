package com.bitgriff.androidcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class CallHelper {

	/**
	 * Listener to detect incoming calls. 
	 */
	private String serverIP="192.168.2.201";
	//private String serverIP="10.0.2.2";
	private String serverPort="8888";
	
	private class CallStateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				// called when someone is ringing to this phone
				String telefon = incomingNumber;
				
				if(telefon.length() == 13){
					new TelefonNoGonderTask().execute(telefon.substring(2).trim()+",1,5",serverIP,"8888");
				}
				else{
					new TelefonNoGonderTask().execute(telefon+",1,5",serverIP,"8888");
				}
				Toast.makeText(ctx, "Incoming: "+incomingNumber,Toast.LENGTH_LONG).show();
				
				break;
			}
		}
	}
	
	/**
	 * Broadcast receiver to detect the outgoing calls.
	 */
	public class OutgoingReceiver extends BroadcastReceiver {
	    public OutgoingReceiver() {
	    }

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
	        
	        Toast.makeText(ctx, 
	        		"Outgoing: "+number, 
	        		Toast.LENGTH_LONG).show();
	    }
  
	}

	private Context ctx;
	private TelephonyManager tm;
	private CallStateListener callStateListener;
	
	private OutgoingReceiver outgoingReceiver;

	public CallHelper(Context ctx) {
		this.ctx = ctx;
		
		callStateListener = new CallStateListener();
		outgoingReceiver = new OutgoingReceiver();
	}
	
	/**
	 * Start calls detection.
	 */
	public void start() {
		tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		ctx.registerReceiver(outgoingReceiver, intentFilter);
	}
	
	/**
	 * Stop calls detection.
	 */
	public void stop() {
		tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
		ctx.unregisterReceiver(outgoingReceiver);
	}

}
