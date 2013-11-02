package com.adbionic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.picasso.R;

public class Create extends PicassoActivity {

	ImageView profilePicture;
	EditText etName, etCaption, etDescription, etLink, etClicks;
	Button bPayment;
	String name, caption, description, link, clicks;
	Uri selectedImageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_post);
		setUp();
	}

	private void setUp() {
		// TODO Auto-generated method stub
		profilePicture = (ImageView) findViewById(R.id.iv_profile_picture);
		etName = (EditText) findViewById(R.id.et_post_name);
		etCaption = (EditText) findViewById(R.id.et_post_caption);
		etDescription = (EditText) findViewById(R.id.et_post_description);
		etLink = (EditText) findViewById(R.id.et_post_link);
		etClicks = (EditText) findViewById(R.id.et_post_clicks);
		bPayment = (Button) findViewById(R.id.b_proceed_to_payments);
		bPayment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getData();
				bundleData();
			}

		});
		profilePicture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK);
				i.setType("image/*");
				startActivityForResult(i, 10);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 10:
				// Set image on ImageView and also upload the image to server
				selectedImageUri = data.getData();
				profilePicture.setImageURI(selectedImageUri);
				break;
			default:
				break;
			}
		}
	}

	private void getData() {
		// TODO Auto-generated method stub
		name = etName.getText().toString();
		caption = etCaption.getText().toString();
		description = etDescription.getText().toString();
		link = etLink.getText().toString();
		clicks = etClicks.getText().toString();
	}

	private void bundleData() {
		// TODO Auto-generated method stub
		try {
			Intent i = new Intent(getApplicationContext(), Payment.class);
			Bundle b = new Bundle();
			b.putString("name", name);
			b.putString("caption", caption);
			b.putString("description", description);
			b.putString("link", link);
			b.putString("clicks", clicks);
			b.putString("image", selectedImageUri.toString());
			i.putExtras(b);
			startActivity(i);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(
					getApplicationContext(),
					"Please make sure you have entered all the details correctly, and that you have selected an image for your post",
					Toast.LENGTH_LONG).show();
		}

	}

}
