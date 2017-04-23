package com.itheima.listview;

import android.os.Bundle;
import android.app.Activity;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new MyListAdapter());
		
		
	}
	private class MyListAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return 1000000000;
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv ;
			tv = new TextView(MainActivity.this);
			tv.setText("hhhh"+position);
			return tv;
		}
	}
}
