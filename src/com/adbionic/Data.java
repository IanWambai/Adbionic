package com.adbionic;

import java.util.List;

import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

//Get data from Parse from the relevant fields and put it in the String Arrays in their respective positions

final class Data {
	static final String BASE = "http://i.imgur.com/";
	static final String EXT = ".jpg";
	static final String[] URLS = { BASE + "CqmBjo5" + EXT,
			BASE + "zkaAooq" + EXT, BASE + "0gqnEaY" + EXT,
			BASE + "9gbQ7YR" + EXT, BASE + "aFhEEby" + EXT,
			BASE + "0E2tgV7" + EXT, BASE + "P5JLfjk" + EXT,
			BASE + "nz67a4F" + EXT, BASE + "dFH34N5" + EXT,
			BASE + "FI49ftb" + EXT, BASE + "DvpvklR" + EXT,
			BASE + "DNKnbG8" + EXT, BASE + "yAdbrLp" + EXT,
			BASE + "55w5Km7" + EXT, BASE + "NIwNTMR" + EXT,
			BASE + "DAl0KB8" + EXT, BASE + "xZLIYFV" + EXT,
			BASE + "HvTyeh3" + EXT, BASE + "Ig9oHCM" + EXT,
			BASE + "7GUv9qa" + EXT, BASE + "i5vXmXp" + EXT,
			BASE + "glyvuXg" + EXT, BASE + "u6JF6JZ" + EXT,
			BASE + "ExwR7ap" + EXT, BASE + "Q54zMKT" + EXT,
			BASE + "9t6hLbm" + EXT, BASE + "F8n3Ic6" + EXT,
			BASE + "P5ZRSvT" + EXT, BASE + "jbemFzr" + EXT,
			BASE + "8B7haIK" + EXT, BASE + "aSeTYQr" + EXT,
			BASE + "OKvWoTh" + EXT, BASE + "zD3gT4Z" + EXT,
			BASE + "z77CaIt" + EXT, };
	static final String[] NAME = { "Name 1", "Name 2", "Name 3", "Name 4",
			"Name 5", "Name 6", "Name 7", "Name 8", "Name 9", "Name 10",
			"Name 11", "Name 12", "Name 13", "Name 14", "Name 15", "Name 16",
			"Name 17", "Name 18", "Name 19", "Name 20", "Name 21", "Name 22",
			"Name 23", "Name 24", "Name 25", "Name 26", "Name 27", "Name 28",
			"Name 29", "Name 30", "Name 31", "Name 32", "Name 33", "Name 34" };
	static final String[] CAPTION = { "Caption 1", "Caption 2", "Caption 3",
			"Caption 4", "Caption 5", "Caption 6", "Caption 7", "Caption 8",
			"Caption 9", "Caption 10", "Caption 11", "Caption 12",
			"Caption 13", "Caption 14", "Caption 15", "Caption 16",
			"Caption 17", "Caption 18", "Caption 19", "Caption 20",
			"Caption 21", "Caption 22", "Caption 23", "Caption 24",
			"Caption 25", "Caption 26", "Caption 27", "Caption 28",
			"Caption 29", "Caption 30", "Caption 31", "Caption 32",
			"Caption 33", "Caption 34" };
	static final String[] DESCRIPTION = { "Description 1", "Description 2",
			"Description 3", "Description 4", "Description 5", "Description 6",
			"Description 7", "Description 8", "Description 9",
			"Description 10", "Description 11", "Description 12",
			"Description 13", "Description 14", "Description 15",
			"Description 16", "Description 17", "Description 18",
			"Description 19", "Description 20", "Description 21",
			"Description 22", "Description 23", "Description 24",
			"Description 25", "Description 26", "Description 27",
			"Description 28", "Description 29", "Description 30",
			"Description 31", "Description 32", "Description 33",
			"Description 34" };
	static final String[] LINK = { "http://www.twitter.com/1",
			"http://www.twitter.com/2", "http://www.twitter.com/3",
			"http://www.twitter.com/4", "http://www.twitter.com/5",
			"http://www.twitter.com/6", "http://www.twitter.com/7",
			"http://www.twitter.com/8", "http://www.twitter.com/9",
			"http://www.twitter.com/10", "http://www.twitter.com/11",
			"http://www.twitter.com/12", "http://www.twitter.com/13",
			"http://www.twitter.com/14", "http://www.twitter.com/15",
			"http://www.twitter.com/16", "http://www.twitter.com/17",
			"http://www.twitter.com/18", "http://www.twitter.com/19",
			"http://www.twitter.com/20", "http://www.twitter.com/21",
			"http://www.twitter.com/22", "http://www.twitter.com/23",
			"http://www.twitter.com/24", "http://www.twitter.com/25",
			"http://www.twitter.com/26", "http://www.twitter.com/27",
			"http://www.twitter.com/28", "http://www.twitter.com/29",
			"http://www.twitter.com/30", "http://www.twitter.com/31",
			"http://www.twitter.com/32", "http://www.twitter.com/33",
			"http://www.twitter.com/34" };

	private Data() {
		// No instances.
	}
	public void retrievePostData(){
		
		ParseQueryAdapter<ParseObject> pa= new ParseQueryAdapter<ParseObject>(null, "Posts"); 
		pa.setImageKey("advertImage");
		pa.setObjectsPerPage(10);
		pa.setTextKey("advert");
		
		
	}
	
}
