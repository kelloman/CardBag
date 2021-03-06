package com.wonders.xlab.cardbag.ui.cardedit;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

/**
 * Created by hua on 16/8/26.
 */

public interface CardEditContract {
    interface View extends BaseContract.View {
        void saveSuccess();

        void showCardNameEmptyMessage();

        void showBarCodeNonMessage();
    }

    interface Presenter extends BaseContract.Presenter {
        void saveCard(CardEntity cardEntity);
    }
}
