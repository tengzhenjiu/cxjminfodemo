package com.megvii.idcardproject.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lapism.searchview.SearchItem;
import com.megvii.idcardproject.R;
import com.megvii.idcardproject.dto.CJUrl;
import com.megvii.idcardproject.dto.Code;
import com.megvii.idcardproject.dto.Family;
import com.megvii.idcardproject.dto.Personal;
import com.megvii.idcardproject.dto.User;
import com.megvii.idcardproject.dto.UserDetail;
import com.megvii.idcardproject.dto.Xzqh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/*
��ͥ��Ϣ
���	�ֶ�����	����	����	����	�ǿ�	��ע
				AAB999	��ͥ���	Varchar2	16		Ϊ�մ����µǼǼ�ͥ
getEdit_hzxm	AAB400	��������	Varchar2	50	��	
getEdit_jhzzjlx	AAC058	����֤������	Varchar2	3	��	�������
getEdit_gmcfzh	AAE135	����֤������	Varchar2	20	��	
				AAB401	�������	Varchar2	20		
getEdit_cjqtbxrs	BAB041	�α�����	number	3		
getEdit_lxdh	AAE005	��ϵ�绰	Varchar2	50		
getEdit_hkxxdz	AAE006	סַ	Varchar2	100		
getEdit_djrq	AAB050	�Ǽ�����	Varchar2	10	��	��ʽ��yyyymmdd

��Ա��Ϣ
���	�ֶ�����	����	����	����	�ǿ�	��ע
				AAC999	���˱��	Varchar2	16		Ϊ�մ����µǼ���Ա
getEdit_cbrxm	AAC003	����	Varchar2	50	��	
getEdit_zjlx	AAC058	֤������	Varchar2	3	��	�������
getEdit_gmcfzh	AAE135	�������ݺ���	Varchar2	20	��	
getEdit_mz		AAC005	����	Varchar2	3	��	�������
	
getEdit_xb		AAC004	�Ա�	Varchar2	3	��	�������
getEdit_csrq	AAC006	��������	Varchar2	10	��	��ʽ��yyyymmdd
getEdit_cbrylb	BAC067	�α���Ա���	Varchar2	3	��	�������
getEdit_cbrq	AAC030	�Ǽ�����	Varchar2	10	��	��ʽ��yyyymmdd
getEdit_yhzgx	AAC069	�뻧����ϵ	Varchar2	3		�������
	
getEdit_lxdh	AAE005	��ϵ�绰	Varchar2	50		
getEdit_xxjzdz	AAE006	סַ	Varchar2	100		
getEdit_hkxz	AAC009	��������	Varchar2	3		�������
getHZSFZ		HZSFZ	�������ݺ���	Varchar2	20	��	*/

