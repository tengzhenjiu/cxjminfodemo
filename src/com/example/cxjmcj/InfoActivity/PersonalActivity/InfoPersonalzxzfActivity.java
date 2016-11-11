/**
 *@filename InfoPersonalzxzfActivity.java
 *@Email tengzhenjiu@qq.com
 *
 */
package com.example.cxjmcj.InfoActivity.PersonalActivity;

import com.example.cxjmcj.R;
import com.example.cxjmcj.InfoActivity.InfoPersonalActivity;
import com.example.cxjmcj.dto.Personal;

import android.app.Activity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Title InfoPersonalzxzfActivity
 * @author tengzj
 * @data 2016��8��26�� ����4:56:49
 */
public class InfoPersonalzxzfActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_personal_zxzf);
		ButterKnife.bind(InfoPersonalzxzfActivity.this);
	}

	@OnClick(R.id.imageView)
	public void toInfoPersonalActivity() {
		finish();
	}
}