import React from 'react';
import {NativeModules, Text, TouchableOpacity, View} from 'react-native';

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

const {NfcModule} = NativeModules;

function TagRegister(): JSX.Element {
  const [nfctag, setNfctag] = React.useState();
  const scanNfc = () => NfcModule.scan(setNfctag);
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Text>Actions that will unlock your phone</Text>
      <CustomButton title="scan nfc" onPress={scanNfc} />
      <Text>{nfctag}</Text>
    </View>
  );
}

export default TagRegister;
