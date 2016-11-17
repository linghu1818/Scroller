package com.ipjmc.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 
 * @author linghu
 * 该方法可以实现任何ViewGroup的顶部和底部的滑动弹性回收效果，类似IOS
 *
 */
public class CustomView extends LinearLayout {
	private static final String TAG = "CustomView";

	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	private Interpolator interpolator;
	private int screenHeight;
	
	public CustomView(Context context) {
		this(context, null);
	}
	
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickable(true);
		setLongClickable(true);
		interpolator = new AccelerateDecelerateInterpolator();
		mScroller	 = new Scroller(context, interpolator);
		mGestureDetector = new GestureDetector(context, new CustomGestureListener());
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		screenHeight = height;
	}

	//调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	//调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {
		//设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, 250);
		invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}
	
	//调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy, int during) {
		//设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, during);
		invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}
	
	@Override
	public void computeScroll() {
		//先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
			//这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			//必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP :
			Log.i(TAG, "get Sy" + getScrollY());
			smoothScrollTo(0, 0);
			break;
		default:
			return mGestureDetector.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}
	
	class CustomGestureListener implements GestureDetector.OnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			int dis = (int)distanceY/2;
			if(dis <= screenHeight/5){
				smoothScrollBy(0, dis, 100);
			}else if(dis <= screenHeight/3){
				smoothScrollBy(0, dis, 250);
			}else if(dis <= screenHeight /2){
				smoothScrollBy(0, dis, 400);
			}else{
				smoothScrollBy(0, dis, 500);
			}
			Log.i(TAG, dis + ".");
			
			return false;
		}
		

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return false;
		}
		
	}
	
	
}