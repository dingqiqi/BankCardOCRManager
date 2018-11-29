package com.lakala.appcomponent.bankCardOCRManager;

import android.app.Activity;

public class BankCardManager {

    /**
     * 银行卡识别
     *
     * @param callback 回调
     */
    public void bankCardRecognize(Activity activity, BankCardRecognize.IBankCardRecognize callback) {
        if (activity == null) {
            if (callback != null) {
                callback.onFail(null);
                return;
            }
        }

        BankCardRecognize cardRecognize = new BankCardRecognize(activity, callback);
        cardRecognize.startRecognize();
    }
}
