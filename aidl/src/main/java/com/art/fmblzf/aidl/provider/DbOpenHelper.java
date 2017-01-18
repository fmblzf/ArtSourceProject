package com.art.fmblzf.aidl.provider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/1/18.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";

    private static final int DB_VERSION = 1;

    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
            + BOOK_TABLE_NAME +"(_id INTERGER PRIMARY KEY,name TEXT)";
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE_NAME +"(_id INTERGER PRIMARY KEY,name TEXT,sex INT)";

    public DbOpenHelper(Context context){
        //可以读取外部配置文件，获取最新的数据库版本号，或者直接在代码中设置DB_VERSION
        super(context,DB_NAME,null,DB_VERSION);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化创建表格
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //进行数据库升级操作，主要有：
        //1.添加表格字段；
        //2.创建新增表格；
        //3.初始化表格数据；
        //可以读取外部文件获取执行脚本，在此直接执行即可。
    }
}
