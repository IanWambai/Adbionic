package com.adbionic;

import com.adbionic.functions.PaymentService;
import com.example.picasso.R;
import com.facebook.Session;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class PostActivity extends BaseActivity {

	AlertDialog.Builder alertDialog;
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_activity);
		
		Intent i = new Intent(getApplicationContext(), PaymentService.class);
		startService(i);

		GridView gv = (GridView) findViewById(R.id.grid_view);
		gv.setAdapter(new PostAdapter(this));
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"Clicked Item: " + (arg2 + 1), Toast.LENGTH_LONG)
						.show();
				Intent i = new Intent(getApplicationContext(), Details.class);
				Bundle b = new Bundle();
				b.putInt("position", arg2);
				i.putExtras(b);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.m_logout:
			alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Logout?");
			alertDialog.setMessage("Are you sure you want to logout?");
			alertDialog.setIcon(android.R.drawable.ic_lock_power_off);
			alertDialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getApplicationContext(), "Logout",
									Toast.LENGTH_LONG).show();
							Session.getActiveSession()
									.closeAndClearTokenInformation();
							i = new Intent(getApplicationContext(),
									StartBase.class);
							startActivity(i);
						}
					});
			alertDialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			break;
		case R.id.m_exit:
			alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Leave?");
			alertDialog.setMessage("Are you sure you want to leave?");
			alertDialog.setIcon(android.R.drawable.ic_lock_power_off);
			alertDialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent("kill");
							intent.setType("text/plain");
							sendBroadcast(intent);
						}
					});
			alertDialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
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
