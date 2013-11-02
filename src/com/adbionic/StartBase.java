package com.adbionic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.picasso.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class StartBase extends FragmentActivity {

	private static final int LOG = 0;
	private static final int SETTINGS = 1;
	private static final int FRAGMENT_COUNT = SETTINGS + 1;

	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	private boolean isResumed = false;
	private UiLifecycleHelper uiHelper;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	Intent i;
	ArrayList<String> al = new ArrayList<String>();
	String user_ID = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Parse.initialize(getApplicationContext(),
				"X2M16qGEHZIX3g7Skag0rJ2b5O6F6qXX2lfGVfsY",
				"vYlcI535Okq9rIP3Zl0VVYZGy4YBC78OdMDmUmQw");
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_base);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		FragmentManager fm = getSupportFragmentManager();
		fragments[LOG] = fm.findFragmentById(R.id.logFragment);
		fragments[SETTINGS] = fm.findFragmentById(R.id.userSettingsFragment);

		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			transaction.hide(fragments[i]);
		}
		transaction.commit();
	}

	private void showFragment(int fragmentIndex, boolean addToBackStack) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			if (i == fragmentIndex) {
				transaction.show(fragments[i]);
			} else {
				transaction.hide(fragments[i]);
			}
		}
		if (addToBackStack) {
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		isResumed = false;
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		// Only make changes if the activity is visible
		if (isResumed) {
			FragmentManager manager = getSupportFragmentManager();
			// Get the number of entries in the back stack
			int backStackSize = manager.getBackStackEntryCount();
			// Clear the back stack
			for (int i = 0; i < backStackSize; i++) {
				manager.popBackStack();
			}
			if (state.isOpened()) {
				// If the session state is open:
				// Show the authenticated fragment

				// Create user account or check existing account
				if (session != null && session.isOpened()) {
					// Get the user's data
					makeMeRequest(session);
				}
				Toast.makeText(getApplicationContext(), user_ID,
						Toast.LENGTH_LONG).show();

				try {
					ParseQuery<ParseObject> p = ParseQuery.getQuery("Users");
					p.whereEqualTo("FaceBookId", user_ID);
					p.findInBackground(new FindCallback<ParseObject>() {

						@Override
						public void done(List<ParseObject> users,
								ParseException arg1) {
							// TODO Auto-generated method stub

							for (ParseObject po : users) {
								String s = po.getString("FaceBookId");
								al.add(s);
							}
							if (al.isEmpty()) {

								Toast.makeText(getApplicationContext(),
										"no such user", Toast.LENGTH_LONG)
										.show();
								ParseObject po = new ParseObject("Users");
								po.put("Name", user_ID);
								po.put("Cash", 0);
								po.put("NumberOfPosts", 0);
								po.saveInBackground();

							} else {
								Toast.makeText(getApplicationContext(),
										"user exists", Toast.LENGTH_LONG)
										.show();
							}

						}
					});
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "You're offline",
							Toast.LENGTH_LONG).show();
				}

				i = new Intent(getApplicationContext(), PostActivity.class);
				startActivity(i);

			} else if (state.isClosed()) {
				// If the session state is closed:
				// Show the login fragment
				showFragment(LOG, false);
			}
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		final Session session = Session.getActiveSession();

		if (session != null && session.isOpened()) {
			// if the session is already open,
			// try to show the selection fragment

			// Create user account or check existing account
			if (session != null && session.isOpened()) {
				// Get the user's data
				makeMeRequest(session);
			}
			Toast.makeText(getApplicationContext(), user_ID, Toast.LENGTH_LONG)
					.show();

			try {
				ParseQuery<ParseObject> p = ParseQuery.getQuery("Users");
				p.whereEqualTo("FaceBookId", user_ID);
				p.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> users,
							ParseException arg1) {
						// TODO Auto-generated method stub

						for (ParseObject po : users) {
							String s = po.getString("FaceBookId");
							al.add(s);
						}
						if (al.isEmpty()) {

							Toast.makeText(getApplicationContext(),
									"no such user", Toast.LENGTH_LONG).show();
							ParseObject po = new ParseObject("Users");
							po.put("Name", user_ID);
							po.put("Cash", 0);
							po.put("NumberOfPosts", 00);
							po.saveInBackground();

						} else {
							Toast.makeText(getApplicationContext(),
									"user exists", Toast.LENGTH_LONG).show();
						}

					}
				});
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Error",
						Toast.LENGTH_LONG).show();
			}

			i = new Intent(getApplicationContext(), PostActivity.class);
			startActivity(i);
		} else {
			// otherwise present the splash screen
			// and ask the person to login.
			showFragment(LOG, false);
		}
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
								user_ID = user.getId();
							} else {
								Toast.makeText(getApplicationContext(),
										"User null", Toast.LENGTH_LONG)
										.show();
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync();
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		} else if (resultCode == Activity.RESULT_OK) {
			// Do nothing for now
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Leave?");
			alertDialog.setMessage("Are you sure you want to leave?");
			alertDialog.setIcon(android.R.drawable.ic_lock_power_off);
			alertDialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							android.os.Process.killProcess(android.os.Process
									.myPid());
						}
					});
			alertDialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

}
