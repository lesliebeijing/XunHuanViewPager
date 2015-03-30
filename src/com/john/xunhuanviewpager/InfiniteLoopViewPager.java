package com.john.xunhuanviewpager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InfiniteLoopViewPager extends ViewPager {
	private MainActivity act;
	private Handler handler;

	public InfiniteLoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InfiniteLoopViewPager(Context context) {
		super(context);
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		super.setAdapter(adapter);
		// 设置当前展示的位置
		setCurrentItem(0);
	}

	public void setInfinateAdapter(Activity act, Handler handler,
			PagerAdapter adapter) {
		this.act = (MainActivity) act;
		this.handler = handler;
		setAdapter(adapter);
	}

	@Override
	public void setCurrentItem(int item) {
		item = getOffsetAmount() + (item % getAdapter().getCount());
		super.setCurrentItem(item);
	}

	/**
	 * 从0开始向左可以滑动的次数
	 * 
	 * @return
	 */
	private int getOffsetAmount() {
		if (getAdapter() instanceof InfiniteLoopViewPagerAdapter) {
			InfiniteLoopViewPagerAdapter infiniteAdapter = (InfiniteLoopViewPagerAdapter) getAdapter();
			// 允许向前滚动100000次
			return infiniteAdapter.getRealCount() * 100000;
		} else {
			return 0;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// System.out.println("InfiniteLoopViewPager onInterceptTouchEvent =====>>>");
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			System.out
					.println("InfiniteLoopViewPager onInterceptTouchEvent =====>>> ACTION_DOWN");
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			// System.out.println("InfiniteLoopViewPager onInterceptTouchEvent =====>>> ACTION_MOVE");
		} else if (ev.getAction() == MotionEvent.ACTION_UP) {
			System.out
					.println("InfiniteLoopViewPager onInterceptTouchEvent =====>>> ACTION_UP");
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			act.isRun = false;
			act.isDown = true;
			handler.removeCallbacksAndMessages(null);
			System.out
					.println("InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_DOWN");
		} else if (action == MotionEvent.ACTION_MOVE) {
			act.isDown = true;
			act.isRun = false;
			handler.removeCallbacksAndMessages(null);
			// System.out.println("InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_MOVE");
		} else if (action == MotionEvent.ACTION_UP) {
			act.isRun = true;
			act.isDown = false;
			handler.removeCallbacksAndMessages(null);
			handler.sendEmptyMessageDelayed(1, 500);
			System.out
					.println("InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_UP");
		}
		return super.dispatchTouchEvent(ev);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			System.out
					.println("InfiniteLoopViewPager onTouchEvent =====>>> ACTION_DOWN");
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			// System.out.println("InfiniteLoopViewPager onTouchEvent =====>>> ACTION_MOVE");
		} else if (ev.getAction() == MotionEvent.ACTION_UP) {
			System.out
					.println("InfiniteLoopViewPager onTouchEvent =====>>> ACTION_UP");
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void setOffscreenPageLimit(int limit) {
		super.setOffscreenPageLimit(limit);
	}
}
