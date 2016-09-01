package com.wonders.xlab.cardbag.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseActivity;
import com.wonders.xlab.cardbag.manager.RealmManager;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.ui.cardshow.CardShowActivity;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);

        RealmManager.init(getApplicationContext());
    }

    public void manageCard(View view) {
        startActivity(new Intent(this, CardMyActivity.class));
    }

    public void useCard(View view) {
        startActivity(new Intent(this, CardShowActivity.class));
    }
}
