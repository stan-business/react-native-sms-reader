package com.smsreader;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Promise;

abstract class SmsReaderSpec extends ReactContextBaseJavaModule {
  SmsReaderSpec(ReactApplicationContext context) {
    super(context);
  }
}
