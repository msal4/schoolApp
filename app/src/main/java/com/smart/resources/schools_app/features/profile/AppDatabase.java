package com.smart.resources.schools_app.features.profile;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public static AppDatabase INSTANCE ;



    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "User")
                    .allowMainThreadQueries().addMigrations(MIGRATION_1_2).build();
        }
        return INSTANCE;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `user` (`uid` INTEGER, "
                    +"`access_token` TEXT," +
                    "`img` TEXT," +
                    "`username` TEXT," +
                    "`user_type` INTEGER,"+
                    " PRIMARY KEY (`uid`))");
        }
    };

    public void insert(User user){
        INSTANCE.userDao().insert(user);
    }


    public void update(int id,String newImg){
        INSTANCE.userDao().updateImg(id,newImg);
    }

    public List<User> getAllUsers(){
        return INSTANCE.userDao().getAll();
    }

    public void deleteUserById(int id){
        INSTANCE.userDao().deleteUserById(id);
    }

    public List<User> getUserById(int ids[]){
        return INSTANCE.userDao().loadAllByIds(ids);
    }

}
