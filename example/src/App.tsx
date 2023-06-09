import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import SMSReader from 'react-native-sms-reader';

export default function App() {
  const [result] = React.useState<number | undefined>();

  React.useEffect(() => {
    // listen to SMS coming from the sender test
    SMSReader.listenOTP('test');
    // remove the SMS listener
    SMSReader.removeOTPListener();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
