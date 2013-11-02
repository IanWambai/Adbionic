package com.adbionic;

import com.example.picasso.R;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Withdraw extends PicassoActivity {

	Button done;
	String url;
	ImageView imageView;
	EditText et_amount;
	TextView urlView;
	String amount;
	int position;
	ProgressDialog pd;
	String cash_in_account; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_withdraw);

		setUp();
		getData();

		urlView.setText("Your Account");
		url = "http://i.imgur.com/u6JF6JZ.jpg";
		Picasso.with(Withdraw.this) //
				.load(url) //
				.placeholder(R.drawable.placeholder) //
				.error(R.drawable.no_internet).into(imageView); //

		done = (Button) findViewById(R.id.b_withdraw_cash);
		done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				amount = et_amount.getText().toString();
				int cashInAccount = Integer.parseInt(cash_in_account);
				int amountToWithdraw = Integer.parseInt(amount);

				if (amountToWithdraw < (cashInAccount - 20)) {
					String verificationCode = "717345221";
					String number = "0700177140";
					String message = verificationCode+"*140*" + amount;
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(number, null, message, null, null);

					pd = ProgressDialog.show(Withdraw.this, "Please wait",
							"Processing payment...", true);

					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(500);
								Intent i = new Intent(Withdraw.this,
										Account.class);
								startActivity(i);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pd.dismiss();
						}
					}).start();

					Toast.makeText(
							getApplicationContext(),
							"You will recieve Ksh." + amount
									+ " within 10 minutes", Toast.LENGTH_LONG)
							.show();

					cashInAccount = cashInAccount - amountToWithdraw;
					// Then send this data to Parse and update the users account

				} else {
					Toast.makeText(
							getApplicationContext(),
							"You do not have sufficient funds to withdraw that much",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void getData() {
		// TODO Auto-generated method stub
		Bundle b = getIntent().getExtras();
		cash_in_account = b.getString("amount");

	}

	private void setUp() {
		// TODO Auto-generated method stub
		urlView = (TextView) findViewById(R.id.url);
		imageView = (ImageView) findViewById(R.id.photo);
		et_amount = (EditText) findViewById(R.id.et_amount_to_withdraw);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	

}
