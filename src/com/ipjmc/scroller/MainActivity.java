package com.ipjmc.scroller;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * @author linghu
 * Scroller的使用的demo，CustomView可以实现任何ViewGroup的顶部和底部的滑动弹性回收效果，类似IOS
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}