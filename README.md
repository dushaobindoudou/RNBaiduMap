# react-native-baidu-map
百度地图的React-Native版本,目前仅支持android系统

## example

```javascript
'use strict';

import React, {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  DeviceEventEmitter
} from 'react-native';

import BaiduMap from 'RNBaiduMap';


function markeredClick(e){
    alert('当前点击的markerId：'+e.id);
}

class BaiduMapExample extends React.Component {
    componentWillUnmount(){
        this.listenrer.remove();
    }

    componentWillMount(){
        this.listenrer = DeviceEventEmitter.addListener('markerClick',function(e:Event){
            alert('当前点击的markerId：'+e.id);
        });
    }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          React Native Baidu MapView!
        </Text>
        <BaiduMap
            center={[40.0162110000,116.3990360000]}
            marker={[
                [40.0162110000,116.3990360000,1],
                [40.0172110000,116.3990360000,2],
                [40.0182110000,116.3990360000,3],
                [40.0152110000,116.3990360000,4]
              ]}
            level={15}
            zoomBtnVisibility={false}
            style={{flex:1}}
            mode={1}
            markerClickEnabled={true}>
        </BaiduMap>
      </View>
    );
  }
}

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    color: 'red',
    textAlign: 'center',
    margin: 10,
  }
});

AppRegistry.registerComponent('BaiduMapExample', () => BaiduMapExample);

```
## 有问题请联系我吧
[RNBaiduMap github](https://github.com/dushaobindoudou/RNBaiduMap)




## step 1 install
```sh
$ npm install RNBaiduMap --save
```

## Step 2 - Update Gradle Settings
```gradle
// file: android/settings.gradle
...

include ':RNBaiduMap',  ':app'
project(':RNBaiduMap').projectDir = new File(rootProject.projectDir, '../node_modules/RNBaiduMap')
```

## Step 3 - Update app Gradle Build

```gradle
// file: android/app/build.gradle
...

dependencies {
    ...
    compile project(':RNBaiduMap')
}
```

## Step 4 - Register React Package
```java
public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

  private ReactInstanceManager mReactInstanceManager;
  private ReactRootView mReactRootView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //初始化百度地图
      SDKInitializer.initialize(getApplicationContext());

      mReactRootView = new ReactRootView(this);
      mReactInstanceManager = ReactInstanceManager.builder()
              .setApplication(getApplication())
              .setBundleAssetName("index.android.bundle")
              .setJSMainModuleName("index.android")
              .addPackage(new MainReactPackage())
              .addPackage(new BaiduMapReactPackage(this)) // <-- Register package here
              .setUseDeveloperSupport(true)
              .setInitialLifecycleState(LifecycleState.RESUMED)
              .build();

      mReactRootView.startReactApplication(mReactInstanceManager, "AwesomeProj", null);
      setContentView(mReactRootView);
  }
```


## update AndroidManifest.xml，填写申请的app的key
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="百度注册的app的name">

    <!--地图要求的权限-->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_SETTINGS" />


    <application
      android:allowBackup="true"
      android:label="@string/app_name"
      android:icon="@mipmap/ic_launcher"
      android:theme="@style/AppTheme">

        <meta-data
          android:name="com.baidu.lbsapi.API_KEY"
          android:value="App的key" />

    </application>

</manifest>
```


## 好吧
改版本是基于 baidumapkit 修改的，感谢
