package com.adbionic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adbionic.functions.FacebookRef;
import com.example.picasso.R;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.squareup.picasso.Picasso;

@SuppressWarnings("deprecation")
public class Details extends PicassoActivity {

	Button share;
	private final List<String> urls = new ArrayList<String>();
	private final List<String> names = new ArrayList<String>();
	private final List<String> captions = new ArrayList<String>();
	private final List<String> descriptions = new ArrayList<String>();
	private final List<String> links = new ArrayList<String>();
	String url, name, caption, description, link;
	ImageView imageView;
	int position;
	Collections cUrls, cNames, cCaptions, cDescriptions, cLinks;
	SharedPreferences sp;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_post);

		Bundle b = getIntent().getExtras();
		position = b.getInt("position");

		cUrls.addAll(urls, PostAdapter.URLS);
		cNames.addAll(names, PostAdapter.NAMES);
		cCaptions.addAll(captions, PostAdapter.CAPTIONS);
		cDescriptions.addAll(descriptions, PostAdapter.DESCRIPTIONS);
		cLinks.addAll(links, PostAdapter.LINKS);

		url = urls.get(position);
		name = names.get(position);
		caption = captions.get(position);
		description = descriptions.get(position);
		link = links.get(position);
		
		setUpFacebook();

		TextView nameView = (TextView) findViewById(R.id.name);
		TextView captionView = (TextView) findViewById(R.id.caption);
		TextView descriptionView = (TextView) findViewById(R.id.description);
		TextView linkView = (TextView) findViewById(R.id.link);
		imageView = (ImageView) findViewById(R.id.photo);

		nameView.setText(name);
		captionView.setText(caption);
		descriptionView.setText(description);
		linkView.setText(link);

		Picasso.with(Details.this) //
				.load(url) //
				.placeholder(R.drawable.placeholder) //
				.error(R.drawable.no_internet).into(imageView); //

		share = (Button) findViewById(R.id.b_share);
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle params = new Bundle();
				params.putString("name", name);
				params.putString("caption", caption);
				params.putString("description", description);
				params.putString("link", link);
				params.putString("picture", url);

				FacebookRef.fb.dialog(Details.this, "feed", params,
						new DialogListener() {

							@Override
							public void onFacebookError(FacebookError e) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										"Please log in again",
										Toast.LENGTH_LONG).show();
							}

							@Override
							public void onError(DialogError e) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										"Please check your internet connection",
										Toast.LENGTH_LONG).show();
							}

							@Override
							public void onComplete(Bundle values) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										"Done",
										Toast.LENGTH_LONG).show();
								//Give the user cash
							}

							@Override
							public void onCancel() {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										"Caneclled",
										Toast.LENGTH_LONG).show();
							}
						});
			}
		});

	}
	
	private void setUpFacebook() {
		// TODO Auto-generated method stub
		String APP_ID = getString(R.string.app_id);
		FacebookRef.fb = new Facebook(APP_ID);

		FacebookRef.asyncrunner = new AsyncFacebookRunner(FacebookRef.fb);

		sp = getPreferences(MODE_PRIVATE);
		String access_token = sp.getString("access_token", null);
		long expires = sp.getLong("access_expires", 0);

		if (access_token != null) {
			FacebookRef.fb.setAccessToken(access_token);
		}
		if (expires != 0) {
			FacebookRef.fb.setAccessExpires(expires);
		}

	}

}
