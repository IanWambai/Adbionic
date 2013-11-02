package com.adbionic;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.picasso.R;
import com.squareup.picasso.Picasso;

final class PostAdapter extends BaseAdapter {
	private final Context context;
	private final List<String> urls = new ArrayList<String>();
	private final List<String> names = new ArrayList<String>();
	private final List<String> captions = new ArrayList<String>();
	private final List<String> descriptions = new ArrayList<String>();
	private final List<String> links = new ArrayList<String>();
	static String[] URLS = null;
	static String[] NAMES = null;
	static String[] CAPTIONS = null;
	static String[] DESCRIPTIONS = null;
	static String[] LINKS = null;
	Collections cUrls, cNames, cCaptions, cDescriptions, cLinks;

	@SuppressWarnings("static-access")
	public PostAdapter(Context context) {
		this.context = context;

		// Ensure we get a different ordering of images on each run.
		cUrls.addAll(urls, Data.URLS);
		cNames.addAll(names, Data.NAME);
		cCaptions.addAll(captions, Data.CAPTION);
		cDescriptions.addAll(descriptions, Data.DESCRIPTION);
		cLinks.addAll(links, Data.LINK);

		URLS = urls.toArray(new String[urls.size()]);
		NAMES = names.toArray(new String[names.size()]);
		CAPTIONS = captions.toArray(new String[descriptions.size()]);
		DESCRIPTIONS = descriptions.toArray(new String[captions.size()]);
		LINKS = links.toArray(new String[links.size()]);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SquaredImageView view = (SquaredImageView) convertView;
		if (view == null) {
			view = new SquaredImageView(context);
			view.setScaleType(CENTER_CROP);
		}

		// Get the image URL for the current position.
		String url = getItem(position);

		// Trigger the download of the URL asynchronously into the image view.
		Picasso.with(context) //
				.load(url) //
				.placeholder(R.drawable.placeholder) //
				.error(R.drawable.no_internet) //
				.fit() //
				.into(view);

		return view;
	}

	@Override
	public int getCount() {
		return urls.size();
	}

	@Override
	public String getItem(int position) {
		return urls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
