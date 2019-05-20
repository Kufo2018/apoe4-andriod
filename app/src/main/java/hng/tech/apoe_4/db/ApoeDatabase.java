package hng.tech.apoe_4.db;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import hng.tech.apoe_4.models.QuestionAnswerChat;
import hng.tech.apoe_4.utils.MainApplication;

@Database(entities = {QuestionAnswerChat.class}, version = 1)
public abstract class ApoeDatabase extends RoomDatabase {
    private static volatile ApoeDatabase INSTANCE;

    public static ApoeDatabase getInstance(){
        if (INSTANCE == null){
            synchronized (ApoeDatabase.class){
                INSTANCE = Room.databaseBuilder(MainApplication.getInstance(), ApoeDatabase.class,
                        "apoe_database").build();
            }
        }

        return INSTANCE;
    }
}
