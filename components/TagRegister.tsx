import React from 'react';
import { NativeModules, View } from 'react-native/types';

const { NfcModule } = NativeModules;

function TagRegister(): JSX.Element {

  const scan = () => {
    return new Promise((resolve, reject) => {
      resolve(NfcModule.scan());
    });
  };

  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      TODO: tle bo jakob naredu lep UI
      tle bos kliknu in bo klicu scan
    </View>
  );
}

export default TagRegister;
