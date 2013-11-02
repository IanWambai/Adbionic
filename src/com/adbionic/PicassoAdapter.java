package com.adbionic;

import com.example.picasso.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

final class PicassoAdapter extends BaseAdapter {
	enum Sample {
		GRID_VIEW("Posts", PostActivity.class), USER_ACCOUNT("Account",
				Account.class), CREATE("Create", Create.class);
		// PREFERENCES("Preferences", Preferences.class);

		private final Class<?> activityClass;

		private final String name;

		Sample(String name, Class<?> activityClass) {
			this.activityClass = activityClass;
			this.name = name;
		}

		public void launch(Activity activity) {
			activity.startActivity(new Intent(activity, activityClass));
			activity.finish();
		}
	}

	private final LayoutInflater inflater;

	public PicassoAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return Sample.values().length;
	}

	@Override
	public Sample getItem(int position) {
		return Sample.values()[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) convertView;
		if (view == null) {
			view = (TextView) inflater.inflate(R.layout.picasso_activity_item,
					parent, false);
		}

		view.setText(getItem(position).name);

		return view;
	}
}
