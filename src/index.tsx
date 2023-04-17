import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-sms-reader' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const SmsReaderModule = isTurboModuleEnabled
  ? require('./NativeSmsReader').default
  : NativeModules.SmsReader;

const RNSMSReader = SmsReaderModule
  ? SmsReaderModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export default class SMSUserConsent {
  static listenOTP(sender: string) {
    if (Platform.OS !== 'ios' && Platform.OS !== 'android') {
      return false;
    }
    return RNSMSReader.listenOTP(sender);
  }

  static removeOTPListener() {
    if (Platform.OS !== 'ios' && Platform.OS !== 'android') {
      return false;
    }
    return RNSMSReader.removeOTPListener();
  }
}
