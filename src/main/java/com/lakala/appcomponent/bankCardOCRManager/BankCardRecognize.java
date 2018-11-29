package com.lakala.appcomponent.bankCardOCRManager;

import android.app.Activity;
import android.graphics.Bitmap;

import exocr.bankcard.BankManager;
import exocr.bankcard.BuildConfig;
import exocr.bankcard.DataCallBack;
import exocr.bankcard.EXBankCardInfo;

/**
 * 银行卡ocr
 */
public class BankCardRecognize implements DataCallBack {

    private final IBankCardRecognize iBankCardRecognize;

    private Activity activity;

    public BankCardRecognize(Activity activity, IBankCardRecognize iBankCardRecognize) {
        this.activity = activity;
        this.iBankCardRecognize = iBankCardRecognize;
        BankManager.getInstance().setView(null);
    }

    public void startRecognize() {
        BankManager.getInstance().setPackageName(activity.getPackageName());
        BankManager.getInstance().setRecoSupportOrientation(BankManager.supportOrientations.onlyPortrait);
        BankManager.getInstance().showLogo(false);
        BankManager.getInstance().setUseLog(BuildConfig.DEBUG);
        BankManager.getInstance().setShowPhoto(true);
        BankManager.getInstance().recognize(this, activity);
    }

    @Override
    public void onRecSuccess(int i, EXBankCardInfo exBankCardInfo) {
        if (iBankCardRecognize != null) {
            iBankCardRecognize.onCardDetected(exBankCardInfo);
        }

        BankManager.getInstance().pauseRecognizeWithStopStream(true);
    }

    @Override
    public void onRecCanceled(int i) {
    }

    @Override
    public void onRecFailed(int i, Bitmap bitmap) {
        if (iBankCardRecognize != null) {
            iBankCardRecognize.onFail(bitmap);
        }

        BankManager.getInstance().pauseRecognizeWithStopStream(true);
    }

    @Override
    public void onCameraDenied() {
        if (iBankCardRecognize != null) {
            iBankCardRecognize.onFail(null);
        }
    }

    public interface IBankCardRecognize {
        void onCardDetected(EXBankCardInfo parcelable);

        void onFail(Bitmap bitmap);
    }
}
