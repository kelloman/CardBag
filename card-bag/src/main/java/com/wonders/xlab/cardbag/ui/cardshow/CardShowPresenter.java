package com.wonders.xlab.cardbag.ui.cardshow;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.CardContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

public class CardShowPresenter extends BasePresenter implements CardShowContract.Presenter {
    private CardShowContract.View mView;
    private CardContract.Model mModel;

    public CardShowPresenter(CardShowContract.View view, CardContract.Model model) {
        mView = view;
        mModel = model;
        attachModels(mModel);
    }

    @Override
    public void getAllCards() {
        mModel.getAllCards(new BaseContract.Model.Callback<List<CardEntity>>() {
            @Override
            public void onSuccess(List<CardEntity> cardEntityList) {
                if (cardEntityList == null || cardEntityList.size() == 0) {
                    mView.noCardData();
                    mView.showMenu(false);
                } else {
                    mView.showMenu(true);
                    mView.showCardViewPager(cardEntityList);
                }
            }

            @Override
            public void onFail(DefaultException e) {
                if (e == null) {
                    e = new DefaultException("获取卡片数据出错");
                }
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
