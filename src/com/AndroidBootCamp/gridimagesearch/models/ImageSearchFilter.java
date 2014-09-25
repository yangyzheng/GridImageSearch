package com.AndroidBootCamp.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageSearchFilter implements Parcelable {
	public String _query = "";
	public String _imageSize = "";
	public String _imageColor = "";
	public String _imageType = "";
	public String _siteUrl = "";

	
	public ImageSearchFilter() {
		_query = "";
    	_imageSize = "any";
		_imageColor = "any";
		_imageType = "any";
		_siteUrl = "";
	}

	public ImageSearchFilter(String query, String imageSize, String imageColor,
			String imageType, String siteUrl) {
		_query = query;
		_imageSize = imageSize;
		_imageColor = imageColor;
		_imageType = imageType;
		_siteUrl = siteUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(_query);
		out.writeString(_imageSize);
		out.writeString(_imageColor);
		out.writeString(_imageType);
		out.writeString(_siteUrl);
	}
	
	
	public static final Parcelable.Creator<ImageSearchFilter> CREATOR = 
			new Parcelable.Creator<ImageSearchFilter>() {
	        public ImageSearchFilter createFromParcel(Parcel in) {
	            return new ImageSearchFilter(in);
        }

        public ImageSearchFilter[] newArray(int size) {
            return new ImageSearchFilter[size];
        }
    };

    // takes a Parcel and returns an object populated with it's values
    private ImageSearchFilter(Parcel in) {
    	_query = in.readString();
    	_imageSize = in.readString();
		_imageColor = in.readString();
		_imageType = in.readString();
		_siteUrl = in.readString();
    }

}
