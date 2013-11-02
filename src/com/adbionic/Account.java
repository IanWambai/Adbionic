package com.adbionic;

import com.example.picasso.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Account extends PicassoActivity {

	private ProfilePictureView profilePictureView;
	private UiLifecycleHelper uiHelper;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	Button bgetAirtime;
	ImageView imageView;
	String amount, times_shared;
	TextView urlView, userNameView, amountOfCash, timesShared;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_account);
		uiHelper = new UiLifecycleHelper(Account.this, callback);
		uiHelper.onCreate(savedInstanceState);

		setUp();
		getParseData();

		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}

		setData();
		bgetAirtime = (Button) findViewById(R.id.b_get_airtime);
		bgetAirtime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Withdraw.class);
				Bundle b=new Bundle();
				b.putString("amount", amount);
				i.putExtras(b);
				startActivity(i);
			}
		});
	}

	private void setData() {
		// TODO Auto-generated method stub
		urlView.setText("Your Account");
		amountOfCash.setText("Cash in account: Ksh." + amount);
		timesShared.setText("Number of posts made: " + times_shared);
	}

	private void getParseData() {
		// TODO Auto-generated method stub
		// Get user details from Parse here
		amount = "1200";
		times_shared = "30";
	}

	private void setUp() {
		// TODO Auto-generated method stub
		urlView = (TextView) findViewById(R.id.url);
		imageView = (ImageView) findViewById(R.id.photo);
		userNameView = (TextView) findViewById(R.id.user_name);
		profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
		amountOfCash = (TextView) findViewById(R.id.cash_in_account);
		timesShared = (TextView) findViewById(R.id.number_of_posts_made);
	}

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (user != null) {
								// Set the id for the ProfilePictureView
								// view that in turn displays the profile
								// picture.
								profilePictureView.setProfileId(user.getId());
								// Set the Textview's text to the user's name.
								userNameView.setText(user.getName());
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync();
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		} else if (resultCode == Activity.RESULT_OK) {
			// Do nothing for now
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

}
