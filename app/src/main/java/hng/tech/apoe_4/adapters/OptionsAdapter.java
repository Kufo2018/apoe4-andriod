package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.models.QuestionAnswerChat;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>{

    private Context context;
    private List<String> options;

    public OptionsAdapter(Context context, List<String> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.answers_list_item, parent, false);

        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        holder.bindTo(options.get(position));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class OptionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.answerText)
        Button answerText;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(String option){
            answerText.setText(option);
            answerText.setOnClickListener(v -> answerText.setSelected(true));
        }

    }
}
