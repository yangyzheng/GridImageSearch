package com.AndroidBootCamp.gridimagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.AndroidBootCamp.gridimagesearch.R;
import com.AndroidBootCamp.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		//Remove the action bar on the imag display activity
		getActionBar().hide();
		//find the image view
		ImageView ivFullImage = (ImageView)findViewById(R.id.ivFullImage);
		ImageResult result = getIntent().getParcelableExtra("ImageResult");
		String fullUrl = result._fullUrl;
		//load image
		Picasso.with(this).load(fullUrl).into(ivFullImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
