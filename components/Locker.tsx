import React from 'react';
import {NativeModules, Text, TouchableOpacity, View} from 'react-native';
const {LockScreenModule} = NativeModules;

import TagRegister from './TagRegister';

function enableAdmin() {
  //Alert.alert('You tapped the button! Enabling admin...');
  LockScreenModule.enableAdmin();
}

function Locker(): JSX.Element {
  const [isLocked, setIsLocked] = React.useState(false);
  LockScreenModule.isAdminActive((result: boolean) => {
    if (!result) {
      enableAdmin();
    }
  });

  LockScreenModule.isLockEnabled((result: boolean) => {
    setIsLocked(result);
  });

  return <TagRegister />;
}

export default Locker;
