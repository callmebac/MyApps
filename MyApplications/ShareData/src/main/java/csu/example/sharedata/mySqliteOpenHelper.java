package csu.example.sharedata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class mySqliteOpenHelper extends SQLiteOpenHelper {

	public mySqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("Create table personA(id int ,x double ,y double,z double );");
		
		
		db.execSQL("Create table personB(id int ,x double ,y double,z double );");
		
	
		db.execSQL("Create table personC(id int ,x double ,y double,z double );");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	} 
}
