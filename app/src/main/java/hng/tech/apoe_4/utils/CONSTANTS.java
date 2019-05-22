package hng.tech.apoe_4.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class CONSTANTS {

    public CONSTANTS() {
    }

    public static String getTimeOfDay() {
        int currentDateString = Calendar.getInstance().getTime().getHours();
        if (currentDateString >= 12 && currentDateString < 18){
            Log.e("Date", "Noon");
            return "Noon";
        }
        else if (currentDateString >= 6 && currentDateString < 12){
            Log.e("Date", "Day");
            return "Morning";
        }
        else if (currentDateString >= 18 && currentDateString <= 23){
            Log.e("Date", "Night");
            return "Night";
        } else {
            Log.e("Date", "MidNight");
            return "Night";
        }
    }

    public static void toastError(Context context, String msg){
        Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void toastSuccess(Context context, String msg){
        Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show();
    }

    public static String getCurrentDate(){
        SimpleDateFormat dt = new SimpleDateFormat("dd MMM, yyyy");

//        String date = serverTime.split(" ")[0];
//        String[] datePieces = date.split("-");
        Calendar calendar = Calendar.getInstance();

//        Log.e("DatePieces", Arrays.toString(datePieces));
//        calendar.set(Integer.parseInt(datePieces[0]), Integer.parseInt(datePieces[1]) - 1, Integer.parseInt(datePieces[2]));
        return  dt.format(calendar.getTime());
    }

}
