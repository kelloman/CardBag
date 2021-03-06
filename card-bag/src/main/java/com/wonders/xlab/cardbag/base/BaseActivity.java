package com.wonders.xlab.cardbag.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wonders.xlab.cardbag.CBagEventConstant;
import com.wonders.xlab.cardbag.R;

import static android.R.attr.type;

/**
 * Created by hua on 16/8/19.
 */
public class BaseActivity extends AppCompatActivity implements BaseContract.View {
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getResources().getColor(R.color.cbTopBarBackground) == getResources().getColor(android.R.color.white) && Build.VERSION.SDK_INT >= 23) {
            setTheme(R.style.CBAppTheme_NoActionBar_LightStatusBar);
        } else {
            setTheme(R.style.CBAppTheme_NoActionBar);
        }
        super.onCreate(savedInstanceState);
    }

    /**
     * change the menu vector icon's color to fit the toolbar background
     * @param menuItem
     */
    protected void setupMenuIcon(MenuItem menuItem) {
        Drawable menuItemDrawable = menuItem.getIcon();
        if (menuItemDrawable != null) {
            menuItemDrawable.mutate();
            menuItemDrawable.setColorFilter(getResources().getColor(R.color.cbTopBarTitleColor), PorterDuff.Mode.SRC_ATOP);
            menuItem.setIcon(menuItemDrawable);
        }
    }

    /**
     * change the menu text color to fit the toolbar background
     * @param menuItem
     */
    protected void setupMenuText(MenuItem menuItem) {
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.cbTopBarTitleColor)), 0, s.length(), 0);
        menuItem.setTitle(s);
    }

    protected void setupActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    protected void showShortToast(String message) {
        showToast(message, true);
    }

    private void showToast(String message, boolean isShort) {
        if (mToast == null) {
            mToast = Toast.makeText(this, message, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(message);
        }
        mToast.show();
    }

    protected void showLongToast(String message) {
        showToast(message, false);
    }

    protected void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        if (title != null) {
            mProgressDialog.setTitle(title);
        }
        if (message != null) {
            mProgressDialog.setMessage(message);
        }
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showAlertDialog(String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveBtnListener, String negativeBtnText, DialogInterface.OnClickListener negativeBtnListener) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(this);
        }
        if (!TextUtils.isEmpty(title)) {
            mBuilder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            mBuilder.setMessage(message);
        }
        if (!TextUtils.isEmpty(positiveBtnText) && null != positiveBtnListener) {
            mBuilder.setPositiveButton(positiveBtnText, positiveBtnListener);
        }
        if (!TextUtils.isEmpty(negativeBtnText) && null != negativeBtnListener) {
            mBuilder.setNegativeButton(negativeBtnText, negativeBtnListener);
        }

        if (mAlertDialog == null) {
            mAlertDialog = mBuilder.create();
        }
        mAlertDialog.show();
    }

    protected void dismissAlertDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    protected void hideKeyboardForce(IBinder token) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(token, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        dismissAlertDialog();
        mBuilder = null;
        mAlertDialog = null;
        mProgressDialog = null;
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    @Override
    public void showToastMessage(String message) {
        showShortToast(message);
    }

    protected void sendBroadcast(String event, String name) {
        Intent intent = new Intent(getPackageName() + CBagEventConstant.EVENT_BROADCAST_SUFFIX);
        intent.putExtra("event", event);
        intent.putExtra("name", name);
        intent.putExtra("timeInMill", System.currentTimeMillis());
        sendBroadcast(intent);
    }
}
