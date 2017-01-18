package com.art.fmblzf.aidl.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/17.
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";


    public static final String AUTHORITY = "com.art.fmblzf.aidl.provider.book.provider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"user",USER_URI_CODE);
    }

    /**
     * 根据请求的Uri再通过UriMatcher的match匹配方法获取已经关联的当前Uri的标示code
     * @param uri
     * @return
     */
    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:break;
        }
        return tableName;
    }

    private Context mContent;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Log.i(TAG,"onCreate , The current Thread name:"+Thread.currentThread().getName());
        mContent = getContext();
        //一般不建议在主线程进行数据库数据的初始化操作
        iniProviderData();
        //创建成功时返回True,否则返回False
        return true;
    }

    /**
     * 初始化内容提供者的数据
     */
    private void iniProviderData(){
        mDb = new DbOpenHelper(mContent).getWritableDatabase();
        mDb.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4,'Ios');");
        mDb.execSQL("insert into book values(5,'Html5');");
        mDb.execSQL("insert into user values(1,'jake',1);");
        mDb.execSQL("insert into user values(2,'jasmine',0);");
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG,"query , The current Thread name:"+Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null){
            return null;
        }
        return mDb.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.i(TAG,"getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(TAG,"insert");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: "+ uri);
        }
        mDb.insert(table,null,values);
        mContent.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG,"delete");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        int count = mDb.delete(table,selection,selectionArgs);
        if (count > 0){
            mContent.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG,"update");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        int count = mDb.update(table,values,selection,selectionArgs);
        if (count>0){
            mContent.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }
}
