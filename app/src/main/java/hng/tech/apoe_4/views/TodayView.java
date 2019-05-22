package hng.tech.apoe_4.views;

import java.util.List;

import hng.tech.apoe_4.models.Question;
import hng.tech.apoe_4.models.QuestionAnswerChat;

public interface TodayView {
    void beginQuestionFetch();
    void onFetchQuestion(Question question);
    void noMoreQuestions(String msg);
    void onAnswerSelected(int position, String answer);
    void questionFetchFailed();
    void chatFetched(List<QuestionAnswerChat> questionAnswerChats);
    void toastSuccess(String msg);
    void toastError(String msg);
}
