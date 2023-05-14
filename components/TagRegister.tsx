import React, {useEffect} from 'react';
import {
  NativeModules,
  Text,
  TouchableOpacity,
  View,
  DeviceEventEmitter,
} from 'react-native';
const {ReactTagStore, LockScreenModule} = NativeModules;
import Icon from 'react-native-vector-icons/Entypo';
import IconCi from 'react-native-vector-icons/MaterialCommunityIcons';
import IconFeather from 'react-native-vector-icons/Feather';
import randomNames from './RandomNames';

function CustomButton({title, onPress}: any): JSX.Element {
  return (
    <TouchableOpacity
      onPress={onPress}
      style={{
        backgroundColor: '#46d690',
        padding: 10,
        width: 200,
        alignItems: 'center',
        borderRadius: 5,
      }}>
      <Text style={{color: 'black', fontWeight: 'bold'}}>{title}</Text>
    </TouchableOpacity>
  );
}

function IconButton({onPress, name, color, size = 30}: any): JSX.Element {
  return (
    <TouchableOpacity onPress={onPress}>
      <Icon name={name} size={size} color={color} />
    </TouchableOpacity>
  );
}

function RenderTag({name, id, onPress}: any): JSX.Element {
  return (
    <View
      style={{
        display: 'flex',
        alignItems: 'center',
        flexDirection: 'row',
        justifyContent: 'space-between',
        width: '100%',
        marginBottom: 10,
        padding: 5,
      }}>
      <View
        style={{
          flex: 1,
          display: 'flex',
          alignItems: 'center',
          flexDirection: 'row',
        }}>
        <View
          style={{
            display: 'flex',
            alignItems: 'center',
            flexDirection: 'row',
          }}>
          <IconCi name="nfc" size={60} color="white" />
          <View style={{paddingLeft: 15}}>
            <Text style={{fontSize: 25, color: 'white'}}>{name}</Text>
            <Text>{id}</Text>
          </View>
        </View>
      </View>
      <View style={{marginRight: 25}}>
        <IconButton onPress={onPress} color="red" name="minus" />
      </View>
    </View>
  );
}

function TagRegister(): JSX.Element {
  const [tags, setTags] = React.useState({} as any);
  const [waitingForTag, setWaitingForTag] = React.useState(false);

  const refreshTags = () => {
    ReactTagStore.GetMaping(map => {
      console.log(map);
      setTags(map);
    });
  };

  useEffect(() => {
    refreshTags();
  }, []);
  return (
    <View
      style={{
        display: 'flex',
        alignItems: 'center',
        backgroundColor: 'black',
        height: '100%',
      }}>
      {!waitingForTag && (
        <>
          <View
            style={{
              alignItems: 'center',
              display: 'flex',
              flexDirection: 'row',
              justifyContent: 'space-between',
              width: '90%',
              margin: 20,
              paddingBottom: 15,
            }}>
            <Text style={{fontSize: 30, color: 'white'}}>Saved NFC tags</Text>
            <IconButton
              name="plus"
              size={50}
              color="#46d690"
              onPress={() => {
                setWaitingForTag(true);
                DeviceEventEmitter.addListener('onNfcTagScanned', e => {
                  console.log('NATIVE_EVENT', e);
                  if (e) {
                    setWaitingForTag(false);
                    ReactTagStore.SetTag(
                      e.tag_id,
                      randomNames[
                        parseInt((randomNames.length * Math.random()) as any)
                      ],
                      () => {},
                    );
                    DeviceEventEmitter.removeAllListeners('onNfcTagScanned');
                    refreshTags();
                  }
                });
              }}
            />
          </View>
          <View>
            {Object.keys(tags).map((key, index) => (
              <RenderTag
                key={index}
                name={tags[key]}
                id={key}
                onPress={() => {
                  ReactTagStore.PopTag(key, () => {
                    refreshTags();
                  });
                }}
              />
            ))}
          </View>
          <TouchableOpacity
            style={{
              position: 'absolute',
              width: '100%',

              bottom: 0,
              right: 0,
              left: 0,
              padding: 10,
            }}
            onPress={() => {
              if (Object.keys(tags).length != 0) {
                LockScreenModule.lock(
                  'To unlock your phone, scan one of your NFC tag',
                );
              }
            }}>
            <View
              style={{
                backgroundColor: '#46d690',
                borderRadius: 10,
                paddingLeft: 30,
                paddingRight: 30,
                paddingTop: 10,
                paddingBottom: 10,
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                justifyContent: 'space-between',
              }}>
              <Text style={{fontSize: 30, color: 'black'}}>Lock me out</Text>
              <IconFeather name="lock" size={30} color="black" />
            </View>
          </TouchableOpacity>
        </>
      )}
      {waitingForTag && (
        <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
          <Text style={{color: 'white', fontSize: 50, padding: 40}}>
            Waiting for tag
          </Text>
          <CustomButton
            title="cancel"
            onPress={() => {
              DeviceEventEmitter.removeAllListeners('onNfcTagScanned');
              setWaitingForTag(false);
            }}
          />
        </View>
      )}
    </View>
  );
}

export default TagRegister;
