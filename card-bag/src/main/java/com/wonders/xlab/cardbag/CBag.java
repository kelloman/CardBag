package com.wonders.xlab.cardbag;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by hua on 16/8/16.
 */
public class CBag {
    private static final String EXTRA_PREFIX = BuildConfig.APPLICATION_ID;
    private static final Object object = new Object();
    private static CBag cBag;
    private Bundle mCBagOptionsBundle;

    private CBag() {
        mCBagOptionsBundle = new Bundle();
    }

    /**
     * have to call the method before call start
     *
     * @param application
     */
    public static CBag init(Application application) {
        if (cBag == null) {
            synchronized (object) {
                if (cBag == null) {
                    cBag = new CBag();
                }
            }
        }

        return cBag;
    }

    /**
     * start @{@link CbMainActivity}
     *
     * @param activity
     */
    public CBag start(Activity activity) {
        activity.startActivity(new Intent(activity, CbMainActivity.class));
        return this;
    }

    /**
     * start @{@link CbMainActivity}
     *
     * @param fragment
     */
    public CBag start(Fragment fragment) {
        fragment.startActivity(new Intent(fragment.getContext(), CbMainActivity.class));
        return this;
    }

    public CBag withOption(Options option) {
        mCBagOptionsBundle.putAll(option.getOptionBundle());
        return this;
    }

    /**
     *
     */
    public static class Options{
        private static final String EXTRA_COLOR_PRIMARY = EXTRA_PREFIX + ".ColorPrimary";
        private static final String EXTRA_COLOR_PRIMARY_DARK = EXTRA_PREFIX + ".ColorPrimaryDark";

        private final Bundle mOptionBundle;

        public Options() {
            mOptionBundle = new Bundle();
        }

        @NonNull
        public Bundle getOptionBundle() {
            return mOptionBundle;
        }

        public void setColorPrimary(@ColorInt int colorPrimary) {
            mOptionBundle.putInt(EXTRA_COLOR_PRIMARY,colorPrimary);
        }

        public void setColorPrimaryDark(@ColorInt int colorPrimaryDark) {
            mOptionBundle.putInt(EXTRA_COLOR_PRIMARY_DARK,colorPrimaryDark);
        }
    }
}
