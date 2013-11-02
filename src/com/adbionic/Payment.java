package com.adbionic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adbionic.functions.PaymentService;
import com.example.picasso.R;

public class Payment extends PicassoActivity {

	String name, caption, description, link, image, clicks, transactionCode,
			mpesaCode;
	TextView tvInstructions;
	Button bCreatePost;
	EditText etValidationCode;
	SharedPreferences sp;
	Intent o;
	int click;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_payment);
		startService();
		getData();
		setUp();

	}

	private void startService() {
		// TODO Auto-generated method stub
		Intent i = new Intent(getApplicationContext(), PaymentService.class);
		startService(i);
	}

	private void setUp() {
		// TODO Auto-generated method stub
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		etValidationCode = (EditText) findViewById(R.id.et_post_transaction_code);
		tvInstructions = (TextView) findViewById(R.id.tv_payment_instructions);
		tvInstructions
				.setText("Send an amount of Ksh."
						+ (click * 50)
						+ " to the number 0700177140 to create your post. You will recieve feedback immediately. Then enter the transaction code you recieve from M-PESA and tap Create Post. If validation is successful your post will be published.");
		bCreatePost = (Button) findViewById(R.id.b_create_post);
		bCreatePost.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Validate payment
				// Get the Transaction code the user has entered
				transactionCode = etValidationCode.getText().toString().trim()
						.toUpperCase();

				// Get the Transaction code the phone has received
				mpesaCode = sp.getString("transaction_code", null);

				// Compare the transaction codes
				if (mpesaCode.equalsIgnoreCase(transactionCode)) {
					o = new Intent(Payment.this, Create.class);
					PendingIntent contentIntent = PendingIntent.getActivity(
							Payment.this, 0, o, 0);
					NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
							Payment.this)
							.setSmallIcon(R.drawable.logo)
							.setContentTitle("Great!")
							.setContentText(
									"We have just confirmed your payment")
							.setTicker("We have just confirmed your payment");
					mBuilder.setContentIntent(contentIntent);
					mBuilder.setDefaults(Notification.DEFAULT_SOUND);
					mBuilder.setAutoCancel(true);
					NotificationManager mNotificationManager = (NotificationManager) Payment.this
							.getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(0, mBuilder.build());
					uploadData();
				} else {
					o = new Intent(Payment.this, Create.class);
					PendingIntent contentIntent = PendingIntent.getActivity(
							Payment.this, 0, o, 0);
					NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
							Payment.this)
							.setSmallIcon(R.drawable.logo)
							.setContentTitle("Woops!")
							.setContentText(
									"The transaction code you just entered isn't quite right")
							.setTicker(
									"The transaction code you just entered isn't quite right");
					mBuilder.setContentIntent(contentIntent);
					mBuilder.setDefaults(Notification.DEFAULT_SOUND);
					mBuilder.setAutoCancel(true);
					NotificationManager mNotificationManager = (NotificationManager) Payment.this
							.getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(0, mBuilder.build());
				}
				Toast.makeText(
						getApplicationContext(),
						"Your post may take several minutes to appear in the feed.",
						Toast.LENGTH_LONG).show();

			}
		});
	}

	private void uploadData() {
		// TODO Auto-generated method stub
		// This method will upload the Name, Caption, Description, Link and
		// Image to Parse
	}

	private void getData() {
		// TODO Auto-generated method stub
		Bundle b = getIntent().getExtras();
		name = b.getString("name");
		caption = b.getString("caption");
		description = b.getString("description");
		link = b.getString("link");
		image = b.getString("image");
		clicks = b.getString("clicks");
		click = Integer.parseInt(clicks);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
