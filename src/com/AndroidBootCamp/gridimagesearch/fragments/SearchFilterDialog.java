package com.AndroidBootCamp.gridimagesearch.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.AndroidBootCamp.gridimagesearch.R;
import com.AndroidBootCamp.gridimagesearch.models.ImageSearchFilter;

public class SearchFilterDialog extends DialogFragment {	
	//private List<String> imageSizes, colorFilters, imageTypes;
	//ArrayAdapter<String> aImageSizes, aColorFilters, aImageTypes;
	private Spinner spImageSize, spImageColor, spImageType;
	private Button btSave, btCancel;
	private EditText etSite;
	private ArrayAdapter<CharSequence> aImageSize, aImageColor, aImageType;
	private ImageSearchFilter searchFilter = null;
 	
//	User can configure advanced search filters such as:
//		Size (small, medium, large, extra-large)
//		Color filter (black, blue, brown, gray, green, etc...)
//		Type (faces, photo, clip art, line art)
//		Site (espn.com)

	public SearchFilterDialog() {
		// Empty constructor required for DialogFragment
		
	}
	
	public static SearchFilterDialog newInstance(String title){//, ImageSearchFilter filter ) {
		SearchFilterDialog frag = new SearchFilterDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		//args.putParcelable("Filter", filter);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle("Advanced Filter");
		View view = inflater.inflate(R.layout.fragment_search_filter, container);
		//ImageSearchFilter filter = getArguments().getParcelable("Filter");
		
		spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spImageColor = (Spinner) view.findViewById(R.id.spImageColor);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        
        etSite = (EditText) view.findViewById(R.id.etSite);
        btSave = (Button) view.findViewById(R.id.btSave);
        btCancel = (Button) view.findViewById(R.id.btCancel);
        
        btSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (searchFilter == null){
					searchFilter = new ImageSearchFilter();
				}
				searchFilter._imageSize = spImageSize.getSelectedItem().toString();
				searchFilter._imageColor = spImageColor.getSelectedItem().toString();
				searchFilter._imageType = spImageType.getSelectedItem().toString();
				searchFilter._siteUrl = etSite.getText().toString();
				 Intent intent = new Intent();
				 intent.putExtra("SearchFilter", searchFilter);
				 Activity activity = getActivity();
				 activity.setResult(activity.RESULT_OK, intent);
				 activity.finish();
			}
		});
        
        btCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Activity activity = getActivity();
				 activity.setResult(activity.RESULT_CANCELED);
				 activity.finish();
				
			}
		});
        
        setupSpinners();
		//etSite.requestFocus();
		getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x8028B7C9));
		return view;
	}
	
	private void setupSpinners(){
	    aImageSize = ArrayAdapter.createFromResource(getActivity(), R.array.imageSizeValue, android.R.layout.simple_spinner_item);
        int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
        aImageSize.setDropDownViewResource(spinner_dd_item);
        spImageSize.setAdapter(aImageSize);

        aImageColor = ArrayAdapter.createFromResource(getActivity(), R.array.imageColorValue, android.R.layout.simple_spinner_item);
        spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
        aImageColor.setDropDownViewResource(spinner_dd_item);
        spImageColor.setAdapter(aImageColor);

        aImageType = ArrayAdapter.createFromResource(getActivity(), R.array.imageTypeValue, android.R.layout.simple_spinner_item);
        spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
        aImageType.setDropDownViewResource(spinner_dd_item);
        spImageType.setAdapter(aImageType);

		
	}
	
//	public void initSpinner(ImageSearchFilter filter) {
//		if (filter == null)
//			return;
//		searchFilter = filter;
//		if (searchFilter._imageSize != null
//				&& !searchFilter._imageSize.isEmpty()) {
//			spImageSize.setSelection(aImageSize
//					.getPosition(searchFilter._imageSize));
//		}
//		if (searchFilter._imageColor != null
//				&& !searchFilter._imageColor.isEmpty()) {
//			spImageColor.setSelection(aImageColor
//					.getPosition(searchFilter._imageColor));
//		}
//		if (searchFilter._imageType != null
//				&& !searchFilter._imageType.isEmpty()) {
//			spImageType.setSelection(aImageType
//					.getPosition(searchFilter._imageType));
//		}
//		if (searchFilter._siteUrl != null) {
//			etSite.setText(searchFilter._siteUrl);
//		}
//
//	}
}
