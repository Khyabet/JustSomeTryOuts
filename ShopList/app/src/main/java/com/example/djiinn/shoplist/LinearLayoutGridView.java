package com.example.djiinn.shoplist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class LinearLayoutGridView extends LinearLayout {
	
	GridView gridView;

	public LinearLayoutGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutGridView(Context context, AttributeSet attrs,
								int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public void setGridView(GridView lv){
		gridView = lv;
	}

}
