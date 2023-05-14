import React, {useEffect} from 'react';
import {
  NativeModules,
  Text,
  TouchableOpacity,
  View,
  DeviceEventEmitter,
} from 'react-native';
const {ReactTagStore} = NativeModules;
import Icon from 'react-native-vector-icons/Entypo';
import IconCi from 'react-native-vector-icons/MaterialCommunityIcons';

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

function IconButton({onPress, name}: any): JSX.Element {
  return (
    <TouchableOpacity onPress={onPress}>
      <Icon name={name} size={30} color="#f00" />
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
        width: '100%',
      }}>
      <IconCi name="nfc" size={60} color="white" />
      <View>
        <Text style={{fontSize: 25, color: 'white'}}>{name}</Text>
        <Text>{id}</Text>
      </View>
      <View style={{padding: 20}}>
        <IconButton onPress={onPress} name="minus" />
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
          <Text style={{fontSize: 30, color: 'white', margin: 20}}>
            Saved NFC tags
          </Text>
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
          <CustomButton
            title="Add a new nfc tag"
            onPress={() => {
              setWaitingForTag(true);
              DeviceEventEmitter.addListener('onNfcTagScanned', e => {
                console.log('NATIVE_EVENT', e);
                if (e) {
                  setWaitingForTag(false);
                  ReactTagStore.SetTag(e.tag_id, Date().toString(), () => {});
                  DeviceEventEmitter.removeAllListeners('onNfcTagScanned');
                  refreshTags();
                }
              });
            }}
          />
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
