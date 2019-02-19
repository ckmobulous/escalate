/*
package com.fametale.utils;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.truup.R;
import com.truup.activities.LoginAct;
import com.truup.databinding.CustomToastBinding;
import com.truup.sharedpreferences.SPreferenceKey;
import com.truup.sharedpreferences.SharedPreferenceWriter;

*/
/**
 * Created by mobulous2 on 27/1/17.
 *//*

public class CustomToast
{

    Activity activity;
    public CustomToast(Activity activity)
    {
        this.activity=activity;
    }

    public void showToast(String text)
    {
        LayoutInflater inflater = LayoutInflater.from(activity);
        CustomToastBinding binding=DataBindingUtil.inflate(inflater, R.layout.custom_toast, (ViewGroup)activity.findViewById(R.id.toast_layout), false);
        View view=binding.getRoot();
//        LayoutInflater li = activity.getLayoutInflater();
        //Getting the View object as defined in the customtoast.xml file
//        View layout = li.inflate(R.layout.custom_toast,(ViewGroup)activity.findViewById(R.id.toast_layout));
        TextView textView=(TextView)view.findViewById(R.id.toast_text);
        textView.setText(text);
        //Creating the Toast object
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);//setting the view of custom toast layout
        toast.show();
        if(text.equals("Invalid User."))
        {
            Intent intent=new Intent(activity, LoginAct.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SPreferenceWriter.getInstance(activity).writeBooleanValue(SPreferenceKey.LOGINKEY, false);
            activity.startActivity(intent);
            activity.finish();
        }
    }
    public void showCustomToast(String text)
    {
        Toast toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        if(text.equals("Invalid User."))
        {
            Intent intent=new Intent(activity, LoginAct.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SPreferenceWriter.getInstance(activity).writeBooleanValue(SPreferenceKey.LOGINKEY, false);
            activity.startActivity(intent);
            activity.finish();
        }
    }
    public static void showCustomToast(Activity activity, String text)
    {
        LayoutInflater inflater = LayoutInflater.from(activity);
        CustomToastBinding binding=DataBindingUtil.inflate(inflater, R.layout.custom_toast, (ViewGroup)activity.findViewById(R.id.toast_layout), false);
        View view=binding.getRoot();
//        LayoutInflater li = activity.getLayoutInflater();
        //Getting the View object as defined in the customtoast.xml file
//        View layout = li.inflate(R.layout.custom_toast,(ViewGroup)activity.findViewById(R.id.toast_layout));
        TextView textView=(TextView)view.findViewById(R.id.toast_text);
        textView.setText(text);
        //Creating the Toast object
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);//setting the view of custom toast layout
        toast.show();
        if(text.equals("Invalid User."))
        {
            Intent intent=new Intent(activity, LoginAct.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SPreferenceWriter.getInstance(activity).writeBooleanValue(SPreferenceKey.LOGINKEY, false);
            activity.startActivity(intent);
            activity.finish();
        }
    }
    public static void showToast(Activity activity, String text)
    {
        Toast toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        if(text.equals("Invalid User."))
        {
            Intent intent=new Intent(activity, LoginAct.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SPreferenceWriter.getInstance(activity).writeBooleanValue(SPreferenceKey.LOGINKEY, false);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
*/
