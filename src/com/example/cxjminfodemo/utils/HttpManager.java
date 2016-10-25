package com.example.cxjminfodemo.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.entity.StringEntity;

import com.example.cxjminfodemo.RcConstant;
import com.example.cxjminfodemo.db.DBManager;
import com.example.cxjminfodemo.dto.Family;
import com.example.cxjminfodemo.dto.Personal;
import com.example.cxjminfodemo.server.dto.CjUser;
import com.example.cxjminfodemo.server.dto.FamilyDTO;
import com.example.cxjminfodemo.server.dto.FamilyMemberDTO;
import com.example.cxjminfodemo.server.dto.MemberDTO;
import com.example.cxjminfodemo.server.dto.UserDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;

public class HttpManager extends HttpUtils {
	Gson gson = new Gson();
	HttpUtils httpUtils = new HttpUtils(5000);
	public FamilyMemberDTO dto;
	public boolean isAlive = true;
	public boolean isError = false;
	public String errorMessage = "";
	Context context;
	DBManager db;

	public HttpManager(Context context) {
		this.context = context;
		db = new DBManager(context);
	}

	List<Family> DTOtoF(List<FamilyDTO> dto) {
		List<Family> familyList = new ArrayList<Family>();
		for (FamilyDTO d : dto) {
			Family family = new Family();
			family.edit_jtbh = d.getAab999();
			family.edit_hzxm = d.getAab400();
			family.edit_gmcfzh = d.getAae135();
			family.edit_jhzzjlx = d.getAac058();
			family.edit_hjbh = d.getAab401();
			family.edit_cjqtbxrs = d.getBab041();
			family.edit_lxdh = d.getAae005();
			family.edit_hkxxdz = d.getAae006();
			family.edit_djrq = d.getAab050();

			family.isEdit = "0";
			family.isUpload = "1";
			familyList.add(family);
		}
		return familyList;
	}

	List<Personal> DTOtoM(List<MemberDTO> dto) {
		List<Personal> personalList = new ArrayList<Personal>();
		for (MemberDTO d : dto) {
			Personal personal = new Personal();
			personal.edit_cbrxm = d.getAac003();
			personal.edit_gmcfzh = d.getAae135();
			personal.edit_mz = d.getAac005();
			personal.edit_xb = d.getAac004();
			personal.edit_csrq = d.getAac006();
			personal.edit_cbrylb = d.getBac067();
			personal.edit_cbrq = d.getAac030();
			personal.edit_yhzgx = d.getAac069();
			personal.edit_xxjzdz = d.getAae006();
			personal.edit_hkxz = d.getAac009();
			personal.HZSFZ = d.getHzsfz();

			personal.edit_zjlx = d.getAac058();
			personal.edit_lxdh = d.getAae005();
			personal.isUpload = "1";
			personalList.add(personal);
		}
		return personalList;
	}

	List<MemberDTO> MtoDTO(List<Personal> dto) {
		List<MemberDTO> personalList = new ArrayList<MemberDTO>();
		for (Personal d : dto) {
			MemberDTO personal = new MemberDTO();
			personal.setAac003(d.edit_cbrxm);
			personal.setAae135(d.edit_gmcfzh);
			personal.setAac005(d.edit_mz);
			personal.setAac004(d.edit_xb);
			personal.setAac006(d.edit_csrq);
			personal.setBac067(d.edit_cbrylb);
			personal.setAac030(d.edit_cbrq);
			personal.setAac069(d.edit_yhzgx);
			personal.setAae006(d.edit_xxjzdz);
			personal.setAac009(d.edit_hkxz);
			personal.setHzsfz(d.HZSFZ);

			personal.setAac058(d.edit_zjlx);
			personal.setAae005(d.edit_lxdh);
			personalList.add(personal);
		}
		return personalList;
	}

	List<FamilyMemberDTO> FMtoDTO() {
		ArrayList<Family> listFamily = db.queryFamily();
		List<FamilyMemberDTO> FM = new ArrayList<FamilyMemberDTO>();

		return null;
	}

	/**
	 * get请求 用于下载家庭与成员信息 2016年10月24日14:59:49
	 * 
	 * @return
	 * 
	 * @return
	 * 
	 */
	public void getJbxx(String countryCode) {

		RequestParams params = new RequestParams();

		params.addHeader("Content-Type", "application/json");
		params.addHeader("Accept", "application/json");
		String url = RcConstant.getPath + countryCode;
		httpUtils.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
			// 请求失败调用次方法
			@Override
			public void onFailure(HttpException error, String msg) {
				isError = true;
				int exceptionCode = error.getExceptionCode();
				if (exceptionCode == 0) {
					errorMessage = "请检查网络连接是否正常！";
				}
				isAlive = false;
			}

			// 请求成功调用此方法
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				isError = false;
				String temp = arg0.result;
				System.out.println(temp);
				dto = gson.fromJson(temp, new TypeToken<FamilyMemberDTO>() {
				}.getType());
				// 存入sql
				db.addFamily(DTOtoF(dto.getJt()));
				db.addPersonal(DTOtoM(dto.getRy()));
				isAlive = false;
			}
		});
	}

	public void getCjxx(String countryCode, String usertoken) {

		RequestParams params = new RequestParams();
		String url = RcConstant.postPath + countryCode;
		params.addHeader("Content-Type", "application/json");
		params.addHeader("usertoken", usertoken);
		FamilyMemberDTO f = new FamilyMemberDTO();

		String jsonStr = gson.toJson(f);
		try {
			params.setBodyEntity(new StringEntity(jsonStr, "utf-8"));
		} catch (UnsupportedEncodingException e) {

		}

		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			// 请求失败调用次方法
			@Override
			public void onFailure(HttpException error, String msg) {
				isError = true;
				int exceptionCode = error.getExceptionCode();
				if (exceptionCode == 0) {
					errorMessage = "请检查网络连接是否正常！";
				}
				isAlive = false;
			}

			// 请求成功调用此方法
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				isError = false;
				System.out.println(arg0.result);
				isAlive = false;
			}
		});
	}
}
