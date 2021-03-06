package com.wonders.xlab.cardbag.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.wonders.xlab.cardbag.CBag;

/**
 * Created by hua on 16/8/21.
 */

public class CardEntity implements Parcelable {
    private String mId;
    private String mCardName;
    private String mBarCode;
    private String mImgUrl = "http://ocg8s5zv8.bkt.clouddn.com/pic_vip_card.png";
    private String mImgFilePath;
    private String mFrontImgUrl;
    private String mFrontImgFilePath;
    private String mBackImgUrl;
    private String mBackImgFilePath;
    private long mCreateDate;

    public CardEntity() {
    }

    public CardEntity(CardEntity cardEntity) {
        copy(cardEntity);
    }

    public void copy(CardEntity cardEntity) {
        if (cardEntity != null) {
            setId(cardEntity.getId());
            setCardName(cardEntity.getCardName());
            setBarCode(cardEntity.getBarCode());
            setImgUrl(cardEntity.getImgUrl());
            setImgFilePath(cardEntity.getImgFilePath());
            setFrontImgUrl(cardEntity.getFrontImgUrl());
            setFrontImgFilePath(cardEntity.getFrontImgFilePath());
            setBackImgUrl(cardEntity.getBackImgUrl());
            setBackImgFilePath(cardEntity.getBackImgFilePath());
            setCreateDate(cardEntity.getCreateDate());
        }
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = !TextUtils.isEmpty(imgUrl) ? imgUrl : CBag.get().getCardImgUrlDefault();
    }

    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String cardName) {
        mCardName = cardName;
    }

    public String getImgFilePath() {
        return mImgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        mImgFilePath = imgFilePath;
    }

    public String getBarCode() {
        return mBarCode;
    }

    public void setBarCode(String barCode) {
        mBarCode = barCode;
    }

    public String getFrontImgUrl() {
        return mFrontImgUrl;
    }

    public void setFrontImgUrl(String frontImgUrl) {
        mFrontImgUrl = frontImgUrl;
    }

    public String getFrontImgFilePath() {
        return mFrontImgFilePath;
    }

    public void setFrontImgFilePath(String frontImgFilePath) {
        mFrontImgFilePath = frontImgFilePath;
    }

    public String getBackImgUrl() {
        return mBackImgUrl;
    }

    public void setBackImgUrl(String backImgUrl) {
        mBackImgUrl = backImgUrl;
    }

    public String getBackImgFilePath() {
        return mBackImgFilePath;
    }

    public void setBackImgFilePath(String backImgFilePath) {
        mBackImgFilePath = backImgFilePath;
    }

    public long getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(long createDate) {
        mCreateDate = createDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mCardName);
        dest.writeString(this.mBarCode);
        dest.writeString(this.mImgUrl);
        dest.writeString(this.mImgFilePath);
        dest.writeString(this.mFrontImgUrl);
        dest.writeString(this.mFrontImgFilePath);
        dest.writeString(this.mBackImgUrl);
        dest.writeString(this.mBackImgFilePath);
        dest.writeLong(this.mCreateDate);
    }

    protected CardEntity(Parcel in) {
        this.mId = in.readString();
        this.mCardName = in.readString();
        this.mBarCode = in.readString();
        this.mImgUrl = in.readString();
        this.mImgFilePath = in.readString();
        this.mFrontImgUrl = in.readString();
        this.mFrontImgFilePath = in.readString();
        this.mBackImgUrl = in.readString();
        this.mBackImgFilePath = in.readString();
        this.mCreateDate = in.readLong();
    }

    public static final Creator<CardEntity> CREATOR = new Creator<CardEntity>() {
        @Override
        public CardEntity createFromParcel(Parcel source) {
            return new CardEntity(source);
        }

        @Override
        public CardEntity[] newArray(int size) {
            return new CardEntity[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardEntity entity = (CardEntity) o;

        if (mCreateDate != entity.mCreateDate) return false;
        if (!mId.equals(entity.mId)) return false;
        if (!mCardName.equals(entity.mCardName)) return false;
        if (!mBarCode.equals(entity.mBarCode)) return false;
        if (!mImgUrl.equals(entity.mImgUrl)) return false;
        if (mImgFilePath != null ? !mImgFilePath.equals(entity.mImgFilePath) : entity.mImgFilePath != null)
            return false;
        if (mFrontImgUrl != null ? !mFrontImgUrl.equals(entity.mFrontImgUrl) : entity.mFrontImgUrl != null)
            return false;
        if (mFrontImgFilePath != null ? !mFrontImgFilePath.equals(entity.mFrontImgFilePath) : entity.mFrontImgFilePath != null)
            return false;
        if (mBackImgUrl != null ? !mBackImgUrl.equals(entity.mBackImgUrl) : entity.mBackImgUrl != null)
            return false;
        return mBackImgFilePath != null ? mBackImgFilePath.equals(entity.mBackImgFilePath) : entity.mBackImgFilePath == null;

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mCardName.hashCode();
        result = 31 * result + mBarCode.hashCode();
        result = 31 * result + mImgUrl.hashCode();
        result = 31 * result + (mImgFilePath != null ? mImgFilePath.hashCode() : 0);
        result = 31 * result + (mFrontImgUrl != null ? mFrontImgUrl.hashCode() : 0);
        result = 31 * result + (mFrontImgFilePath != null ? mFrontImgFilePath.hashCode() : 0);
        result = 31 * result + (mBackImgUrl != null ? mBackImgUrl.hashCode() : 0);
        result = 31 * result + (mBackImgFilePath != null ? mBackImgFilePath.hashCode() : 0);
        result = 31 * result + (int) (mCreateDate ^ (mCreateDate >>> 32));
        return result;
    }
}
