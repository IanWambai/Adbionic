package com.adbionic.functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class PaymentService extends BroadcastReceiver {

	Intent o;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle bundle = intent.getExtras(); // ---get the SMS message passed
												// in---
			SmsMessage[] msgs = null;
			if (bundle != null) {
				// ---retrieve the SMS message received---
				try {
					Object[] pdus = (Object[]) bundle.get("pdus");
					msgs = new SmsMessage[pdus.length];
					for (int i = 0; i < msgs.length; i++) {
						String attachCode = "776f76wfuh";
						msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						String msgBody = msgs[i].getMessageBody();

						if (msgBody.startsWith(attachCode)) {
							this.abortBroadcast();
							Toast.makeText(context,
									"M-PESA text with Attach Code received",
									Toast.LENGTH_LONG).show();
							String transactionCode = msgBody
									.substring(attachCode.length()).trim()
									.toUpperCase();

							// Save it SharedPreferences
							SharedPreferences sp;
							SharedPreferences.Editor editor;
							sp = PreferenceManager
									.getDefaultSharedPreferences(context);
							editor = sp.edit();

							editor.putString("transaction_code",
									transactionCode).commit();
						}
					}
				} catch (Exception e) {
					Log.d("Exception caught", e.getMessage());
				}
			}
		}
	}
}
