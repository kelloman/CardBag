package com.wonders.xlab.cardbag.ui.cardmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.data.MyCardModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;
import com.wonders.xlab.cardbag.view.SideBar;
import com.wonders.xlab.cardbag.view.TopBar;

import java.util.List;

public class CardMyActivity extends MVPActivity implements CardMyContract.View {

    private TopBar mTopBar;
    private ImageView mIvAdd;
    private RecyclerView mRecyclerView;
    private SideBar mSideBar;
    private CardMyIconRVAdapter mIconRVAdapter;
    private CardMyListRVAdapter mListRVAdapter;

    private GridLayoutManager mGridLayoutManager;

    private CardMyContract.Presenter mPresenter;


    private boolean mIsIconMode = true;

    @Override
    protected BaseContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_my_card_activity);
        mTopBar = (TopBar) findViewById(R.id.top_bar);
        mSideBar = (SideBar) findViewById(R.id.side_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mTopBar.setOnRightMenuClickListener(new TopBar.OnRightMenuClickListener() {
            @Override
            public void onClick(View view) {
                mSideBar.setVisibility(mIsIconMode ? View.VISIBLE : View.INVISIBLE);
                ((ImageView) view).setImageResource(mIsIconMode ? R.drawable.ic_menu_icon : R.drawable.ic_menu_list);
                mIsIconMode = !mIsIconMode;
                if (mPresenter != null) {
                    mPresenter.getMyCards();
                }
            }
        });
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardMyActivity.this, CardSearchActivity.class));
            }
        });
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                showShortToast(s);
            }
        });
        mGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mPresenter = new CardMyPresenter(new MyCardModel(), this);
        }
        mPresenter.getMyCards();
    }

    @Override
    public void showMyCards(List<CardEntity> cardEntityList) {
        if (mIsIconMode) {
            if (mIconRVAdapter == null) {
                mIconRVAdapter = new CardMyIconRVAdapter();
            }
            mGridLayoutManager.setSpanCount(2);
            mIconRVAdapter.setDatas(cardEntityList);
            mRecyclerView.setAdapter(mIconRVAdapter);
        } else {
            if (mListRVAdapter == null) {
                mListRVAdapter = new CardMyListRVAdapter();
            }
            mGridLayoutManager.setSpanCount(1);
            mListRVAdapter.setDatas(cardEntityList);
            mRecyclerView.setAdapter(mListRVAdapter);
        }

    }

    @Override
    public void showToastMessage(String message) {
        showShortToast(message);
    }
}