
import React from 'react';
import { Alert, DeviceEventEmitter, Text, TouchableOpacity, View } from "react-native";


function CustomButton({ title, onPress }: any): JSX.Element {
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
            <Text style={{ color: 'white', fontWeight: 'bold' }}>{title}</Text>
        </TouchableOpacity>
    );
}

function onPress() {
    Alert.alert('You tapped the button! Locking screen...');
}

// function to lock the screen
function lockScreen() {
    // lock the screen
    DeviceEventEmitter.emit('lockScreen', true);
}

function Locker(): JSX.Element {
    
    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <CustomButton title="Click Me" onPress={onPress} />
        </View>
    );
}

export default Locker;