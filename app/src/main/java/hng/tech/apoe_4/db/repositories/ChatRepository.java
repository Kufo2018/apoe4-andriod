package hng.tech.apoe_4.db.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import hng.tech.apoe_4.db.ApoeDatabase;
import hng.tech.apoe_4.db.dao.ChatDao;
import hng.tech.apoe_4.models.QuestionAnswerChat;

public class ChatRepository {
    private ChatDao chatDao;

    public ChatRepository(Application application) {
        ApoeDatabase database = ApoeDatabase.getInstance();
        chatDao = database.chatDao();
    }

    private static class InsertChatAsyncClass extends AsyncTask<QuestionAnswerChat, Void, Void>{

        private ChatDao chatDao;

        public InsertChatAsyncClass(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected Void doInBackground(QuestionAnswerChat... questionAnswerChats) {
            chatDao.insertSingleElement(questionAnswerChats[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("DatabaseOp", "Chat Element inserted");
        }
    }

    public void insertChatElement(QuestionAnswerChat questionAnswerChat){
        new InsertChatAsyncClass(chatDao).execute(questionAnswerChat);
    }
}
