/**
 *@filename NoScrollListView.java
 *@Email tengzhenjiu@qq.com
 *
 */
package com.example.cxjminfodemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Ŀ����Ϊ����ʾlistview���Ҳ�Ļ�����
 * 
 * @Title NoScrollListView
 * @author tengzj
 * @data 2016��8��23�� ����2:24:09
 */
public class NoScrollListView extends ListView {

	public NoScrollListView(Context context) {
		super(context);
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}