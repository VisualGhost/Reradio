package com.billing;

import android.content.IntentSender;

public interface BillingHelper {

    String ITEM_TYPE_INAPP = "inapp";
    String ITEM_TYPE_SUBS = "subs";

    int BILLING_VERSION = 3;

    int BILLING_RESPONSE_RESULT_OK = 0;

    void establishBillingServiceConnection(OnConnectionListener onConnectionListener);

    void disposeBillingServiceConnection();

    void launchPurchaseFlow(OnIntentSenderListener onIntentSenderListener, String sku, String itemType);

    interface OnConnectionListener {
        void onConnected();
    }

    interface OnIntentSenderListener {
        void onIntentSender(IntentSender intentSender);
    }

}
