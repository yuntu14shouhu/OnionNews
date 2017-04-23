package com.onionnews.demo.onionnews.ColumnView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by asus on 2016/11/4.
 */

public class CustomFontsTextView extends TextView{
    public CustomFontsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFonts(context);
    }
    /**
     * 初始化字体
     * @param context
     */
    private void initFonts(Context context) {
        //设置字体样式
        setTypeface(FontCustom.setFont(context));
    }

    private static class FontCustom {
        static String fongUrl = "fzxingkai.ttf";
        static Typeface tf;

        /**
         * 设置字体
         */
        public static Typeface setFont(Context context)
        {
            if (tf == null)
            {
                tf = Typeface.createFromAsset(context.getAssets(),fongUrl);
            }
            return tf;
        }
    }
}
