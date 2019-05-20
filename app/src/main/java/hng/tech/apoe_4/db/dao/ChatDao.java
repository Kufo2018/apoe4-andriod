package hng.tech.apoe_4.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import hng.tech.apoe_4.models.QuestionAnswerChat;

@Dao
public interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleElement(QuestionAnswerChat questionAnswerChat);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleElements(List<QuestionAnswerChat> questionAnswerChats);

    @Query("SELECT * FROM QuestionAnswerChat WHERE time = :currentDay")
    void getChatForCurrentDay(String currentDay);
}
