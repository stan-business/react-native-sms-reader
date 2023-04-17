package com.rnsmsreader;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class RNSMSReaderModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;
    private Promise promise;
    private SmsRetrieveBroadcastReceiver receiver;
    private static final String E_OTP_ERROR = "E_OTP_ERROR";
    private static final String RECEIVED_OTP_PROPERTY = "receivedOtpMessage";
    public static final int SMS_CONSENT_REQUEST = 1244;
    public static final String NAME = "RNSMSReader";

    public RNSMSReaderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    @NonNull
    public String getName() {
      return NAME;
    }

   @ReactMethod
   public void listenOTP(String sender, final Promise promise) {
       unregisterReceiver();

       if (this.receiver != null) {
           promise.reject(E_OTP_ERROR, new Error("Reject previous request"));
       }

       this.promise = promise;
       Task<Void> task = SmsRetriever.getClient(reactContext.getCurrentActivity()).startSmsUserConsent(sender);
       task.addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               // successfully started an SMS Retriever for one SMS message
               registerReceiver();
           }
       });
       task.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               promise.reject(E_OTP_ERROR, e);
           }
       });
   }

    @ReactMethod
    public void removeOTPListener() {
        unregisterReceiver();
    }

    private void registerReceiver() {
        receiver = new SmsRetrieveBroadcastReceiver(reactContext.getCurrentActivity());
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        reactContext.getCurrentActivity().registerReceiver(receiver, intentFilter, SmsRetriever.SEND_PERMISSION, null);
    }

    private void unregisterReceiver() {
        if (receiver != null) {
            try {
                reactContext.getCurrentActivity().unregisterReceiver(receiver);
                receiver = null;
            } catch(IllegalStateException e) {
                // Failed to unregister, mostly it happens when SIM card is not setup
            }
        }
    }

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            switch (requestCode) {
                case SMS_CONSENT_REQUEST:
                    unregisterReceiver();
                    if (resultCode == RESULT_OK) {
                        // Get SMS message content
                        String message = intent.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                        WritableMap map = Arguments.createMap();
                        map.putString(RECEIVED_OTP_PROPERTY, message);
                        promise.resolve(map);
                    } else {
                        promise.reject(E_OTP_ERROR, new Error("Result code: " + resultCode));
                    }
                    promise = null;
                    break;
            }
        }
    };
}
