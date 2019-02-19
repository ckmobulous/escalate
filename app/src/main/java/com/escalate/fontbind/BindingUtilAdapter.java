package com.escalate.fontbind;

import android.databinding.BindingAdapter;
import android.widget.TextView;

/**
 * Created by Rajeev Kr. Sharma [rajeevrocker7@gmail.com] on 27/6/18.
 */

public class BindingUtilAdapter
{
    @BindingAdapter({"font"})
    public static void setFont(TextView textView, String fontName)
    {
        textView.setTypeface(FontCache.getInstance().get(fontName));
    }

    // @BindingAdapter({"imageUrl"})
  /* @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(RoundedImageView imageView, Object url) {
        if (!url.equals("")) {
            AQuery aQuery = new AQuery(imageView);
            aQuery.id(imageView).image((String)url, true, true, 0, R.drawable.profile_one);

        }
    }*/

//    @BindingAdapter("android:src")
//    public static void setImageDrawable(ImageView view, Drawable drawable) {
//        view.setImageDrawable(drawable);
//    }
//
//
//    //@BindingAdapter({"Background"})
//    @BindingAdapter("bind:Background")
//    public static void setImageResource(ImageView view, int resource) {
//        view.setImageResource(resource);
//    }

}
