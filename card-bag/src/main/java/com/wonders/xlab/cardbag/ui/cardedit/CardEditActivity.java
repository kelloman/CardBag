package com.wonders.xlab.cardbag.ui.cardedit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.data.CardEditModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.util.FileUtil;
import com.wonders.xlab.cardbag.util.ImageViewUtil;
import com.wonders.xlab.cardbag.widget.RatioImageView;
import com.wonders.xlab.cardbag.widget.TopBar;
import com.wonders.xlab.qrscanner.BarCodeEncoder;
import com.wonders.xlab.qrscanner.XQrScanner;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

public class CardEditActivity extends MVPActivity<CardEditContract.Presenter> implements CardEditContract.View {
    private final int REQUEST_CODE_MASK = 1;
    private final int REQUEST_CODE_SCAN_BAR_CODE = REQUEST_CODE_MASK << 1;
    private final int REQUEST_CODE_TAKE_PHOTO_CARD_FRONT = REQUEST_CODE_MASK << 2;
    private final int REQUEST_CODE_TAKE_PHOTO_CARD_BACK = REQUEST_CODE_MASK << 3;
    private final int REQUEST_CODE_CROP_FRONT = REQUEST_CODE_MASK << 4;
    private final int REQUEST_CODE_CROP_BACK = REQUEST_CODE_MASK << 5;

    private TopBar mTopBar;
    private RatioImageView mIvCard;
    private RatioImageView mIvCardFront;
    private RatioImageView mIvCardBack;
    private RatioImageView mIvBarCode;
    private EditText mEtCardName;
    private TextView mTvBarCode;
    private ImageView mIvClear;

    private CardEntity mCardEntity;

    private String mCardFrontPhotoPath;
    private String mCardBackPhotoPath;

    private UCrop.Options mOptions;

    private CardEditContract.Presenter mPresenter;

