package hng.tech.apoe_4.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.adapters.OptionsAdapter;
import hng.tech.apoe_4.adapters.QuestionsAdapter;
import hng.tech.apoe_4.models.Question;
import hng.tech.apoe_4.models.QuestionAnswerChat;
import hng.tech.apoe_4.presenters.TodayPresenter;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.WeatherResponse;
import hng.tech.apoe_4.utils.CONSTANTS;
import hng.tech.apoe_4.utils.PermisionManager;
import hng.tech.apoe_4.utils.ProgressAnim;
import hng.tech.apoe_4.views.TodayView;
import im.delight.android.location.SimpleLocation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static hng.tech.apoe_4.activities.Home.lat;
import static hng.tech.apoe_4.activities.Home.lng;


public class TodayFragment extends Fragment implements TodayView {

    @BindView(R.id.exerciseProgress)
    ProgressBar tempProgress;

    @BindView(R.id.progress_APO)
    ProgressBar stepsProgress;

    @BindView(R.id.sleepProgress)
    ProgressBar sleepProgress;

//    @BindView(R.id.questions_view)
//    LinearLayout questionsLayout;

//    @BindView(R.id.loadingQuestions)
private ProgressBar loadingQuestions;
    private TextView noMoreQuestions;
//    @BindView(R.id.loading)
//    ProgressBar progressBar;

    @BindView(R.id.temp)
    TextView tempText;

    @BindView(R.id.answersRecyclerView)
    RecyclerView answersRecyclerView;

    @BindView(R.id.questionsRecyclerView)
    RecyclerView questionsRecyclerView;

    private String questionId;

    private LayoutInflater genInflater;

    private TodayPresenter todayPresenter;

    private List<QuestionAnswerChat> questionAnswerChatList;
    private  List<String> options;
    private QuestionsAdapter questionsAdapter;
    private OptionsAdapter optionsAdapter;


    private float from = (float)10;
    private float to;
    private double progress;

    private char degree = '\u00B0';

    private SimpleLocation location;
    private int LOCATION_REQUEST_CODE = 1;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();

        }

    }

    private void requestStoragePermission() {
        PermisionManager.requestPermision(getContext(), LOCATION_REQUEST_CODE,getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
                location.beginUpdates();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private View.OnClickListener buttonTap = v -> {
//        YoYo.with(Techniques.SlideOutRight)
//                .duration(700)
//                .repeat(5)
//                .playOn(questionsLayout);

//        Button selected = (Button) v;
//       sendAnswer(selected.getText().toString());
    };

    private void sendAnswer(String answer){

        todayPresenter.sendAnswer(questionId, answer);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this, view);

        genInflater = inflater;

        questionAnswerChatList = new ArrayList<>();
        options = new ArrayList<>();
        questionAnswerChatList.add(new QuestionAnswerChat(QuestionAnswerChat.LOADING_TYPE));

        questionsAdapter = new QuestionsAdapter(getContext(), questionAnswerChatList);
        optionsAdapter = new OptionsAdapter(getContext(), options);
        questionsRecyclerView.setHasFixedSize(true);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        questionsRecyclerView.setAdapter(questionsAdapter);

        answersRecyclerView.setHasFixedSize(true);
        answersRecyclerView.setAdapter(optionsAdapter);
//        submit_button = view.findViewById(R.id.submit_button);

        Toasty.info(getContext(), CONSTANTS.getTimeOfDay()).show();

        todayPresenter = new TodayPresenter(getContext(), this);


//        questionsLayout.removeAllViews();
//        View questionView = inflater.inflate(R.layout.no_more_questions, questionsLayout);
//        loadingQuestions = questionView.findViewById(R.id.loadingQuestions);
//        noMoreQuestions = questionView.findViewById(R.id.no_more_questions_tv);

        todayPresenter.fetchQuestion();
//        questionsLayout.addView(questionView);




// construct a new instance of SimpleLocation
        location = new SimpleLocation(getActivity());

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            Toast.makeText(getActivity(), "Enable Location Permission", Toast.LENGTH_LONG).show();
            SimpleLocation.openSettings(getActivity());
        }else {
            lat  =location.getLatitude();
            lng= location.getLongitude();
            Log.d("TAG", "location-> " + lat + " " + lng );
        }



        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        Context context = inflater.getContext();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("https://fcc-weather-api.glitch.me/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        ApiInterface apiInterface = mRetrofit.create(ApiInterface.class);

        if (lng == 0.0 && lat == 0.0) {
            lng = location.getLongitude();
            lat = location.getLatitude();

        }else {
            apiInterface.getWeather(lng, lat).enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful()) {
                        double temp = lat == 0 ? 27.6 : response.body().getMain().getTemp();
                        double tempMax = lat == 0 ? 36.5 : response.body().getMain().getTempMax();
                        Log.d("TAG", "temp: " + temp);
                        Log.d("TAG", "tempMax: " + tempMax);

                        progress = (temp / tempMax) * 100;
                        Log.d("TAG", "progress: " + progress);

//                    tempProgress.setProgress((int) progress);
                        setAnimation();

                        tempText.setText(String.valueOf((int) temp) + degree +"C");
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {

                }
            });
        }



        getSleepTime();
        getStepNumber();


        return view;
    }

    private void showNextQuestion(@NonNull LayoutInflater inflater, Question question) {
        questionId = question.getId();
//        questionsLayout.removeAllViews();

    }



    //this method helps with animating progress bar

    private void setAnimation() {
        to = (float)progress;
        ProgressAnim anim = new ProgressAnim(tempProgress, from, to);
        anim.setDuration(2000);
        tempProgress.startAnimation(anim);
    }

    //this method the amount of sleep later
    private  void getSleepTime () {
        to = (float)80;
        ProgressAnim anim = new ProgressAnim(sleepProgress, from, to);
        anim.setDuration(2000);
        sleepProgress.startAnimation(anim);
    }

    //this method the amount of steps later
    private  void getStepNumber () {
        to = (float)90;
        ProgressAnim anim = new ProgressAnim(stepsProgress, from, to);
        anim.setDuration(2000);
        stepsProgress.startAnimation(anim);
    }
//this prepares the recycler view
// this methods fetch the data from the json asset file and displays the data

    public static TodayFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beginQuestionFetch() {
//        loadingQuestions.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchQuestion(Question question) {
//        loadingQuestions.setVisibility(View.GONE);
        if (questionAnswerChatList.get(questionAnswerChatList.size() - 1).getType() == QuestionAnswerChat.LOADING_TYPE){
            questionAnswerChatList.remove(questionAnswerChatList.size() - 1);
            questionsAdapter.notifyDataSetChanged();
        }

        QuestionAnswerChat questionAnswerChat = new QuestionAnswerChat();
        questionAnswerChat.setText(question.getText());
        questionAnswerChat.setType(QuestionAnswerChat.QUESTION_TYPE);

        questionAnswerChatList.add(questionAnswerChat);
        questionsAdapter.notifyDataSetChanged();

        options.addAll(question.getOptions());
        optionsAdapter.notifyDataSetChanged();


    }

    @Override
    public void noMoreQuestions(String msg) {


    }

    @Override
    public void questionFetchFailed() {
        loadingQuestions.setVisibility(View.GONE);
    }

    @Override
    public void toastSuccess(String msg) {

    }

    @Override
    public void toastError(String msg) {

    }
}