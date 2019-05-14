package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.models.QuestionAnswerChat;

public class QuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<QuestionAnswerChat> questionAnswerChatList;

    private int lastPosition = -1;

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
                setAnimation(questionViewHolder.itemView, position);
                break;
            }

            case (QuestionAnswerChat.ANSWER_TYPE):{
                AnswerViewHolder answerViewHolder = (AnswerViewHolder) holder;
                answerViewHolder.bindTo(questionAnswerChatList.get(position));
                setAnimation(answerViewHolder.itemView, position);
                break;
            }

            default:{
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.bindTo();
                setAnimation(loadingViewHolder.itemView, position);
                break;
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

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
                    YoYo.with(Techniques.FadeIn)
                .duration(300)
//                .repeat(5)
                .playOn(viewToAnimate);

//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
