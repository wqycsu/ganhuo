package com.wqy.ganhuo.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.wqy.ganhuo.greendao.IOSCache;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table IOSCACHE.
*/
public class IOSCacheDao extends AbstractDao<IOSCache, Long> {

    public static final String TABLENAME = "IOSCACHE";

    /**
     * Properties of entity IOSCache.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ObjectId = new Property(1, String.class, "objectId", false, "OBJECT_ID");
        public final static Property PlatformType = new Property(2, Integer.class, "platformType", false, "PLATFORM_TYPE");
        public final static Property Who = new Property(3, String.class, "who", false, "WHO");
        public final static Property PublishedAt = new Property(4, String.class, "publishedAt", false, "PUBLISHED_AT");
        public final static Property Desc = new Property(5, String.class, "desc", false, "DESC");
        public final static Property Type = new Property(6, String.class, "type", false, "TYPE");
        public final static Property Url = new Property(7, String.class, "url", false, "URL");
        public final static Property CreatedAt = new Property(8, String.class, "createdAt", false, "CREATED_AT");
        public final static Property UpdatedAt = new Property(9, String.class, "updatedAt", false, "UPDATED_AT");
        public final static Property Used = new Property(10, Boolean.class, "used", false, "USED");
        public final static Property Page = new Property(11, Integer.class, "page", false, "PAGE");
    };


    public IOSCacheDao(DaoConfig config) {
        super(config);
    }
    
    public IOSCacheDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IOSCACHE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'OBJECT_ID' TEXT UNIQUE ," + // 1: objectId
                "'PLATFORM_TYPE' INTEGER," + // 2: platformType
                "'WHO' TEXT," + // 3: who
                "'PUBLISHED_AT' TEXT," + // 4: publishedAt
                "'DESC' TEXT," + // 5: desc
                "'TYPE' TEXT," + // 6: type
                "'URL' TEXT," + // 7: url
                "'CREATED_AT' TEXT," + // 8: createdAt
                "'UPDATED_AT' TEXT," + // 9: updatedAt
                "'USED' INTEGER," + // 10: used
                "'PAGE' INTEGER);"); // 11: page
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_IOSCACHE_PAGE ON IOSCACHE" +
                " (PAGE);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'IOSCACHE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, IOSCache entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindString(2, objectId);
        }
 
        Integer platformType = entity.getPlatformType();
        if (platformType != null) {
            stmt.bindLong(3, platformType);
        }
 
        String who = entity.getWho();
        if (who != null) {
            stmt.bindString(4, who);
        }
 
        String publishedAt = entity.getPublishedAt();
        if (publishedAt != null) {
            stmt.bindString(5, publishedAt);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(6, desc);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(7, type);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
 
        String createdAt = entity.getCreatedAt();
        if (createdAt != null) {
            stmt.bindString(9, createdAt);
        }
 
        String updatedAt = entity.getUpdatedAt();
        if (updatedAt != null) {
            stmt.bindString(10, updatedAt);
        }
 
        Boolean used = entity.getUsed();
        if (used != null) {
            stmt.bindLong(11, used ? 1l: 0l);
        }
 
        Integer page = entity.getPage();
        if (page != null) {
            stmt.bindLong(12, page);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public IOSCache readEntity(Cursor cursor, int offset) {
        IOSCache entity = new IOSCache( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // objectId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // platformType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // who
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // publishedAt
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // desc
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // type
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // url
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // createdAt
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // updatedAt
            cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0, // used
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11) // page
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, IOSCache entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setObjectId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPlatformType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setWho(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPublishedAt(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDesc(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setType(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCreatedAt(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUpdatedAt(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUsed(cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0);
        entity.setPage(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(IOSCache entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(IOSCache entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
