package hng.tech.apoe_4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.adapters.NotifAdapter;
//import hng.tech.apoe_4.fragments.EmptyFragment;
import hng.tech.apoe_4.fragments.NotifFragment;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.Count;
import hng.tech.apoe_4.retrofit.responses.Notif;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationActivity extends AppCompatActivity {
    List<Notif> mNotif;
    Notif notif;
    public static int counttt;
    NotifAdapter adapter;
    ApiInterface apiInterface;
    ImageView back2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        back2 = findViewById(R.id.back2);

        back2.setOnClickListener(view -> {
            onBackPressed();
        });
//



        //openFragment(new EmptyFragment(), "empty");
        openFragment(new NotifFragment(), "notification");

//        if (counttt > 0){
//            openFragment(new NotifFragment(), "notification");
//        }else{
//            openFragment(new EmptyFragment(), "empty");
//        }
        // Lookup the recyclerview in activity layout

    }

    private void openFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.notifCont, fragment, tag);
        transaction.addToBackStack(fragment.getTag());
        Log.d("TAG","fragment tag: "+fragment.getTag());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
