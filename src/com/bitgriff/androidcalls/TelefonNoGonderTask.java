package com.bitgriff.androidcalls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;

class TelefonNoGonderTask extends AsyncTask<String, String, String> {

    

    protected String doInBackground(String... params) {
      
    	Socket socket = null;
		DataOutputStream dataOutputStream = null;
		DataInputStream dataInputStream = null;
		
		try {
			
			socket = new Socket(params[1], Integer.parseInt(params[2]));
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream.writeUTF(params[0]);
			//textIn.setText(dataInputStream.readUTF());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (dataOutputStream != null){
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (dataInputStream != null){
				try {
					dataInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	return null;
    }

    protected void onPostExecute(String str) {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }
}