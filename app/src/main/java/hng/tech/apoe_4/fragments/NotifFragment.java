package hng.tech.apoe_4.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.adapters.NotifAdapter;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.Count;
import hng.tech.apoe_4.retrofit.responses.Notif;
//import hng.tech.apoe_4.utils.MainApplication2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotifFragment extends Fragment{
    NotifAdapter adapter;
    List<Notif> mNotif;
    ApiInterface apiInterface;
    LinearLayout progress;
    RecyclerView rvContacts;
    TextView error;
    public static  int countt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.notif, container, false);
        rvContacts = v.findViewById(R.id.recyc);
        progress = v.findViewById(R.id.progress);
        error = v.findViewById(R.id.errr);


        // Create adapter passing in the sample user data

        mNotif = new ArrayList<>();
        // Attach the adapter to the recyclerview to populate items

        RecyclerView.LayoutManager man = new LinearLayoutManager(getActivity());
        rvContacts.setLayoutManager(man);

        adapter = new NotifAdapter(getActivity(), mNotif);
        rvContacts.setAdapter(adapter);



        // That's all!
        //counttt = adapter.getItemCount();

        getNotification();

        return v;
    }
    private void getNotification(){
        getRetrofit("https://chimdike.000webhostapp.com/");

        Call<List<Notif>> call = apiInterface.notifications();
        call.enqueue(new Callback<List<Notif>>() {
            @Override
            public void onResponse(Call<List<Notif>> call, Response<List<Notif>> response) {
                mNotif = response.body();
                //Log.d("result", "onResponse: " + mNotif.get(1).getNotification());
                adapter.setBlogList(mNotif);
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Notif>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNotificationCount(){
        getRetrofit("https://chimdike.000webhostapp.com/");

        Call<Count> call = apiInterface.count();
        call.enqueue(new Callback<Count>() {
            @Override
            public void onResponse(Call<Count> call, Response<Count> response) {
                if (response.body().getCount().equals("0")){
                    progress.setVisibility(View.INVISIBLE);
                    error.setVisibility(View.VISIBLE);
                    rvContacts.setVisibility(View.INVISIBLE);

                }else{
                    getNotification();
                }
            }

            @Override
            public void onFailure(Call<Count> call, Throwable t) {

            }
        });
    }

    private void getRetrofit(String url1){
        //Build Retrofit and connect to interface
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url1)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

}
