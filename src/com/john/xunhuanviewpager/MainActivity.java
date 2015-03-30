package com.john.xunhuanviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private InfiniteLoopViewPager viewPager;
	private int[] imageViewIds;
	private ImageView[] imageViews;
	private InfiniteLoopViewPagerAdapter pagerAdapter;
	private Handler mHandler;
	private int sleepTime = 3000;
	public boolean isRun = false;
	public boolean isDown = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,
							true);
					if (isRun && !isDown) {
						this.sendEmptyMessageDelayed(0, sleepTime);
					}
					break;

				case 1:
					if (isRun && !isDown) {
						this.sendEmptyMessageDelayed(0, sleepTime);
					}
					break;
				}

			}
		};
		initView();
	}

	private void initView() {
		imageViewIds = new int[] { R.drawable.i1, R.drawable.i2, R.drawable.i3,
				R.drawable.i4 };
		imageViews = new ImageView[imageViewIds.length];
		for (int i = 0; i < imageViewIds.length; i++) {
			imageViews[i] = new ImageView(this);
			imageViews[i].setImageResource(imageViewIds[i]);
		}
		viewPager = (InfiniteLoopViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new InfiniteLoopViewPagerAdapter(
				new MyLoopViewPagerAdatper());
		viewPager.setInfinateAdapter(this, mHandler, pagerAdapter);
	}

	private class MyLoopViewPagerAdatper extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (View) object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// return super.instantiateItem(container, position);
			container.addView(imageViews[position]);
			return imageViews[position];
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		isRun = false;
		mHandler.removeCallbacksAndMessages(null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		isRun = true;
		mHandler.sendEmptyMessageDelayed(0, sleepTime);
	}
}
