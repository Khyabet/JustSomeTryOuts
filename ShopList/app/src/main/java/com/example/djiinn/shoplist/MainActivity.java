package com.example.djiinn.shoplist;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	//items stored in ListView
	public class Item {
		Drawable ItemDrawable;
		String ItemString;
		Item(Drawable drawable, String t){
			ItemDrawable = drawable;
			ItemString = t;
		}
	}
	
	//objects passed in Drag and Drop operation
	class PassObject{
		View view;
		Item item;
		List<Item> srcList;
		
		PassObject(View v, Item i, List<Item> s){
			view = v;
			item = i;
			srcList = s;
		}
	}
	
	static class ViewHolder {
		ImageView icon;
		TextView text;	
	}

	public class ItemsListAdapter extends BaseAdapter {
		
		private Context context;
		private List<Item> list;

		ItemsListAdapter(Context c, List<Item> l){
			context = c;
			list = l;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			
		    // reuse views
		    if (rowView == null) {
		    	LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		    	rowView = inflater.inflate(R.layout.row, null);

		    	ViewHolder viewHolder = new ViewHolder();
		    	viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
		    	viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
		    	rowView.setTag(viewHolder);	
		    }

		    ViewHolder holder = (ViewHolder) rowView.getTag();
		    holder.icon.setImageDrawable(list.get(position).ItemDrawable);
		    holder.text.setText(list.get(position).ItemString);
		    
		    rowView.setOnDragListener(new ItemOnDragListener(list.get(position)));

		    return rowView;
		}
		
		public List<Item> getList(){
			return list;
		}
	}

	List<Item> items1, items2, items3;
	GridView gridView;
	ImageView imageView;
	ItemsListAdapter myItemsListAdapter1, myItemsListAdapter2, myItemsListAdapter3;
	LinearLayoutGridView area1;
	LinearLayoutImageView area3;
	TextView prompt;
	ArrayList<Item> shopList;
	
	//Used to resume original color in drop ended/exited
	int resumeColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gridView = (GridView) findViewById(R.id.gridview);
		imageView = (ImageView) findViewById(R.id.imageView);
		
		area1 = (LinearLayoutGridView)findViewById(R.id.pane1);
		area3 = (LinearLayoutImageView)findViewById(R.id.pane3);

		area3.setOnDragListener(myOnDragListener);
		area1.setGridView(gridView);
		area3.setImageView(imageView);
		
		initItems();
		myItemsListAdapter1 = new ItemsListAdapter(this, items1);
		myItemsListAdapter2 = new ItemsListAdapter(this, items2);
		myItemsListAdapter3 = new ItemsListAdapter(this, items3);
		gridView.setAdapter(myItemsListAdapter1);
		
		/*
		//Auto scroll to end of ListView
		listView1.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		listView2.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		listView3.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		*/
		
		gridView.setOnItemClickListener(listOnItemClickListener);
		
		//listView1.setOnItemLongClickListener(myOnItemLongClickListener);
		//listView3.setOnItemLongClickListener(myOnItemLongClickListener);

		gridView.setOnTouchListener(myOnTouchListener);
		
		prompt = (TextView) findViewById(R.id.prompt);
		// make TextView scrollable
		prompt.setMovementMethod(new ScrollingMovementMethod());
		//clear prompt area if LongClick
		prompt.setOnLongClickListener(new OnLongClickListener(){
			
			@Override
			public boolean onLongClick(View v) {
				prompt.setText("");
				return true;	
			}});
		
		resumeColor  = getResources().getColor(android.R.color.background_light);

	}
	
	OnItemLongClickListener myOnItemLongClickListener = new OnItemLongClickListener(){

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			Item selectedItem = (Item)(parent.getItemAtPosition(position));
			
			ItemsListAdapter associatedAdapter = (ItemsListAdapter)(parent.getAdapter());
		    List<Item> associatedList = associatedAdapter.getList();
			
			PassObject passObj = new PassObject(view, selectedItem, associatedList);

			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, passObj, 0);
			
			return true;
		}
		
	};

	View.OnTouchListener myOnTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
			{
				View listItemView = null;
				// Find the child view that was touched (perform a hit test)
				Rect rect = new Rect();
				int childCount = gridView.getChildCount();
				int[] listViewCoords = new int[2];
				gridView.getLocationOnScreen(listViewCoords);
				int x = (int) motionEvent.getRawX() - listViewCoords[0];
				int y = (int) motionEvent.getRawY() - listViewCoords[1];
				View child;
				for (int i = 0; i < childCount; i++) {
					child = gridView.getChildAt(i);
					child.getHitRect(rect);
					if (rect.contains(x, y)) {
						listItemView = child;
						break;
					}
				}

				if (listItemView != null)
				{
					int itemPosition = gridView.getPositionForView(listItemView);
					Item selectedItem = (Item)(gridView.getItemAtPosition(itemPosition));

					ItemsListAdapter associatedAdapter = (ItemsListAdapter)(gridView.getAdapter());
					List<Item> associatedList = associatedAdapter.getList();

					PassObject passObj = new PassObject(listItemView, selectedItem, associatedList);

					ClipData data = ClipData.newPlainText("", "");
					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(listItemView);
					view.startDrag(data, shadowBuilder, passObj, 0);
				}
				return true;
			}
			else
			{
				return false;
			}
		}
	};

	OnDragListener myOnDragListener = new OnDragListener() {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			String area;
			if(v == area1){
				area = "area1";	
			}else if(v == area3){
				area = "area3";	
			}else{
				area = "unknown";	
			}
			
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					prompt.append("ACTION_DRAG_STARTED: " + area  + "\n");
					break;	
				case DragEvent.ACTION_DRAG_ENTERED:
					prompt.append("ACTION_DRAG_ENTERED: " + area  + "\n");
					break;	
				case DragEvent.ACTION_DRAG_EXITED:
					prompt.append("ACTION_DRAG_EXITED: " + area  + "\n");
					break;	
				case DragEvent.ACTION_DROP:
					prompt.append("ACTION_DROP: " + area  + "\n");

					PassObject passObj = (PassObject)event.getLocalState();
					View view = passObj.view;
					Item passedItem = passObj.item;
					List<Item> srcList = passObj.srcList;
					GridView oldParent = (GridView)view.getParent();

					ItemsListAdapter srcAdapter = (ItemsListAdapter)(oldParent.getAdapter());

					LinearLayoutImageView newView = (LinearLayoutImageView) v;
					shopList.add(passedItem);

					prompt.append("ShopList Item Count: " + shopList.size()  + "\n");
					srcAdapter.notifyDataSetChanged();
					newView.notifyImageView(shopList.size());
					break;
			   case DragEvent.ACTION_DRAG_ENDED:
				   prompt.append("ACTION_DRAG_ENDED: " + area  + "\n");  
			   default:
				   break;	   
			}
			   
			return true;
		}
		
	};
	
	class ItemOnDragListener implements OnDragListener{
		
		Item  me;
		
		ItemOnDragListener(Item i){
			me = i;
		}

		@Override
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				prompt.append("Item ACTION_DRAG_STARTED: " + "\n");
				break;	
			case DragEvent.ACTION_DRAG_ENTERED:
				prompt.append("Item ACTION_DRAG_ENTERED: " + "\n");
				v.setBackgroundColor(0x30000000);
				break;	
			case DragEvent.ACTION_DRAG_EXITED:
				prompt.append("Item ACTION_DRAG_EXITED: " + "\n");
				v.setBackgroundColor(resumeColor);
				break;	
			case DragEvent.ACTION_DROP:
				prompt.append("Item ACTION_DROP: " + "\n");

				PassObject passObj = (PassObject)event.getLocalState();
				View view = passObj.view;
				Item passedItem = passObj.item;
				List<Item> srcList = passObj.srcList;
				ListView oldParent = (ListView)view.getParent();
				ItemsListAdapter srcAdapter = (ItemsListAdapter)(oldParent.getAdapter());
				
				ListView newParent = (ListView)v.getParent();
				ItemsListAdapter destAdapter = (ItemsListAdapter)(newParent.getAdapter());
				List<Item> destList = destAdapter.getList();
				
				int removeLocation = srcList.indexOf(passedItem);
				int insertLocation = destList.indexOf(me);
				/*
				 * If drag and drop on the same list, same position,
				 * ignore
				 */
				if(srcList != destList || removeLocation != insertLocation){
					if(removeItemToList(srcList, passedItem)){
						destList.add(insertLocation, passedItem);
					}
					
					srcAdapter.notifyDataSetChanged();
					destAdapter.notifyDataSetChanged();
				}

				v.setBackgroundColor(resumeColor);
				
				break;
		   case DragEvent.ACTION_DRAG_ENDED:
			   prompt.append("Item ACTION_DRAG_ENDED: "  + "\n");
			   v.setBackgroundColor(resumeColor);
		   default:
			   break;	   
		}
		   
		return true;
		}
		
	}
	
	OnItemClickListener listOnItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(MainActivity.this, 
					((Item)(parent.getItemAtPosition(position))).ItemString, 
					Toast.LENGTH_SHORT).show();
		}
		
	};

	private void initItems(){
		items1 = new ArrayList<Item>();
		items2 = new ArrayList<Item>();
		items3 = new ArrayList<Item>();
		shopList = new ArrayList<Item>();
		
		TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
		TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);
		
		for(int i=0; i<arrayDrawable.length(); i++){
			Drawable d = arrayDrawable.getDrawable(i);
			String s = arrayText.getString(i);
			Item item = new Item(d, s);
			items1.add(item);
		}
		
		arrayDrawable.recycle();
		arrayText.recycle();
	}
	
	private boolean removeItemToList(List<Item> l, Item it){
		boolean result = l.remove(it);
		return result;
	}
	
	private boolean addItemToList(List<Item> l, Item it){
		boolean result = l.add(it);
		return result;
	}

}
