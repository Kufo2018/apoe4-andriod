package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
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
        View view;
        switch (viewType){
            case (QuestionAnswerChat.QUESTION_TYPE):{
                view = LayoutInflater.from(context).inflate(R.layout.question_list_item, parent, false);
                return new QuestionViewHolder(view);
            }

            case (QuestionAnswerChat.ANSWER_TYPE):{
                view = LayoutInflater.from(context).inflate(R.layout.answer_list_item, parent, false);
                return new AnswerViewHolder(view);
            }

            default:{
                view = LayoutInflater.from(context).inflate(R.layout.loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case (QuestionAnswerChat.QUESTION_TYPE):{
                QuestionViewHolder questionViewHolder = (QuestionViewHolder) holder;
                questionViewHolder.bindTo(questionAnswerChatList.get(position));
            }

            case (QuestionAnswerChat.ANSWER_TYPE):{
                AnswerViewHolder answerViewHolder = (AnswerViewHolder) holder;
                answerViewHolder.bindTo(questionAnswerChatList.get(position));
            }

            default:{
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.bindTo();
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionAnswerChatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return questionAnswerChatList.get(position).getType();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.questionText)
        TextView questionText;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(QuestionAnswerChat questionAnswerChat){
            questionText.setText(questionAnswerChat.getText());
        }
    }

    class AnswerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.answerText)
        TextView answerText;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(QuestionAnswerChat questionAnswerChat){
            answerText.setText(questionAnswerChat.getText());
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.animation_view)
        LottieAnimationView animationView;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(){
            animationView.setAnimation("animation_loading.json");
            animationView.setRepeatCount(LottieDrawable.INFINITE);
            animationView.playAnimation();
        }
    }
}
