package com.escalate.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.ProgressBar;


import com.escalate.R;

import java.io.File;
import java.util.List;


public class ServiceParameters {

    /********* How to make the perfect Singleton?: ***********/
//    Link:  https://medium.com/exploring-code/how-to-make-the-perfect-singleton-de6b951dfdb0

    private static volatile ServiceParameters serviceParameters = null;
    private static final String TAG = ServiceParameters.class.getSimpleName();

    //    PRIVATE CONSTRUCTOR
    private ServiceParameters() {
    }

    //   METHOD TO INSTANTIATE ServiceParameters
    public static synchronized ServiceParameters getInstance() {

        if (serviceParameters == null) {     //Check for the first time

            synchronized (ServiceParameters.class) {    //Check for the second time.
                if (serviceParameters == null)  // if there is no instance available... create new one
                    serviceParameters = new ServiceParameters();
            }
        }

        return serviceParameters;

    }

    //    METHOD TO PRINT 'String' PARAMETERS REQUIRED IN API / SERVICE
    public static StringBuilder setUpParameters(String... paramArray) {
        StringBuilder builder = new StringBuilder();
        builder.append("THE SERVICE PARAMETERS ARE : \n");
        if (paramArray != null && paramArray.length != 0) {
            for (String data : paramArray) {
                builder.append("\t");
                builder.append(data);
                builder.append("\n");
            }
        } else {
            Log.e(TAG, "setUpParameters(): " + "Length of String Array parameters is ZERO");
        }


        Log.w(TAG, builder.toString());
        return builder;
    }

    //    METHOD TO PRINT ' List Of String and Files ' PARAMETERS REQUIRED IN API / SERVICE
    public static StringBuilder setUpParameters(List<String> stringParamsList, File... files) {
        StringBuilder builder = new StringBuilder();

        builder.append("THE SERVICE PARAMETERS ARE : \n");
        if (stringParamsList != null && stringParamsList.size() != 0) {
            for (String data : stringParamsList) {
                builder.append("\t");
                builder.append(data);
                builder.append("\n");
            }
        } else {
            Log.e(TAG, "setUpParameters(): " + "Size of List of String parameters is ZERO");
        }

        if (files != null && files.length != 0) {
            for (File fileData : files) {
                builder.append("\t");
                builder.append(fileData.getAbsolutePath());
                builder.append("\n");
            }
        } else {
            Log.e(TAG, "setUpParameters(): " + "Length of Files Array parameters is ZERO");
        }


        Log.w(TAG, builder.toString());
        return builder;
    }


    //    METHOD: TO SET UP CUSTOM PROGRESS BAR CIRCLE TINT COLOR, WHEN COVER PIC LOADS
    public void setUpProgressbarCover(Context context, ProgressBar progressbar) {
        if (context != null) {
            // fixes pre-Lollipop progressBar indeterminateDrawable tinting
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                Drawable wrapDrawable = DrawableCompat.wrap(progressbar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, R.color.white));
                progressbar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else {
                progressbar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            }
        }
    }
}