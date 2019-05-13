package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hng.tech.apoe_4.models.QuestionAnswerChat;

public class QuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<QuestionAnswerChat> questionAnswerChatList;

    public QuestionsAdapter(Context context, List<QuestionAnswerChat> questionAnswerChatList) {
        this.context = context;
        this.questionAnswerChatList = questionAnswerChatList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
