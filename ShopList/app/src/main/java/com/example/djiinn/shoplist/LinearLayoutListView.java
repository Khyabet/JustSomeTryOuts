package com.example.djiinn.shoplist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.LinearLayout;

public class LinearLayoutListView extends LinearLayout {

	ListView listView;

	public LinearLayoutListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutListView(Context context, AttributeSet attrs,
								int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public void setListView(ListView lv){
		listView = lv;
	}

}
