package hng.tech.apoe_4.db.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import hng.tech.apoe_4.db.ApoeDatabase;
import hng.tech.apoe_4.db.dao.ChatDao;
import hng.tech.apoe_4.models.QuestionAnswerChat;
import hng.tech.apoe_4.views.TodayView;

public class ChatRepository {
    private ChatDao chatDao;

    public ChatRepository(Application application) {
        ApoeDatabase database = ApoeDatabase.getInstance();
        chatDao = database.chatDao();
    }

    private static class InsertChatAsyncClass extends AsyncTask<List<QuestionAnswerChat>, Void, Void>{

        private ChatDao chatDao;

        public InsertChatAsyncClass(ChatDao chatDao) {
            this.chatDao = chatDao;
        }

        @Override
        protected Void doInBackground(List<QuestionAnswerChat>... questionAnswerChats) {
            chatDao.insertMultipleElements(questionAnswerChats[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("DatabaseOp", "Chat Element inserted");
        }
    }

    private static class ObtainChatAsyncClass extends AsyncTask<String, Void, List<QuestionAnswerChat>> {

        private ChatDao chatDao;
        TodayView todayView;

        public ObtainChatAsyncClass(ChatDao chatDao, TodayView todayView) {
            this.chatDao = chatDao;
            this.todayView = todayView;
        }


        @Override
        protected List<QuestionAnswerChat> doInBackground(String... dates) {
            return chatDao.getChatForCurrentDay(dates[0]);
        }

        @Override
        protected void onPostExecute(List<QuestionAnswerChat> questionAnswerChats) {
            super.onPostExecute(questionAnswerChats);
            todayView.chatFetched(questionAnswerChats);
        }
    }


    public void insertChatElement(List<QuestionAnswerChat> questionAnswerChat){
        new InsertChatAsyncClass(chatDao).execute(questionAnswerChat);
    }

    public void getChat(String date, TodayView todayView){
        new ObtainChatAsyncClass(chatDao, todayView).execute(date);
    }
}
