package com.AndroidBootCamp.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.AndroidBootCamp.gridimagesearch.R;
import com.AndroidBootCamp.gridimagesearch.adapters.EndlessScrollListener;
import com.AndroidBootCamp.gridimagesearch.adapters.ImageResultsAdapter;
import com.AndroidBootCamp.gridimagesearch.models.ImageResult;
import com.AndroidBootCamp.gridimagesearch.models.ImageSearchFilter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class SearchActivity extends Activity {
	private GridView gdResult;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private MenuItem searchItem; 
	private SearchView searchView;
	private ImageSearchFilter searchFilter;
	private final int FILTER_REQUEST = 100;
	private final String searchUrlBase = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupGridView();
		searchFilter = new ImageSearchFilter();
	}

	// set up grid view with data source, adapter and listeners
	private void setupGridView() {
		// create the data source
		imageResults = new ArrayList<ImageResult>();
		// create the corresponding adapter and attaches the data source to it
		aImageResults = new ImageResultsAdapter(this, imageResults);
		// find the grid view
		gdResult = (GridView) findViewById(R.id.gdResult);
		// link the adapter to the view
		gdResult.setAdapter(aImageResults);
		// set listeners
		gdResult.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				customLoadMoreDataFromApi(page);
				// or customLoadMoreDataFromApi(totalItemsCount);
			}
		});
		gdResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// launch the image display activity

				// create the intent
				Intent i = new Intent(SearchActivity.this,
						ImageDisplayActivity.class);
				// get image result/full image to display
				ImageResult result = imageResults.get(position);
				// pass image result to display
				i.putExtra("ImageResult", result);
				// lauch the new activity
				startActivity(i);

			}
		});
	}
	
	// Append more data into the adapter
	public void customLoadMoreDataFromApi(int offset) {
		// This method probably sends out a network request and appends new data items to your adapter.
		// Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		// Deserialize API response and then construct new objects to append to the adapter
		String query = searchView.getQuery().toString();
		AsyncHttpClient client = new AsyncHttpClient();
		//String searchUrl = searchUrlBase + query + "&rsz=8" +"&start=" + offset*8;
		String searchUrl = buildSearchUrl(query, offset, false);
		client.get(searchUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// Log.d("DEBUG", response.toString());
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData")
							.getJSONArray("results");
					aImageResults.addAll(ImageResult
							.fromJSONArray(imageResultsJson));

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
	}

	//get image data
	private void queryImagesFirstBatch(String query) {
		aImageResults.clear();
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
		//String searchUrl = searchUrlBase + query + "&rsz=8";
		String searchUrl = buildSearchUrl(query, 0, true);
		client.get(searchUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// Log.d("DEBUG", response.toString());
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData")
							.getJSONArray("results");
					// clear results, avoid it when paginating, only the first
					// time)
					imageResults.clear();
					// when make changes to the adapter it modifies the
					// underlying data automatically
					aImageResults.addAll(ImageResult
							.fromJSONArray(imageResultsJson));
					// Log.d("DEBUG", "imageResults size =" +
					// imageResults.size());

				} catch (JSONException e) {
					e.printStackTrace();
				}
				// Log.i("INFO", imageResults.get(7).toString());
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Log.d("DEBUG", "Error response" + responseString);
			}
		});
	}

	private String buildSearchUrl(String queryStr, int offset,
			boolean firstBatch) {
		String query = searchUrlBase + queryStr + "&rsz=8";
		String imageFilter = "";

		if (searchFilter != null) {
			if (searchFilter._imageSize != null
					&& !searchFilter._imageSize.isEmpty()
					&& searchFilter._imageSize != "any") {
				imageFilter += "&imgsz=" + searchFilter._imageSize;
			}
			if (searchFilter._imageColor != null
					&& !searchFilter._imageColor.isEmpty()
					&& searchFilter._imageColor != "any") {
				imageFilter += "&imgcolor=" + searchFilter._imageColor;
			}
			if (searchFilter._imageType != null
					&& !searchFilter._imageType.isEmpty()
					&& searchFilter._imageType != "any") {
				imageFilter += "&imgtype=" + searchFilter._imageType;
			}
			if (searchFilter._siteUrl != null
					&& !searchFilter._siteUrl.isEmpty()) {
				imageFilter += "&as_sitesearch=" + searchFilter._siteUrl;
			}
		}

		if (!imageFilter.isEmpty()){
			query+=imageFilter;
		}
		else if (!firstBatch){ //for endless scrolling
			query+="&start=" + (offset-1)*8;
		}
		
		Log.d("DEBUG", offset+ query);
		return query;

	}
	
	// check Internet connectivity
	public Boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = 
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		boolean isWiFi = activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
		Log.i("Info", (isWiFi ? "On a WiFi network." : "Not on a WiFi network"));
		return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

	public Boolean isOnline() {
		try {
			Process p1 = java.lang.Runtime.getRuntime().exec(
					"ping -c 1 www.google.com");
			int returnVal = p1.waitFor();
			boolean reachable = (returnVal == 0);
			return reachable;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();//getSupportMenuInflater();
	    inflater.inflate(R.menu.menu_search_view, menu);
	    searchItem = menu.findItem(R.id.action_search);
	    searchView = (SearchView) searchItem.getActionView();
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
	       @Override
	       public boolean onQueryTextSubmit(String query) {
	    	   //Log.d("DEBUG", "query=" + searchView.getQuery().toString());
	    	   queryImagesFirstBatch(query);
	           return true;
	       }

	       @Override
	       public boolean onQueryTextChange(String newText) {
	           return false;
	       }
	   });
	   return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// first parameter is the context, second is the class of the activity to launch
			  Intent it = new Intent(this, SearchFilterFragmentActivity.class);
			  it.putExtra("Filter", searchFilter);
			  // brings up the fragment activity
			  startActivityForResult(it, FILTER_REQUEST);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 protected void onActivityResult(int requestCode, int resultCode,
             Intent data) {
         if (requestCode == FILTER_REQUEST) {
             if (resultCode == RESULT_OK) {
            	ImageSearchFilter t_filter = data.getParcelableExtra("SearchFilter");
         		searchFilter._imageSize = t_filter._imageSize;
         		searchFilter._imageColor = t_filter._imageColor;
         		searchFilter._imageType = t_filter._imageType;
         		searchFilter._siteUrl = t_filter._siteUrl;
             }
             //else RESULT_CANCELED, nothing changed
         }
     }
}
