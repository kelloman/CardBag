package com.wonders.xlab.cardbag.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.view.TopBar;

public class HomeActivity extends Activity {
    private static final int REQUEST_CODE_SCAN = 1234;
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);
        mTopBar = (TopBar) findViewById(R.id.topbar);
        mTopBar.setOnRightMenuClickListener(new TopBar.OnRightMenuClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "right click", Toast.LENGTH_SHORT).show();
            }
        });
        mTopBar.setMenuSize(20);
        mTopBar.setTitleGravity(TopBar.GRAVITY_TITLE_LEFT);
        mTopBar.setTitleSize(10);
        mTopBar.setTitleColor(getResources().getColor(R.color.colorAccent));
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            String result = data.getStringExtra(XQrScanner.EXTRA_RESULT_BAR_OR_CODE_STRING);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            BarCodeEncoder ecc = new BarCodeEncoder(mImageView.getWidth(), mImageView.getHeight());
            try {
                Bitmap bitm = ecc.barcode(result);
                mImageView.setImageBitmap(bitm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    public void manageCard(View view) {
    }

    public void useCard(View view) {
    }
}