import React from 'react';
import {Alert, Text, TouchableOpacity, View, NativeModules} from 'react-native';
const {LockScreenModule, Pedometer} = NativeModules;

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
  //LockScreenModule.lockScreen();
  Pedometer.getStepCount((steps :any) => Alert.alert(steps));
}

// function to lock the screen
function lockScreen() {}

function Locker(): JSX.Element {
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <CustomButton title="Click Me" onPress={onPress} />
    </View>
  );
}

export default Locker;
