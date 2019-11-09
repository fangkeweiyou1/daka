package com.jiyun_greendao;

import android.content.Context;
import android.util.Log;

import com.jiyun_greendao.info.DaoMaster;
import com.jiyun_greendao.info.DaoSession;
import com.jiyun_greendao.info.OrderLogisInfoDao;
import com.jiyun_greendao.info.WordInfoDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by zhangyuncai on 2019/10/15.
 */
public class DBOpenHelper extends DataHelper<DaoMaster, DaoSession> {

    private DBOpenHelper.DevOpenHelper mDevOpenHelper;

    private static DBOpenHelper mDBOpenHelper;
    public static Context dbContext;


    public DBOpenHelper(Context context) {
        super(context);
    }

    public static OrderLogisInfoDao getOrderLogisInfoDao() {
        return getDaoSession(dbContext).getOrderLogisInfoDao();
    }
    public static WordInfoDao getWordInfoDao() {
        return getDaoSession(dbContext).getWordInfoDao();
    }

    public static DaoSession getDaoSession(Context context) {
        dbContext = context;
        create(context);
        DaoSession daoSession = mDBOpenHelper.getDaoSession();
        if (daoSession == null) {
            mDBOpenHelper.init(context);
            daoSession = mDBOpenHelper.getDaoSession();
        }
        return daoSession;
    }


    private static void create(Context context) {

        if (mDBOpenHelper == null) {
            mDBOpenHelper = new DBOpenHelper(context);
        }
    }

    @Override
    public void init(Context context) {

        mDevOpenHelper = new DBOpenHelper.DevOpenHelper(context);
        mDatabase = mDevOpenHelper.mDatabase;
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();

    }


    public static class DevOpenHelper extends DaoMaster.OpenHelper {

        public Database mDatabase;

        public DevOpenHelper(Context context) {
            this(context, DB_NAME);
        }

        public DevOpenHelper(Context context, String name) {
            super(context, name);
            mDatabase = ENCRYPTED ? getEncryptedWritableDb("super-secret") : getWritableDb();
        }


        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
//            super.onUpgrade(db, oldVersion, newVersion);
            Log.i("greenDAO", "migrating schema from version " + oldVersion + " to " + newVersion);
            dropAllTables(db, true);
            onCreate(db);
            for (int migrateVersion = oldVersion + 1; migrateVersion <= newVersion; migrateVersion++) {
                switch (migrateVersion) {
              /*      case 2:
                        db.execSQL("ALTER TABLE INHABITANT ADD COLUMN 'GENDER' INTEGER NOT NULL DEFAULT '0';");
                        break;*/
//                    case 4:
//                        db.execSQL("ALTER TABLE REPORT_INFO ADD COLUMN 'PATROL_IN_TIME' TEXT;");
//                        db.execSQL("ALTER TABLE REPORT_INFO ADD COLUMN 'PATROL_OUT_TIME' TEXT;");
//                        db.execSQL("ALTER TABLE REPORT_INFO ADD COLUMN 'STAR' INTEGER NOT NULL DEFAULT '0';");
//                        break;
                }
            }
        }

        private void dropAllTables(Database db, boolean b) {
            //todo 这里升级数据库要用到
            OrderLogisInfoDao.dropTable(db, b);
            WordInfoDao.dropTable(db, b);
        }
        //                sql语句示例
//                db.execSQL("ALTER TABLE INHABITANT ADD COLUMN 'GENDER' INTEGER NOT NULL DEFAULT '0';");
//                db.execSQL("ALTER TABLE INHABITANT ADD COLUMN 'SPECIES' TEXT;");
//                db.execSQL("ALTER TABLE INVERTEBRATE ADD COLUMN 'SPECIES' TEXT;");
//                db.execSQL("ALTER TABLE PLANT ADD COLUMN 'SPECIES' TEXT;");
//                db.execSQL("ALTER TABLE CORAL ADD COLUMN 'SPECIES' TEXT;");
    }

}
