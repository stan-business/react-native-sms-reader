# react-native-sms-reader

SMS reader with recipient filter

Upgrade of unmainted project [react-native-sms-user-consent](https://github.com/kyivstarteam/react-native-sms-user-consent)

## Installation

```sh
npm install react-native-sms-reader
```

**Not yet available for iOS**

## Usage

```js
import SMSReader from 'react-native-sms-reader';

// listen to SMS coming from the sender test
SMSReader.listenOTP('test');
// remove the SMS listener
SMSReader.removeOTPListener();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
