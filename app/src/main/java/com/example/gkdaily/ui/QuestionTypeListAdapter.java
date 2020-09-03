package com.example.gkdaily.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gkdaily.R;
import com.example.gkdaily.roomDB.TypeDBEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class QuestionTypeListAdapter extends RecyclerView.Adapter<QuestionTypeListAdapter.QuestionTypeListAdapterViewHolder> {

    List<TypeDBEntity> questionTypes_adp;
    Context ctx;
    public static final String SHARED_PREF_WIDGET_QUESTION_LIST = "widget_question_pref";
    public static final String QUESTION_LIST_SP = "question_type_list";

    ArrayList<String> questionType_list = new ArrayList<>();
    String questionType;

    OnclickPassType onclickPassType;

    public interface OnclickPassType{
        public void questionTypeClicked(int position, String typeName);
    }

    public QuestionTypeListAdapter(Context context, List<TypeDBEntity> questionTypes_adp) {
        this.questionTypes_adp = questionTypes_adp;
        ctx = context;
        onclickPassType = (OnclickPassType) ctx;

        for (int i=0; i < questionTypes_adp.size(); i++){
            questionType = questionTypes_adp.get(i).getTopic();
            questionType_list.add(questionType);
        }
    }

    @NonNull
    @Override
    public QuestionTypeListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View myCardView = layoutInflater.inflate(R.layout.cardview_topic_list, parent, false);
        return new QuestionTypeListAdapterViewHolder(myCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionTypeListAdapterViewHolder holder, final int position) {

        /*SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_WIDGET_QUESTION_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_widget = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(questionType_list);
        editor_widget.putString(QUESTION_LIST_SP, json);
        editor_widget.apply();*/

        holder.topicName.setText(questionType_list.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type_clicked = questionType_list.get(position);
              //  Toast.makeText(v.getContext(), "Pressed" + type_clicked, Toast.LENGTH_LONG ).show();
                onclickPassType.questionTypeClicked(position, type_clicked);
            }
        });


    }


    @Override
    public int getItemCount() {
        return questionTypes_adp==null?0:questionTypes_adp.size();
    }

    public class QuestionTypeListAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView topicName;
        CardView cardView;

        public QuestionTypeListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName_text);
           cardView = itemView.findViewById(R.id.cardViewLayout);

        }
    }

}
