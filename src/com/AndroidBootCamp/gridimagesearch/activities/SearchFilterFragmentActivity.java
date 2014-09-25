//package com.AndroidBootCamp.gridimagesearch.activities;
//
//import com.AndroidBootCamp.gridimagesearch.R;
//import com.AndroidBootCamp.gridimagesearch.R.id;
//import com.AndroidBootCamp.gridimagesearch.R.layout;
//import com.AndroidBootCamp.gridimagesearch.R.menu;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//public class SearchFilterFragmentActivity extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_search_filter_fragment);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.search_filter, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}

package com.AndroidBootCamp.gridimagesearch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.AndroidBootCamp.gridimagesearch.R;
import com.AndroidBootCamp.gridimagesearch.fragments.SearchFilterDialog;
import com.AndroidBootCamp.gridimagesearch.models.ImageSearchFilter;

public class SearchFilterFragmentActivity extends FragmentActivity {
	private ImageSearchFilter searchFilter; 

	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	  	super.onCreate(savedInstanceState);
	  	setContentView(R.layout.activity_search_filter_fragment);
	  //get data passed in from Intent bundle
	    searchFilter = getIntent().getParcelableExtra("Filter"); 
	  	showFilterDialog(searchFilter);
	  }

	  private void showFilterDialog(ImageSearchFilter filter) {
		FragmentManager fm = getSupportFragmentManager();
		//FragmentTransaction ft = fm.beginTransaction();
		SearchFilterDialog filterDialog = 
				SearchFilterDialog.newInstance("Image Search Filter");//, filter);
//		ft.replace(R.id.fragContainer, filterDialog);
//		ft.commit();
		filterDialog.show(fm, "fragment_filter");
	  }
	}