public class DBManager {
	private final int BUFFER_SIZE = 400000;
	private DBHelper helper;
	private SQLiteDatabase db;
	public static final String DB_NAME = "test.db"; // ��������ݿ��ļ���
	public static final String PACKAGE_NAME = "com.neuqsoft.cxjmcj";
	public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME
			+ "/database"; // ���ֻ��������ݿ��λ��(/data/data/com.cssystem.activity/cssystem.db)
	private Context context;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		// ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// ����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
		db = helper.getWritableDatabase();
		this.context = context;
	}

	/**
	 * add persons
	 * 
	 * @param persons
	 */
	public void addUser(List<User> list) {
		db.beginTransaction(); // ��ʼ����
		try {
			for (User user : list) {
				db.execSQL("REPLACE INTO user  VALUES( null, ?,?)", new Object[] { user.username, user.password });
			}
			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	public void addUserDetail(List<UserDetail> list) {
		db.beginTransaction(); // ��ʼ����
		try {
			for (UserDetail userDetail : list) {
				db.execSQL("REPLACE INTO userdetail  VALUES( ?, ?,?, ?,?, ?,?, ?,?, ?)",
						new Object[] { userDetail.taskid, userDetail.account, userDetail.city, userDetail.cjarea,
								userDetail.downloadflag, userDetail.sfcl, userDetail.taskdesc, userDetail.taskstatus,
								userDetail.uploadflag, userDetail.serverBaseurl });
			}
			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	public void addPersonal(List<Personal> personals) {
		db.beginTransaction(); // ��ʼ����
		try {
			for (Personal personal : personals) {
				// 14���ֶ�
				db.execSQL("REPLACE INTO personal VALUES(?,?, ?,?,?,?,?,   ?,?,?,?,?  ,?,?,?,?,?  ,?,?)",
						new Object[] { null,personal.getLsh(), personal.getEdit_grbh(), personal.getEdit_cbrxm(),
								personal.getEdit_zjlx(), personal.getEdit_gmcfzh(), personal.getEdit_mz(),
								personal.getEdit_xb(), personal.getEdit_csrq(), personal.getEdit_cbrylb(),
								personal.getEdit_cbrq(), personal.getEdit_yhzgx(), personal.getEdit_lxdh(),
								personal.getEdit_xxjzdz(), personal.getEdit_hkxz(), personal.getEdit_jf(),
								personal.getHZSFZ(), personal.getIsEdit(), personal.getIsUpload() });
			}
			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	public void addFamily(List<Family> familys) {
		db.beginTransaction(); // ��ʼ����
		try {
			// 9���ֶ�
			for (Family family : familys) {
				db.execSQL("REPLACE INTO family VALUES(?,?, ?,?,?,?,?   ,?,?,?,?,?,  ?,?,?)",
						new Object[] { null,family.getLsh(), family.getEdit_jtbh(), family.getEdit_hzxm(),
								family.getEdit_jhzzjlx(), family.getEdit_gmcfzh(), family.getEdit_hjbh(),
								family.getEdit_cjqtbxrs(), family.getEdit_lxdh(), family.getEdit_hkxxdz(),
								family.getEdit_jtxxdz(), family.getEdit_djrq(), family.getIsEdit(), family.isUpload,
								family.getXzqh() });
			}
			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	public void addCode(List<Code> list) {
		db.beginTransaction(); // ��ʼ����
		try {
			for (Code code : list) {
				db.execSQL("REPLACE INTO code  VALUES( null, ?,?,?,?)",
						new Object[] { code.AAA100, code.AAA101, code.AAA103, code.AAA102 });
			}
			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	public void addXzqh(Xzqh xzqh) {
		db.beginTransaction(); // ��ʼ����
		try {

			db.execSQL("REPLACE INTO xzqh VALUES( null, ?,?,?,?, ?,?)",
					new Object[] { xzqh.getCountry(), xzqh.getTown(), xzqh.getCounty(), xzqh.getCity(),
							xzqh.getProvince(), xzqh.getName()});

			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	public void addUrl(CJUrl u) {
		db.beginTransaction(); // ��ʼ����
		try {
			db.execSQL("REPLACE INTO url  VALUES( null, ?,?,?)",
					new Object[] { u.getCjarea(), u.getName(), u.getUrl() });
			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	/**
	 * update personal
	 * 
	 * @param personal
	 */
	public void updatePersonal(Personal personal) {
		ContentValues cv = new ContentValues();
		cv.put("LSH", personal.getLsh());
		cv.put("AAC003", personal.getEdit_cbrxm());
		cv.put("AAE135", personal.getEdit_gmcfzh());
		cv.put("AAC005", personal.getEdit_mz());
		cv.put("AAC004", personal.getEdit_xb());
		cv.put("AAC006", personal.getEdit_csrq());
		cv.put("BAC067", personal.getEdit_cbrylb());
		cv.put("AAC999", personal.getEdit_grbh());
		cv.put("AAC030", personal.getEdit_cbrq());
		cv.put("AAC069", personal.getEdit_yhzgx());
		cv.put("AAE006", personal.getEdit_xxjzdz());
		cv.put("AAC009", personal.getEdit_hkxz());
		cv.put("HZSFZ", personal.getHZSFZ());
		cv.put("AAC058", personal.getEdit_zjlx());
		cv.put("AAE005", personal.getEdit_lxdh());
		cv.put("JFBZ", personal.getEdit_jf());
		cv.put("ISEDIT", personal.getIsEdit());
		cv.put("ISUPLOAD", personal.getIsUpload());

		db.update("personal", cv, "_id = ?", new String[] { String.valueOf(personal.getId()) });
	}

	public void updateFamily(Family family) {
		ContentValues cv = new ContentValues();
		cv.put("LSH", family.getLsh());
		cv.put("AAB999", family.getEdit_jtbh());
		cv.put("AAB400", family.getEdit_hzxm());
		cv.put("AAE135", family.getEdit_gmcfzh());
		cv.put("AAC058", family.getEdit_jhzzjlx());
		cv.put("AAB401", family.getEdit_hjbh());
		cv.put("BAB041", family.getEdit_cjqtbxrs());
		cv.put("AAE005", family.getEdit_lxdh());
		cv.put("AAC010", family.getEdit_hkxxdz());
		cv.put("AAE006", family.getEdit_jtxxdz());
		cv.put("AAB050", family.getEdit_djrq());
		cv.put("XZQH", family.getXzqh());
		cv.put("ISEDIT", family.getIsEdit());
		cv.put("ISUPLOAD", family.getIsUpload());

		db.update("family", cv, "_id = ?", new String[] { String.valueOf(family.getId()) });
	}

	/**
	 * delete old family
	 * 
	 * @param family
	 */
	public void deleteFamily(Family family) {
		db.delete("family", "_id = ?", new String[] { family.id });
		for (Personal personal : queryPersonal(family.getEdit_jtbh())) {
			deletePersonal(personal);
		}
	}

	/**
	 * delete old personal
	 * 
	 * @param personal
	 */
	public void deletePersonal(Personal personal) {
		db.delete("personal", "_id = ?", new String[] { personal.id });
	}

	public void deleteCode(String AAA100) {
		db.delete("code", "AAA100 = ?", new String[] { AAA100 });
	}

	/**
	 * query all persons, return list
	 * 
	 * @return List<Person>
	 */
	public List<User> queryUser() {
		ArrayList<User> users = new ArrayList<User>();
		Cursor c = queryTheCursor("user");
		while (c.moveToNext()) {
			User user = new User();
			user._id = c.getInt(c.getColumnIndex("_id"));
			user.username = c.getString(c.getColumnIndex("username"));
			user.password = c.getString(c.getColumnIndex("password"));
			users.add(user);
		}
		c.close();
		return users;
	}

	public List<Code> queryCode(String var) {
		ArrayList<Code> codes = new ArrayList<Code>();
		Cursor c = db.rawQuery("SELECT * FROM code where AAA100='" + var + "'", null);
		while (c.moveToNext()) {
			Code code = new Code();
			code._id = c.getInt(c.getColumnIndex("_id"));
			code.AAA100 = c.getString(c.getColumnIndex("AAA100"));
			code.AAA101 = c.getString(c.getColumnIndex("AAA101"));
			code.AAA103 = c.getString(c.getColumnIndex("AAA103"));
			code.AAA102 = c.getString(c.getColumnIndex("AAA102"));
			codes.add(code);
		}
		c.close();
		return codes;
	}

	public String queryCodeFromName(String AAA103) {
		String AAA102 = "";
		Cursor c = db.rawQuery("SELECT AAA102 FROM code where AAA103='" + AAA103 + "'", null);
		while (c.moveToNext()) {
			AAA102 = c.getString(c.getColumnIndex("AAA102"));
		}
		c.close();
		return AAA102;
	}

	public String queryCodeFromCode(String AAA102) {
		String AAA103 = "";
		Cursor c = db.rawQuery("SELECT AAA103 FROM code where AAA102='" + AAA102 + "'", null);
		while (c.moveToNext()) {
			AAA103 = c.getString(c.getColumnIndex("AAA103"));
		}
		c.close();
		return AAA103;
	}

	public Xzqh queryXzqh(String country) {
		ArrayList<Code> codes = new ArrayList<Code>();
		Cursor c = db.rawQuery("SELECT * FROM xzqh where COUNTRY='" + country + "'", null);
		Xzqh xzqh = new Xzqh();
		while (c.moveToNext()) {
			xzqh.country = c.getString(c.getColumnIndex("COUNTRY"));
			xzqh.town = c.getString(c.getColumnIndex("TOWN"));
			xzqh.county = c.getString(c.getColumnIndex("COUNTY"));
			xzqh.city = c.getString(c.getColumnIndex("CITY"));
			xzqh.province = c.getString(c.getColumnIndex("PROVINCE"));
			xzqh.name = c.getString(c.getColumnIndex("NAME"));
//			xzqh.sfcl = c.getString(c.getColumnIndex("SFCL"));
//			xzqh.cjzt = c.getString(c.getColumnIndex("CJZT"));
		}
		c.close();
		return xzqh;
	}

	public CJUrl queryUrl(String cjarea) {
		Cursor c = db.rawQuery("SELECT * FROM code where COUNTRYCODE='" + cjarea + "'", null);
		CJUrl url = new CJUrl();
		while (c.moveToNext()) {
			url.cjarea = c.getString(c.getColumnIndex("COUNTRYCODE"));
			url.name = c.getString(c.getColumnIndex("NAME"));
			url.url = c.getString(c.getColumnIndex("URL"));
		}
		c.close();
		return url;
	}

	public int Quer(String pwd, String name) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		Cursor cursor = db.rawQuery("select * from user where username=?", new String[] { name });
		// hashmap.put("name",db.rawQuery("select * from User where name=?",new
		// String[]{name}).toString());
		if (cursor.getCount() > 0) {
			Cursor pwdcursor = db.rawQuery("select * from user where password=? and username=?",
					new String[] { pwd, name });
			if (pwdcursor.getCount() > 0) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return 0;
		}
	}

	public List<UserDetail> queryUserDetail(String account) {
		ArrayList<UserDetail> UserDetails = new ArrayList<UserDetail>();
		Cursor c = db.rawQuery("SELECT * FROM userdetail where account='" + account + "'", null);
		while (c.moveToNext()) {
			UserDetail userDetail = new UserDetail();
			userDetail.taskid = c.getString(c.getColumnIndex("taskid"));
			userDetail.account = c.getString(c.getColumnIndex("account"));
			userDetail.city = c.getString(c.getColumnIndex("city"));
			userDetail.cjarea = c.getString(c.getColumnIndex("cjarea"));
			userDetail.downloadflag = c.getString(c.getColumnIndex("downloadflag"));
			userDetail.sfcl = c.getString(c.getColumnIndex("sfcl"));
	    	userDetail.taskdesc = c.getString(c.getColumnIndex("taskdesc"));
			userDetail.taskstatus = c.getString(c.getColumnIndex("taskstatus"));
			userDetail.uploadflag = c.getString(c.getColumnIndex("uploadflag"));
			userDetail.serverBaseurl = c.getString(c.getColumnIndex("serverBaseurl"));
			UserDetails.add(userDetail);
		}
		c.close();
		return UserDetails;
	}

	public ArrayList<Family> queryFamily() {
		ArrayList<Family> familys = new ArrayList<Family>();
		Cursor c = queryTheCursor("family");
		while (c.moveToNext()) {
			Family family = new Family();
			family.id = c.getString(c.getColumnIndex("_id"));
			family.lsh = c.getString(c.getColumnIndex("LSH"));
			family.edit_jtbh = c.getString(c.getColumnIndex("AAB999"));
			family.edit_hzxm = c.getString(c.getColumnIndex("AAB400"));
			family.edit_gmcfzh = c.getString(c.getColumnIndex("AAE135"));
			family.edit_jhzzjlx = c.getString(c.getColumnIndex("AAC058"));
			family.edit_hjbh = c.getString(c.getColumnIndex("AAB401"));
			family.edit_cjqtbxrs = c.getString(c.getColumnIndex("BAB041"));
			family.edit_lxdh = c.getString(c.getColumnIndex("AAE005"));
			family.edit_hkxxdz = c.getString(c.getColumnIndex("AAC010"));
			family.edit_jtxxdz = c.getString(c.getColumnIndex("AAE006"));
			family.edit_djrq = c.getString(c.getColumnIndex("AAB050"));
			family.xzqh = c.getString(c.getColumnIndex("XZQH"));
			family.isEdit = c.getString(c.getColumnIndex("ISEDIT"));
			family.isUpload = c.getString(c.getColumnIndex("ISUPLOAD"));
			familys.add(family);
		}
		c.close();
		return familys;
	}

	public Family queryFamilyByJtbh(String jtbh) {
		String sql = " Select * from family where AAB999='" + jtbh + "'";
		Family family = new Family();
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			family.id = c.getString(c.getColumnIndex("_id"));
			family.lsh = c.getString(c.getColumnIndex("LSH"));
			family.edit_jtbh = c.getString(c.getColumnIndex("AAB999"));
			family.edit_hzxm = c.getString(c.getColumnIndex("AAB400"));
			family.edit_gmcfzh = c.getString(c.getColumnIndex("AAE135"));
			family.edit_jhzzjlx = c.getString(c.getColumnIndex("AAC058"));
			family.edit_hjbh = c.getString(c.getColumnIndex("AAB401"));
			family.edit_cjqtbxrs = c.getString(c.getColumnIndex("BAB041"));
			family.edit_lxdh = c.getString(c.getColumnIndex("AAE005"));
			family.edit_hkxxdz = c.getString(c.getColumnIndex("AAC010"));
			family.edit_jtxxdz = c.getString(c.getColumnIndex("AAE006"));
			family.edit_djrq = c.getString(c.getColumnIndex("AAB050"));
			family.xzqh = c.getString(c.getColumnIndex("XZQH"));
			family.isEdit = c.getString(c.getColumnIndex("ISEDIT"));
			family.isUpload = c.getString(c.getColumnIndex("ISUPLOAD"));
		}
		c.close();
		return family;
	}

	public ArrayList<Family> queryFamily(String XZQH) {
		ArrayList<Family> familys = new ArrayList<Family>();
		String sql = " Select * from family where XZQH='" + XZQH + "'";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			Family family = new Family();
			family.id = c.getString(c.getColumnIndex("_id"));
			family.lsh = c.getString(c.getColumnIndex("LSH"));
			family.edit_jtbh = c.getString(c.getColumnIndex("AAB999"));
			family.edit_hzxm = c.getString(c.getColumnIndex("AAB400"));
			family.edit_gmcfzh = c.getString(c.getColumnIndex("AAE135"));
			family.edit_jhzzjlx = c.getString(c.getColumnIndex("AAC058"));
			family.edit_hjbh = c.getString(c.getColumnIndex("AAB401"));
			family.edit_cjqtbxrs = c.getString(c.getColumnIndex("BAB041"));
			family.edit_lxdh = c.getString(c.getColumnIndex("AAE005"));
			family.edit_hkxxdz = c.getString(c.getColumnIndex("AAC010"));
			family.edit_jtxxdz = c.getString(c.getColumnIndex("AAE006"));
			family.edit_djrq = c.getString(c.getColumnIndex("AAB050"));
			family.xzqh = c.getString(c.getColumnIndex("XZQH"));
			family.isEdit = c.getString(c.getColumnIndex("ISEDIT"));
			family.isUpload = c.getString(c.getColumnIndex("ISUPLOAD"));
			familys.add(family);
		}
		c.close();
		return familys;
	}

	public ArrayList<Personal> queryPersonal() {
		ArrayList<Personal> personals = new ArrayList<Personal>();
		Cursor c = queryTheCursor("personal");
		if (c != null) {
			while (c.moveToNext()) {
				Personal personal = new Personal();
				personal.id = c.getString(c.getColumnIndex("_id"));
				personal.lsh = c.getString(c.getColumnIndex("LSH"));
				personal.edit_grbh = c.getString(c.getColumnIndex("AAC999"));
				personal.edit_cbrxm = c.getString(c.getColumnIndex("AAC003"));
				personal.edit_gmcfzh = c.getString(c.getColumnIndex("AAE135"));
				personal.edit_mz = c.getString(c.getColumnIndex("AAC005"));
				personal.edit_xb = c.getString(c.getColumnIndex("AAC004"));
				personal.edit_csrq = c.getString(c.getColumnIndex("AAC006"));
				personal.edit_cbrylb = c.getString(c.getColumnIndex("BAC067"));
				personal.edit_cbrq = c.getString(c.getColumnIndex("AAC030"));
				personal.edit_yhzgx = c.getString(c.getColumnIndex("AAC069"));
				personal.edit_xxjzdz = c.getString(c.getColumnIndex("AAE006"));
				personal.edit_hkxz = c.getString(c.getColumnIndex("AAC009"));
				personal.edit_jf = c.getString(c.getColumnIndex("JFBZ"));
				personal.HZSFZ = c.getString(c.getColumnIndex("HZSFZ"));
				personal.edit_zjlx = c.getString(c.getColumnIndex("AAC058"));
				personal.edit_lxdh = c.getString(c.getColumnIndex("AAE005"));
				personal.isEdit = c.getString(c.getColumnIndex("ISEDIT"));
				personal.isUpload = c.getString(c.getColumnIndex("ISUPLOAD"));
				personals.add(personal);
			}
			c.close();
		}

		return personals;
	}

	public String queryTime(String HZSFZ) {
		String date = null;
		String sql = "Select * from personal where HZSFZ='" + HZSFZ + "'order by AAC030 desc limit 1";
		Cursor c = db.rawQuery(sql, null);
		if (c != null) {
			while (c.moveToNext()) {
				date = c.getString(c.getColumnIndex("AAC030"));
			}
			c.close();
		}
		return date;
	}
	public String queryTime2(String gmcfzh) {
		String date = null;
		String sql = "Select * from family where AAE135='" + gmcfzh + "'order by AAB050 desc limit 1";
		Cursor c = db.rawQuery(sql, null);
		if (c != null) {
			while (c.moveToNext()) {
				date = c.getString(c.getColumnIndex("AAB050"));
			}
			c.close();
		}
		return date;
	}

	// ͨ����ͥ���
	public ArrayList<Personal> queryPersonal(String HZSFZ) {
		ArrayList<Personal> personals = new ArrayList<Personal>();
		String sql = " Select * from personal where HZSFZ='" + HZSFZ + "'";
		Cursor c = db.rawQuery(sql, null);
		if (c != null) {
			while (c.moveToNext()) {
				Personal personal = new Personal();
				personal.id = c.getString(c.getColumnIndex("_id"));
				personal.lsh = c.getString(c.getColumnIndex("LSH"));
				personal.edit_grbh = c.getString(c.getColumnIndex("AAC999"));
				personal.edit_cbrxm = c.getString(c.getColumnIndex("AAC003"));
				personal.edit_gmcfzh = c.getString(c.getColumnIndex("AAE135"));
				personal.edit_mz = c.getString(c.getColumnIndex("AAC005"));
				personal.edit_xb = c.getString(c.getColumnIndex("AAC004"));
				personal.edit_csrq = c.getString(c.getColumnIndex("AAC006"));
				personal.edit_cbrylb = c.getString(c.getColumnIndex("BAC067"));
				personal.edit_cbrq = c.getString(c.getColumnIndex("AAC030"));
				personal.edit_yhzgx = c.getString(c.getColumnIndex("AAC069"));
				personal.edit_xxjzdz = c.getString(c.getColumnIndex("AAE006"));
				personal.edit_hkxz = c.getString(c.getColumnIndex("AAC009"));
				personal.edit_jf = c.getString(c.getColumnIndex("JFBZ"));
				personal.HZSFZ = c.getString(c.getColumnIndex("HZSFZ"));
				personal.isEdit = c.getString(c.getColumnIndex("ISEDIT"));
				personal.isUpload = c.getString(c.getColumnIndex("ISUPLOAD"));
				personal.edit_zjlx = c.getString(c.getColumnIndex("AAC058"));
				personal.edit_lxdh = c.getString(c.getColumnIndex("AAE005"));
				personals.add(personal);
			}
			c.close();
		}
		return personals;
	}

	// ͨ����ͥ���
	public Personal queryPersonalByGmsfzh(String gmsfzh) {
		String sql = " Select * from personal where AAE135='" + gmsfzh + "'";
		Cursor c = db.rawQuery(sql, null);
		Personal personal = new Personal();
		if (c != null) {
			while (c.moveToNext()) {
				personal.id = c.getString(c.getColumnIndex("_id"));
				personal.lsh = c.getString(c.getColumnIndex("LSH"));
				personal.edit_grbh = c.getString(c.getColumnIndex("AAC999"));
				personal.edit_cbrxm = c.getString(c.getColumnIndex("AAC003"));
				personal.edit_gmcfzh = c.getString(c.getColumnIndex("AAE135"));
				personal.edit_mz = c.getString(c.getColumnIndex("AAC005"));
				personal.edit_xb = c.getString(c.getColumnIndex("AAC004"));
				personal.edit_csrq = c.getString(c.getColumnIndex("AAC006"));
				personal.edit_cbrylb = c.getString(c.getColumnIndex("BAC067"));
				personal.edit_cbrq = c.getString(c.getColumnIndex("AAC030"));
				personal.edit_yhzgx = c.getString(c.getColumnIndex("AAC069"));
				personal.edit_xxjzdz = c.getString(c.getColumnIndex("AAE006"));
				personal.edit_hkxz = c.getString(c.getColumnIndex("AAC009"));
				personal.edit_jf = c.getString(c.getColumnIndex("JFBZ"));
				personal.HZSFZ = c.getString(c.getColumnIndex("HZSFZ"));
				personal.edit_zjlx = c.getString(c.getColumnIndex("AAC058"));
				personal.edit_lxdh = c.getString(c.getColumnIndex("AAE005"));
				personal.isEdit = c.getString(c.getColumnIndex("ISEDIT"));
				personal.isUpload = c.getString(c.getColumnIndex("ISUPLOAD"));
			}
			c.close();
		}
		return personal;
	}

	// ��ѯ�����ֶ�2016��10��13��17:36:11
	public String query_usern(Context context, String variable) {
		String account1 = "";
		String sql = " Select * from userdetail where account='" + variable + "'";

		Cursor c = db.rawQuery(sql, null);
		if (c != null) {
			while (c.moveToNext()) {
				account1 = c.getString(c.getColumnIndex("account"));
			}
			c.close();
		}

		return account1;

	}

	public String query_user(Context context, String variable) {
		String userName = "";
		String sql = " Select * from user where username='" + variable + "'";

		Cursor c = db.rawQuery(sql, null);
		if (c != null) {
			while (c.moveToNext()) {
				userName = c.getString(c.getColumnIndex("username"));
			}
			c.close();
		}

		return userName;

	}

	// ���ر�־λ��1/�ϴ���־λ��1/2016��11��2��10:44:30
	public void update_df(Context context, String Code, String flag, String flag2) {
		String cjarea1 = "";
		String sql = "update userdetail set '" + flag + "'='" + flag2 + "' where cjarea='" + Code + "'";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			cjarea1 = c.getString(c.getColumnIndex("cjarea"));
		}
		c.close();
	}

	/**
	 * query all persons, return cursor
	 * 
	 * @return Cursor
	 */
	public Cursor queryTheCursor(String name) {
		Cursor c = null;
		switch (name) {
		case "user":
			c = db.rawQuery("SELECT * FROM user", null);
			break;
		case "family":
			c = db.rawQuery("SELECT * FROM family", null);
			break;
		case "personal":
			c = db.rawQuery("SELECT * FROM personal", null);
			break;

		default:
			System.out.println("Cursor error");
		}

		return c;
	}

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}

	public ArrayList<SearchItem> querySearchItemFamily() {
		ArrayList<SearchItem> familys = new ArrayList<SearchItem>();
		String sql = "Select AAE135 from family";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			SearchItem family = new SearchItem();
			// ����Ϊ0
			family.set_icon(R.drawable.yezhu);
			// family.set_name(c.getString(c.getColumnIndex("AAB400")));
			family.set_text(c.getString(c.getColumnIndex("AAE135")));
			familys.add(family);
		}
		c.close();
		return familys;
	}

	public ArrayList<SearchItem> querySearchItemMember() {
		ArrayList<SearchItem> familys = new ArrayList<SearchItem>();
		String sql = "Select AAE135 from personal";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			SearchItem family = new SearchItem();
			// ����Ϊ0
			family.set_icon(R.drawable.member);
			family.set_name(c.getString(c.getColumnIndex("AAC003")));
			family.set_text(c.getString(c.getColumnIndex("AAE135")));
			familys.add(family);
		}
		c.close();
		return familys;
	}

	public ArrayList<SearchItem> querySearchItemMemberName() {
		ArrayList<SearchItem> familys = new ArrayList<SearchItem>();
		String sql = "Select AAC003 from personal";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			SearchItem family = new SearchItem();
			// ����Ϊ0
			family.set_icon(R.drawable.member);
			family.set_text(c.getString(c.getColumnIndex("AAC003")));
			family.set_num(c.getString(c.getColumnIndex("AAE135")));
			familys.add(family);
		}
		c.close();
		return familys;
	}

}