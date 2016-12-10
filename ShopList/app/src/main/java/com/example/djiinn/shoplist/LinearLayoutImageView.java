package com.example.djiinn.shoplist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LinearLayoutImageView extends LinearLayout {

	ImageView imageView;
	Context context;
	BadgeView badge;


	private Drawable vollKorbImage, leerKorbImage;

	public LinearLayoutImageView(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutImageView(Context context, AttributeSet attrs,
								 int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
		// TODO Auto-generated constructor stub
	}

	private void init(Context context)
	{
		this.context = context;
		this.vollKorbImage = ResourcesCompat.getDrawable(getResources(), R.drawable.transparent_vollkorb, null);
		this.leerKorbImage = ResourcesCompat.getDrawable(getResources(), R.drawable.transparent_leerkorb, null);
	}

	public void setImageView(ImageView iv){
		imageView = iv;
		badge = new BadgeView(this.context, imageView);
		imageView.setImageDrawable(this.leerKorbImage);
	}

	public void notifyImageView(int size)
	{
		if(imageView.getDrawable() != this.vollKorbImage)
		{
			imageView.setImageDrawable(this.vollKorbImage);
		}
		if(size != 0)
		{
			badge.setText(String.valueOf(size));
			badge.show();
		}
		else
		{
			imageView.setImageDrawable(this.leerKorbImage);
		}
	}

}
