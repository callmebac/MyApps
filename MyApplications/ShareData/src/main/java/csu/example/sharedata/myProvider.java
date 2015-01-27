package csu.example.sharedata;

import android.R.integer;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.*;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class myProvider extends ContentProvider {

	final static String TABLE_NAME1 = "personA";
	final static String TABLE_NAME2 = "personB";
	final static String TABLE_NAME3 = "personC";
	private static final String DATABASE_NAME = "sqliteDB.db";
	private static String AUTHORITY = "csu.example.sharedata.myProvider";
	private static final int DATABASE_VERSION = 1;


	private static int trun = 1;
	private static int twalk = 2;
	private static int tsit = 3;

	private static String id = "id" ;
	private static final String x = "x" ;
	private static final String y = "y";
	private static final String z = "z" ;

	mySqliteOpenHelper mSqliteOpenHelper;

	private static final UriMatcher sMatcher;
	static {
		sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		sMatcher.addURI(AUTHORITY, TABLE_NAME1, 1);
		sMatcher.addURI(AUTHORITY, TABLE_NAME1 + "/#", 8);

		sMatcher.addURI(AUTHORITY, TABLE_NAME2, 2);
		sMatcher.addURI(AUTHORITY, TABLE_NAME2 + "/#", 9);

		sMatcher.addURI(AUTHORITY, TABLE_NAME3, 3);
		sMatcher.addURI(AUTHORITY, TABLE_NAME3 + "/#", 10);

		// sMatcher.addURI(AUTHORITY,TABLE_NAME1,1);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME1,2);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME1,3);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME1,4);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME2,1);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME2,2);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME2,3);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME2,4);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME3,1);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME3,2);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME3,3);
		// sMatcher.addURI(AUTHORITY,TABLE_NAME3,4);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mSqliteOpenHelper = new mySqliteOpenHelper(this.getContext(),
				"sqliteDB", null, 1);
		return true;

	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Log.d("！！！！！！", "！！！！！！！！");
		SQLiteDatabase db = mSqliteOpenHelper.getReadableDatabase();
		Cursor c = null;

		switch (sMatcher.match(uri)) {
		case 1:
		case 8:
			c = db.query(TABLE_NAME1, projection, selection, selectionArgs,
					null, null, sortOrder);
Log.i("personA","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			break;
		case 2:
		case 9:
			c = db.query(TABLE_NAME2, projection, selection, selectionArgs,
					null, null, sortOrder);
Log.i("personB","----------------&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			break;
		case 3:
		case 10:
			c = db.query(TABLE_NAME3, projection, selection, selectionArgs,
					null, null, sortOrder);
Log.i("personC","kkkkkkkkkkkkkkkkkkkk");
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

Log.d("！！！！！！", "！！！！！！！！");
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}



}
