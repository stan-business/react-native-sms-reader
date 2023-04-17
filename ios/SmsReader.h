
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNSmsReaderSpec.h"

@interface SmsReader : NSObject <NativeSmsReaderSpec>
#else
#import <React/RCTBridgeModule.h>

@interface SmsReader : NSObject <RCTBridgeModule>
#endif

@end
