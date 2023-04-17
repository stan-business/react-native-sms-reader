import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  listenOTP(sender: string): void;
  removeOTPListener(): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RNSMSReader');
