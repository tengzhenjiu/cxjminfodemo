package com.neuqsoft.cxjmcj;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.neuqsoft.cxjmcj.db.DBManager;
import com.neuqsoft.cxjmcj.dto.User;
import com.neuqsoft.cxjmcj.server.dto.CjUser;
import com.neuqsoft.cxjmcj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Title LoginActivity
 * @author tengzj
 * @data 2016年8月23日 下午4:52:12
 */
@SuppressWarnings({ "deprecation" })
public class LoginActivity extends Activity {

	private DBManager mgr;
	private EditText edit_user;
	private EditText edit_pw;
	private ArrayList<User> users;
	private String query_usern;
	private ImageView image_left;
	private TextView btn_login;
	private SharedPreferences sp;
	private Gson gson;
	private HttpUtils utils;
	private String userName;
	private String passWord;

	private SharedPreferences tokenSp;
	Context activity;

	/********** INITIALIZES *************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		activity = this;
		utils = new HttpUtils(1000);
		gson = new Gson();
		mgr = new DBManager(this);

		// mgr.addUser(users);
		initView();
		initData();

	}

	/*
	 * （1）输入用户名、密码 （2）无需注册功能 ，一个村对应一个用户名、密码 （3）登录安全性的考虑，密码用MD5加密传输
	 * （4）实现用户系统注销功能（首页注销功能） （5）登录后，自动将当前用户信息保存到sqlite中
	 */

	private void initView() {
		edit_user = (EditText) findViewById(R.id.edit_user);
		edit_pw = (EditText) findViewById(R.id.edit_pw);
		image_left = (ImageView) findViewById(R.id.image_left);
		btn_login = (TextView) findViewById(R.id.btn_login);

	}

	private void initData() {

		sp = getSharedPreferences("LoginFlag", MODE_PRIVATE);

		image_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		/** 登陆 */
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userName = edit_user.getText().toString().trim();
				passWord = edit_pw.getText().toString().trim();

				/** 网络登陆 */
				loginfromnet();

			}
		});

	}

	protected void loginfromnet() {
		// TODO Auto-generated method stub
		/**
		 * 用户登录POST请求服务器，验证用户名和密码是否正确 登陆成功返回token 时间：2016年10月20日09:45:42
		 */
		RequestParams params = new RequestParams();
		params.addHeader("Content-Type", "application/json");
		params.addHeader("Accept", "text/plain");
		params.addHeader("client_id", "1");
		CjUser userDTO = new CjUser();
		userDTO.setAccount(userName);
		userDTO.setName("");
		userDTO.setArea("");
		userDTO.setPwd(passWord);

		String jsonStr = gson.toJson(userDTO);

		params.setBodyEntity(new StringEntity(jsonStr, "utf-8"));

		utils.send(HttpMethod.POST, RcConstant.loginPath, params, new RequestCallBack<String>() {
			// 请求失败调用次方法

			@Override
			public void onFailure(HttpException error, String msg) {
				int exceptionCode = error.getExceptionCode();
				if (exceptionCode == 0) {

					loginfromlocal();
				} else if (exceptionCode == 406) {
					ToastUtil.showShort(getApplicationContext(), "用户名或密码错误！");

				}
			}

			// 请求成功调用此方法
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				/** 获取服务器返回的Token，并储存到SP中 */
				String token = responseInfo.result;
				tokenSp = getSharedPreferences("Token", MODE_PRIVATE);
				tokenSp.edit().putString("token", token).commit();
				System.out.println("输出结果为" + token);

				/** --------进入选择页面-------- */
				enterInfo();
				ToastUtil.showShort(getApplicationContext(), "有网络连接，在线登陆！");
				insertUser();
			}

		});

	}

	/**
	 * 本地登录 时间：2016年11月8日16:27:45
	 */
	protected void loginfromlocal() {
		List<User> queryUser = mgr.queryUser();
		for (User user : queryUser) {
			if (user.username.equals(userName) && user.password.equals(passWord)) {
				enterInfo();
				ToastUtil.showShort(this, "无网络连接，离线登陆！");
				// } else if (!user.username.equals(userName) ||
				// !user.password.equals(passWord)) {
				// ToastUtil.showShort(this, "用户名或密码错误！");
			}
		}
	}

	/** 把登陆信息存到数据库 */
	private void insertUser() {
		users = new ArrayList<User>();
		User user1 = new User(userName, passWord);
		users.add(user1);
		mgr.addUser(users);
	}

	/**
	 * 获取用户的详细个人信息 时间：2016年10月20日14:18:03
	 *
	 */

	// 2016年10月19日14:36:23

	protected void enterInfo() {
//		LoadingDialog ld = new LoadingDialog(this);
//		ld.show();
		Intent intent = new Intent(this, MainActivity3.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 应用的最后一个Activity关闭时应释放DB

	}

}