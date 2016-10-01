package com.billing;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.utils.DebugLogger;

public class DebugBillingActivity extends Activity implements
        BillingHelper.OnConnectionListener,
        BillingHelper.OnIntentSenderListener {

    private static final String SKU_ISSUE_1 = "one_issue";
    private static final String SKU_YEARLY = "one_year_issue";

    private static final int RC_REQUEST = 10001;
    private static final String TAG = DebugBillingActivity.class.getSimpleName();

    private BillingHelper mBillingHelper;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBillingHelper = new BillingHelperImpl(this, "");
        mBillingHelper.establishBillingServiceConnection(this);
        mButton = new Button(this);
        setContentView(mButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBillingHelper.disposeBillingServiceConnection();
    }

    @Override
    public void onConnected() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBillingHelper.launchPurchaseFlow(DebugBillingActivity.this, SKU_YEARLY, BillingHelper.ITEM_TYPE_SUBS);
            }
        });
    }

    @Override
    public void onIntentSender(IntentSender intentSender) {
        try {
            startIntentSenderForResult(intentSender, RC_REQUEST, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        } catch (IntentSender.SendIntentException e) {
            DebugLogger.e(TAG, e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
