
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNSMSReaderSpec.h"

@interface RNSMSReader : NSObject <NativeSmsReaderSpec>
#else
#import <React/RCTBridgeModule.h>

@interface RNSMSReader : NSObject <RCTBridgeModule>
#endif

@end