    @Override
    public CardEditContract.Presenter getPresenter() {
        if (null == mPresenter) {
            mPresenter = new CardEditPresenter(this, new CardEditModel());
        }
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_card_edit_activity);
        mTopBar = (TopBar) findViewById(R.id.top_bar);
        mIvCard = (RatioImageView) findViewById(R.id.iv_card);
        mIvCardFront = (RatioImageView) findViewById(R.id.iv_card_front);
        mIvCardBack = (RatioImageView) findViewById(R.id.iv_card_back);
        mIvBarCode = (RatioImageView) findViewById(R.id.iv_bar_code);
        mEtCardName = (EditText) findViewById(R.id.et_card_name);
        mTvBarCode = (TextView) findViewById(R.id.tv_bar_code);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);

        setupViewListener();

        initWithIntentExtra();

        initUCropOptions();
    }

    private void initWithIntentExtra() {
        Intent intent = getIntent();
        if (intent != null) {
            mCardEntity = intent.getParcelableExtra("data");

            if (null != mCardEntity) {
                mEtCardName.setText(mCardEntity.getCardName());
                setBarCodeView(mCardEntity.getBarCode());
                ImageViewUtil.load(this, mCardEntity.getImgUrl(), mIvCard);
                if (TextUtils.isEmpty(mCardEntity.getFrontImgUrl())) {
                    mCardFrontPhotoPath = mCardEntity.getFrontImgFilePath();
                    if (!TextUtils.isEmpty(mCardFrontPhotoPath)) {
                        ImageViewUtil.load(this, Uri.parse("file:" + mCardFrontPhotoPath), mIvCardFront);
                    }
                } else {
                    ImageViewUtil.load(this, mCardEntity.getFrontImgUrl(), mIvCardFront);
                }
                if (TextUtils.isEmpty(mCardEntity.getBackImgUrl())) {
                    mCardBackPhotoPath = mCardEntity.getBackImgFilePath();
                    if (!TextUtils.isEmpty(mCardBackPhotoPath)) {
                        ImageViewUtil.load(this, Uri.parse("file:" + mCardBackPhotoPath), mIvCardBack);
                    }
                } else {
                    ImageViewUtil.load(this, mCardEntity.getBackImgUrl(), mIvCardBack);
                }

                if (mEtCardName.length() > 0) {
                    mEtCardName.setSelection(mEtCardName.length());
                }
            }
        }

        mTopBar.setTitle(mCardEntity == null || TextUtils.isEmpty(mCardEntity.getBarCode()) ? getString(R.string.title_card_edit_add) : getString(R.string.title_card_edit_edit));
    }

    private void setupViewListener() {
        mTopBar.setOnRightMenuClickListener(new TopBar.OnRightMenuClickListener() {
            @Override
            public void onClick(View view) {
                if (mCardEntity == null) {
                    mCardEntity = new CardEntity();
                }
                mCardEntity.setCardName(mEtCardName.getText().toString());
                mCardEntity.setBarCode(mTvBarCode.getText().toString());
                mCardEntity.setFrontImgFilePath(mCardFrontPhotoPath);
                mCardEntity.setBackImgFilePath(mCardBackPhotoPath);

                getPresenter().saveCard(mCardEntity);
            }
        });
        mEtCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mIvClear.setVisibility(s.length() <= 0 ? View.INVISIBLE : View.VISIBLE);
            }
        });
        mIvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtCardName.setText("");
            }
        });
    }

    private void initUCropOptions() {
        if (mOptions == null) {
            mOptions = new UCrop.Options();
            int color = getResources().getColor(R.color.topBarBackground);
            mOptions.setToolbarColor(color);
            mOptions.setToolbarWidgetColor(getResources().getColor(R.color.textBlack));
            mOptions.setStatusBarColor(getResources().getColor(R.color.textBlack));
            mOptions.setLogoColor(getResources().getColor(R.color.textBlack));
        }
    }

    public void scanBarCode(View view) {
        XQrScanner.getInstance()
                .startForResult(this, REQUEST_CODE_SCAN_BAR_CODE);
    }

    public void shotCardFront(View view) {
        dispatchTakePictureIntent("front", REQUEST_CODE_TAKE_PHOTO_CARD_FRONT);
    }

    public void shotCardBack(View view) {
        dispatchTakePictureIntent("back", REQUEST_CODE_TAKE_PHOTO_CARD_BACK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_BAR_CODE:
                    String mBarCode = data.getStringExtra(XQrScanner.EXTRA_RESULT_BAR_OR_CODE_STRING);
                    setBarCodeView(mBarCode);
                    break;
                case REQUEST_CODE_TAKE_PHOTO_CARD_FRONT:
                    crop(REQUEST_CODE_CROP_FRONT);
                    break;
                case REQUEST_CODE_TAKE_PHOTO_CARD_BACK:
                    crop(REQUEST_CODE_CROP_BACK);
                    break;
                case REQUEST_CODE_CROP_FRONT:
                    ImageViewUtil.load(this, UCrop.getOutput(data), mIvCardFront);
                    break;
                case REQUEST_CODE_CROP_BACK:
                    ImageViewUtil.load(this, UCrop.getOutput(data), mIvCardBack);
                    break;
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(data);
                    showShortToast(cropError.getMessage());
                    break;
            }
        }
    }

    private void setBarCodeView(final String mBarCode) {
        if (TextUtils.isEmpty(mBarCode)) {
            return;
        }
        mTvBarCode.setText(mBarCode);

        mIvBarCode.post(new Runnable() {
            @Override
            public void run() {
                BarCodeEncoder ecc = new BarCodeEncoder(mIvBarCode.getWidth(), mIvBarCode.getHeight());
                try {
                    mIvBarCode.setImageBitmap(ecc.barcode(mBarCode));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void crop(int requestCode) {
        Uri pathUri = null;
        switch (requestCode) {
            case REQUEST_CODE_CROP_FRONT:
                pathUri = Uri.parse("file:" + mCardFrontPhotoPath);
                break;
            case REQUEST_CODE_CROP_BACK:
                pathUri = Uri.parse("file:" + mCardBackPhotoPath);
                break;
        }
        if (pathUri == null) {
            showShortToast(getString(R.string.crop_picture_failed));
            return;
        }
        UCrop.of(pathUri, pathUri)
                .withAspectRatio(3, 2)
//                .withMaxResultSize(maxWidth, maxHeight)
                .withOptions(mOptions)
                .start(this, requestCode);

    }

    private void dispatchTakePictureIntent(String fileName, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileUtil.createTempFile(this, fileName);
                if (photoFile == null) {
                    showShortToast(getString(R.string.save_photo_failed));
                    return;
                }
                switch (requestCode) {
                    case REQUEST_CODE_TAKE_PHOTO_CARD_FRONT:
                        mCardFrontPhotoPath = photoFile.getAbsolutePath();
                        break;
                    case REQUEST_CODE_TAKE_PHOTO_CARD_BACK:
                        mCardBackPhotoPath = photoFile.getAbsolutePath();
                        break;
                }
            } catch (IOException ex) {
                // Error occurred while creating the File
                showShortToast(getString(R.string.save_photo_failed));
            }
            // Continue only if the File was successfully created
            Uri photoURI = Uri.fromFile(photoFile);//FileProvider.getUriForFile(this,"com.wonders.xlab.cardbag.fileprovider",photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, requestCode);
        }
    }

    @Override
    public void saveSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
