package com.AndroidBootCamp.gridimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.AndroidBootCamp.gridimagesearch.R;
import com.AndroidBootCamp.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, android.R.layout.simple_list_item_1, images);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 // Get the data item for this position
	       ImageResult imageInfo = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).
	        		  inflate(R.layout.item_image_result, parent, false);
	       }
	       // Lookup view for data population
	       ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
	      // TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
	       //cleanup the last image
	       ivImage.setImageResource(0);
	       // Populate the data into the template view using the data object
	       //tvTitle.setText(Html.fromHtml(imageInfo._title));
	       //Remotely download the image data in the background with Picasso
	       Picasso.with(this.getContext()).load(imageInfo._thumbUrl).resize(300, 300).into(ivImage);
	       // Return the completed view to render on screen
	       return convertView;
	}

}
