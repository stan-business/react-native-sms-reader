package com.smsreader;

import com.facebook.react.bridge.ReactApplicationContext;

abstract class SmsReaderSpec extends NativeSmsReaderSpec {
  SmsReaderSpec(ReactApplicationContext context) {
    super(context);
  }
}
