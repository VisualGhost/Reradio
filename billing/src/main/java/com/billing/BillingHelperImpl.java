package com.billing;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.vending.billing.IInAppBillingService;
import com.utils.DebugLogger;

public class BillingHelperImpl implements BillingHelper {

    private static final String TAG = BillingHelperImpl.class.getSimpleName();

    // Keys for the responses from InAppBillingService
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String RESPONSE_BUY_INTENT = "BUY_INTENT";

    private IInAppBillingService mService;
    private ServiceConnection mConnection;
    private Context mContext;

    private boolean mIsItemBillingSupported;
    private boolean mIsSubsBillingSupported;

    String mSignatureBase64;

    public BillingHelperImpl(Context context, String base64PublicKey) {
        mContext = context;
        mSignatureBase64 = base64PublicKey;
    }

    @Override
    public void establishBillingServiceConnection(OnConnectionListener onConnectionListener) {
        mConnection = getConnection(onConnectionListener);
        bindService(mConnection);
    }

    @Override
    public void disposeBillingServiceConnection() {
        DebugLogger.d(TAG, "disposing");
        if (mConnection != null && mContext != null) {
            mContext.unbindService(mConnection);
        }
        mService = null;
        mConnection = null;
    }

    private ServiceConnection getConnection(final OnConnectionListener onConnectionListener) {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mService = IInAppBillingService.Stub.asInterface(iBinder);
                try {
                    mIsItemBillingSupported = isBillingSupported(ITEM_TYPE_INAPP);
                    mIsSubsBillingSupported = isBillingSupported(ITEM_TYPE_SUBS);
                    DebugLogger.dt(TAG, mIsItemBillingSupported);
                } catch (RemoteException e) {
                    DebugLogger.e(TAG, "Billing is not supported");
                }
                onConnectionListener.onConnected();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mService = null;
            }
        };
    }

    private void bindService(ServiceConnection serviceConnection) {
        Intent serviceIntent = getServiceIntent();
        if (isServiceAvailable(serviceIntent)) {
            DebugLogger.d(TAG, "Service available");
            mContext.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            DebugLogger.d(TAG, "Service NOT available");
            // no service available to handle that Intent
        }
    }

    private Intent getServiceIntent() {
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        return serviceIntent;
    }

    private boolean isServiceAvailable(Intent intent) {
        return !mContext.getPackageManager().queryIntentServices(intent, 0).isEmpty();
    }

    private boolean isBillingSupported(String type) throws RemoteException {
        return mService.isBillingSupported(BILLING_VERSION, getPackageName(), type) ==
                BILLING_RESPONSE_RESULT_OK;
    }

    private String getPackageName() {
        return mContext.getPackageName();
    }

    @Override
    public void launchPurchaseFlow(OnIntentSenderListener onIntentSenderListener, String sku, String itemType) {
        if (itemType.equals(ITEM_TYPE_SUBS) && !mIsSubsBillingSupported) {
            DebugLogger.d(TAG, "Subscription are not available");
        } else {
            try {
                Bundle buyIntentBundle = getBuyIntentBundle(sku, itemType);
                int response = getResponseCodeFromBundle(buyIntentBundle);
                if (response == BILLING_RESPONSE_RESULT_OK) {
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable(RESPONSE_BUY_INTENT);
                    if (pendingIntent != null) {
                        onIntentSenderListener.onIntentSender(pendingIntent.getIntentSender());
                    }
                }
            } catch (RemoteException e) {
                DebugLogger.e(TAG, e.toString());
            }
        }
    }

    private Bundle getBuyIntentBundle(String sku, String itemType) throws RemoteException {
        return mService.getBuyIntent(BILLING_VERSION, getPackageName(), sku, itemType, "");
    }

    // Workaround to bug where sometimes response codes come as Long instead of Integer
    int getResponseCodeFromBundle(Bundle b) {
        Object o = b.get(RESPONSE_CODE);
        if (o == null) {
            DebugLogger.d(TAG, "Bundle with null response code, assuming OK (known issue)");
            return BILLING_RESPONSE_RESULT_OK;
        } else if (o instanceof Integer) return ((Integer) o).intValue();
        else if (o instanceof Long) return (int) ((Long) o).longValue();
        else {
            DebugLogger.e(TAG, "Unexpected type for bundle response code.");
            throw new RuntimeException("Unexpected type for bundle response code: " + o.getClass().getName());
        }
    }
}
