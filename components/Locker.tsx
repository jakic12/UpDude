import React from 'react';
import {NativeModules, Text, TouchableOpacity, View} from 'react-native';
const {LockScreenModule} = NativeModules;

import TagRegister from './TagRegister';

function CustomButton({title, onPress}: any): JSX.Element {
  return (
    <TouchableOpacity
      onPress={onPress}
      style={{
        backgroundColor: '#2980b9',
        padding: 10,
        width: 200,
        alignItems: 'center',
        borderRadius: 5,
      }}>
      <Text style={{color: 'white', fontWeight: 'bold'}}>{title}</Text>
    </TouchableOpacity>
  );
}

function onPress() {
  //Alert.alert('You tapped the button! Locking screen...');
  LockScreenModule.lock("Get locked lol");
}

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
