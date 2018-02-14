package com.vinsonzhan.onekey.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.vinsonzhan.onekey.model.Account;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACCOUNT".
*/
public class AccountDao extends AbstractDao<Account, Long> {

    public static final String TABLENAME = "ACCOUNT";

    /**
     * Properties of entity Account.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Category = new Property(1, String.class, "category", false, "CATEGORY_NAME");
        public final static Property Title = new Property(2, String.class, "title", false, "ACCOUNT_TITLE");
        public final static Property UserName = new Property(3, String.class, "userName", false, "ACCOUNT_USERNAME");
        public final static Property Password = new Property(4, String.class, "password", false, "ACCOUNT_PASSWORD");
        public final static Property Comment = new Property(5, String.class, "comment", false, "ACCOUNT_COMMENT");
        public final static Property CreatedTime = new Property(6, Long.class, "createdTime", false, "CREATE_TIME");
        public final static Property ModifiedTime = new Property(7, Long.class, "modifiedTime", false, "MODIFIED_TIME");
    }


    public AccountDao(DaoConfig config) {
        super(config);
    }
    
    public AccountDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CATEGORY_NAME\" TEXT," + // 1: category
                "\"ACCOUNT_TITLE\" TEXT," + // 2: title
                "\"ACCOUNT_USERNAME\" TEXT," + // 3: userName
                "\"ACCOUNT_PASSWORD\" TEXT," + // 4: password
                "\"ACCOUNT_COMMENT\" TEXT," + // 5: comment
                "\"CREATE_TIME\" INTEGER," + // 6: createdTime
                "\"MODIFIED_TIME\" INTEGER);"); // 7: modifiedTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Account entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(2, category);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(4, userName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(6, comment);
        }
 
        Long createdTime = entity.getCreatedTime();
        if (createdTime != null) {
            stmt.bindLong(7, createdTime);
        }
 
        Long modifiedTime = entity.getModifiedTime();
        if (modifiedTime != null) {
            stmt.bindLong(8, modifiedTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Account entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(2, category);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(4, userName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(6, comment);
        }
 
        Long createdTime = entity.getCreatedTime();
        if (createdTime != null) {
            stmt.bindLong(7, createdTime);
        }
 
        Long modifiedTime = entity.getModifiedTime();
        if (modifiedTime != null) {
            stmt.bindLong(8, modifiedTime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Account readEntity(Cursor cursor, int offset) {
        Account entity = new Account( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // category
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // password
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // comment
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // createdTime
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // modifiedTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Account entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCategory(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPassword(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setComment(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreatedTime(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setModifiedTime(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Account entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Account entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Account entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}