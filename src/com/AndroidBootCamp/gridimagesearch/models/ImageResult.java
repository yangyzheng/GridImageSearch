package com.AndroidBootCamp.gridimagesearch.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ImageResult implements Parcelable{
	public String _fullUrl;
	public String _thumbUrl;	
	public String _title;
	
	//responseData->results[N]-> width/height/url/title/tburl
	public ImageResult(JSONObject json){
		try
		{
			this._fullUrl = json.getString("url");
			this._thumbUrl = json.getString("tbUrl");
			this._title= json.getString("title");
		}
		catch(JSONException e){
			e.getStackTrace();
		}
	}
	
	//take array of json images and return ArrayList of ImageResult
	public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		//Log.d("DEBUG", "Json array length = " + array.length());
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObj = array.getJSONObject(i);
				if (jsonObj != null) {
					results.add(new ImageResult(jsonObj));
				}
			} catch (JSONException e) {
				e.getStackTrace();
			}
		}

		return results;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	//The following are required for Parcelable
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(_fullUrl);
		out.writeString(_thumbUrl);
		out.writeString(_title);
	}
	
	public static final Parcelable.Creator<ImageResult> CREATOR = 
			new Parcelable.Creator<ImageResult>() {
	        public ImageResult createFromParcel(Parcel in) {
	            return new ImageResult(in);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };

    // takes a Parcel and returns an object populated with it's values
    private ImageResult(Parcel in) {
    	_fullUrl = in.readString();
    	_thumbUrl = in.readString();
    	_title = in.readString();
    }

}

