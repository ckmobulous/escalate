package com.escalate.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


@SuppressWarnings("PointlessBooleanExpression")
public class Util {


    public static String formatSeconds(int seconds) {
        return /*getTwoDecimalsValue(seconds / 3600) + ":"
                +*/ getTwoDecimalsValue(seconds / 60) + ":"
                + getTwoDecimalsValue(seconds % 60);
    }

    private static String getTwoDecimalsValue(int value) {
        if (value >= 0 && value <= 9) {
            return "0" + value;
        } else {
            return value + "";
        }
    }

    public static String getProperText(TextView textView){
        return textView.getText().toString().trim();
    }

    public static void showValidationAlert(Context context, ArrayList<String> messages) {
        // TODO Auto-generated method stub

        int i = 1;
        String msg = "";
        for (String message : messages) {

            if (i == 1) {
                msg = msg + i + ". " + message;
            } else {
                msg = msg + "<br>" + i + ". " + message;
            }
            i++;
        }

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setTitle("Validation Error !");
        ab.setIcon(android.R.drawable.ic_dialog_alert);
        ab.setMessage(Html.fromHtml("<font color='#E6005C'>" + msg + "</font>"));

        ab.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();

            }
        });
        // ab.create();
        ab.show();
    }

    public static boolean showInternetAlert(final Context context) {

        // final Context context = Containts.APP_CONTEXT;
        boolean isConnected = isOnline(context);
        if (!isConnected) {
            AlertDialog.Builder ab = new AlertDialog.Builder(context);
            ab.setCancelable(false);
            ab.setMessage("It seems to Internet disconnected. Please Turn ON the Network Connection.");
            ab.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                           /* Intent I = new Intent(
                                    Settings.ACTION_WIFI_SETTINGS);
                            context.startActivity(I);*/
                            dialog.dismiss();
                        }
                    });

           /* ab.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {

                            dialog.dismiss();
                        }
                    });*/

            ab.create();
            // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();
        }
        return isConnected;
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() == true);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static String getCurrentDateString() {
        SimpleDateFormat postFormater = new SimpleDateFormat(
                "MMMM dd, yyyy hh:mm a");

        String newDateStr = postFormater.format(Calendar.getInstance()
                .getTime());
        return newDateStr;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static String getDateString(long dateTime) {
        SimpleDateFormat postFormater = new SimpleDateFormat(
                "dd-MMM-yyyy");

        String newDateStr = postFormater.format(dateTime);
        return newDateStr;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    public static boolean isValidPhone(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.PHONE.matcher(target).matches();
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo= Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public static String getCreatedDate(String str) {
        String[] exactDate ;
        String[] date  = str.split(" ");

        exactDate = date[0].split("-");

        return exactDate[2];
    }

    public static String getOtp(String msg){

        String[] otp = msg.split(" ");

        return otp[0];
    }

    public static String getDayName(int day) {
        String dayName = "";
        switch (day) {
            case 1:
                dayName = "Sunday";
                break;
            case 2:
                dayName = "Monday";
                break;
            case 3:
                dayName = "Tuesday";
                break;
            case 4:
                dayName = "Wednesday";
                break;
            case 5:
                dayName = "Thursday";
                break;
            case 6:
                dayName = "Friday";
                break;
            case 7:
                dayName = "Saturday";
                break;
        }
        return dayName;
    }

    public static String getMonthDaysByString(String month) {
        String dayName = "";
        // int integerHour = Integer.parseInt(hour);

        switch (month) {

            case "Jan":
                dayName = "31";
                break;
            case "Feb":
                dayName = "28";
                break;
            case "Mar":
                dayName = "31";
                break;
            case "Apr":
                dayName = "30";
                break;
            case "May":
                dayName = "31";
                break;
            case "Jun":
                dayName = "30";
                break;
            case "Jul":
                dayName = "31";
                break;
            case "Aug":
                dayName = "31";
                break;
            case "Sep":
                dayName = "30";
                break;
            case "Oct":
                dayName = "31";
                break;
            case "Nov":
                dayName = "30";
                break;
            case "Dec":
                dayName = "31";
                break;

        }
        Log.e("....", "getMonthDigit: "+dayName );
        return  dayName;
    }

    public static String getMonthByString(String month) {
        String dayName = "";
        // int integerHour = Integer.parseInt(hour);

        switch (month) {

            case "Jan":
                dayName = "01";
                break;
            case "Feb":
                dayName = "02";
                break;
            case "Mar":
                dayName = "03";
                break;
            case "Apr":
                dayName = "04";
                break;
            case "May":
                dayName = "05";
                break;
            case "Jun":
                dayName = "06";
                break;
            case "Jul":
                dayName = "07";
                break;
            case "Aug":
                dayName = "08";
                break;
            case "Sep":
                dayName = "09";
                break;
            case "Oct":
                dayName = "10";
                break;
            case "Nov":
                dayName = "11";
                break;
            case "Dec":
                dayName = "12";
                break;

        }
        Log.e("....", "getMonthDigit: "+dayName );
        return  dayName;
    }

    public static String getMonthDigit(int month) {
        String dayName = "";
       // int integerHour = Integer.parseInt(hour);

        switch (month) {

            case 1:
                dayName = "01";
                break;
            case 2:
                dayName = "02";
                break;
            case 3:
                dayName = "03";
                break;
            case 4:
                dayName = "04";
                break;
            case 5:
                dayName = "05";
                break;
            case 6:
                dayName = "06";
                break;
            case 7:
                dayName = "07";
                break;
            case 8:
                dayName = "08";
                break;
            case 9:
                dayName = "09";
                break;
            case 10:
                dayName = "10";
                break;
            case 11:
                dayName = "11";
                break;
            case 12:
                dayName = "12";
                break;

        }
        Log.e("....", "getMonthDigit: "+dayName );
        return  dayName;
    }

    public static String getMonthDaysByDigit(int month) {
        String dayName = "";
        // int integerHour = Integer.parseInt(hour);

        switch (month) {

            case 1:
                dayName = "31";
                break;
            case 2:
                dayName = "28";
                break;
            case 3:
                dayName = "31";
                break;
            case 4:
                dayName = "30";
                break;
            case 5:
                dayName = "31";
                break;
            case 6:
                dayName = "30";
                break;
            case 7:
                dayName = "31";
                break;
            case 8:
                dayName = "31";
                break;
            case 9:
                dayName = "30";
                break;
            case 10:
                dayName = "31";
                break;
            case 11:
                dayName = "30";
                break;
            case 12:
                dayName = "31";
                break;

        }
        Log.e("....", "getMonthDigit: "+dayName );
        return  dayName;
    }



    public static String getMonthNameByDigit(int month) {
        String dayName = "";
        // int integerHour = Integer.parseInt(hour);

        switch (month) {

            case 1:
                dayName = "Jan";
                break;
            case 2:
                dayName = "Feb";
                break;
            case 3:
                dayName = "Mar";
                break;
            case 4:
                dayName = "Aug";
                break;
            case 5:
                dayName = "May";
                break;
            case 6:
                dayName = "June";
                break;
            case 7:
                dayName = "July";
                break;
            case 8:
                dayName = "Aug";
                break;
            case 9:
                dayName = "Sep";
                break;
            case 10:
                dayName = "Oct";
                break;
            case 11:
                dayName = "Nov";
                break;
            case 12:
                dayName = "Dec";
                break;

        }
        Log.e("....", "getMonthDigit: "+dayName );
        return  dayName;
    }

    public static String getTimeHour(String hour) {
        String dayName = "";
        int integerHour = Integer.parseInt(hour);

        switch (integerHour) {
            case 0:
                dayName = "00";
                break;
            case 1:
                dayName = "01";
                break;
            case 2:
                dayName = "02";
                break;
            case 3:
                dayName = "03";
                break;
            case 4:
                dayName = "04";
                break;
            case 5:
                dayName = "05";
                break;
            case 6:
                dayName = "06";
                break;
            case 7:
                dayName = "07";
                break;
            case 8:
                dayName = "08";
                break;
            case 9:
                dayName = "09";
                break;
            case 10:
                dayName = "10";
                break;
            case 11:
                dayName = "11";
                break;
            case 12:
                dayName = "12";
                break;
            case 13:
                dayName = "01";
                break;
            case 14:
                dayName = "02";
                break;
            case 15:
                dayName = "03";
                break;
            case 16:
                dayName = "04";
                break;
            case 17:
                dayName = "05";
                break;
            case 18:
                dayName = "06";
                break;
            case 19:
                dayName = "07";
                break;
            case 20:
                dayName = "08";
                break;
            case 21:
                dayName = "09";
                break;
            case 22:
                dayName = "10";
                break;
            case 23:
                dayName = "11";
                break;
            }
        return dayName;
    }
}
