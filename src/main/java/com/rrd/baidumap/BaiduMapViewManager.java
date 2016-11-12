package com.rrd.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ZoomControls;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.bridge.ReadableArray;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.uimanager.LayoutShadowNode;

import java.util.Map;

import static com.baidu.mapapi.BMapManager.getContext;


public class BaiduMapViewManager extends SimpleViewManager<MapView> implements ActivityEventListener,LifecycleEventListener{
    public static final String RCT_CLASS = "RCTBaiduMap";
    public static final String TAG = "RCTBaiduMap";

    private Activity mActivity;
    private ReactContext mContext;
    private MapView mv;
    private OnMarkerClickListener omcl;

    private boolean isInited = false;
    private boolean isBind = false;

    @Override
    public LayoutShadowNode createShadowNodeInstance() {
        return new BaiduMapShadowNode();
    }

    @Override
    public Class getShadowNodeClass() {
        return BaiduMapShadowNode.class;
    }

//    @Override
//    public void onActivityResult() {
//        //onActivityResult(, , );
//    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent){

    }

    @Override
    public void onHostResume() {
        // Activity `onResume`

    }

    @Override
    public void onHostPause() {
        // Activity `onPause`
    }

    @Override
    public void onHostDestroy() {
        // Activity `onDestroy`
        //mv.getMap().removeMarkerClickListener(omcl);
    }


    public BaiduMapViewManager(Activity activity) {
        mActivity = activity;

    }

//    public void onReceiveNativeEvent(){
//        WritableMap event = Arguments.createMap();
//        event.putString("message", "MyMessage");
//        ReactContext reactContext = (ReactContext)getContext();
//        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
//                getId(),
//                "topChange",
//                event);
//    }


    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params){

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName,params);


    }

    /**
     * 地图模式
     *
     * @param mapView
     * @param type
     *  1. 普通
     *  2.卫星
     */
    @ReactProp(name="mode", defaultInt = 1)
    public void setMode(MapView mapView, int type) {
        Log.i(TAG, "mode:" + type);
        mapView.getMap().setMapType(type);
    }

    @ReactProp(name="level", defaultInt = 10)
    public void setLevel(MapView mapView, int level) {
        Log.i(TAG, "mode:" + level);
        MapStatus mMapStatus = new MapStatus.Builder()
                .zoom(level)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mapView.getMap().setMapStatus(mMapStatusUpdate);
    }

    /**
     * 实时交通图
     *
     * @param mapView
     * @param isEnabled
     */
    @ReactProp(name="trafficEnabled", defaultBoolean = false)
    public void setTrafficEnabled(MapView mapView, boolean isEnabled) {
        Log.d(TAG, "trafficEnabled:" + isEnabled);
        mapView.getMap().setTrafficEnabled(isEnabled);
    }

    @ReactProp(name="markerClickEnabled", defaultBoolean = false)
    public void setMarkerClickEnabled(MapView mapView, boolean isEnabled) {
        //Log.d(TAG, "trafficEnabled:" + isEnabled);
        //mapView.getMap().setTrafficEnabled(isEnabled);
        Log.e("添加click","markerClicked");
        if(omcl == null) {
            omcl = new OnMarkerClickListener() {
                /**
                 * 地图 Marker 覆盖物点击事件监听函数
                 *
                 * @param marker 被点击的 marker
                 */
                public boolean onMarkerClick(Marker marker) {

                    WritableMap params = Arguments.createMap();

                    params.putString("title", marker.getTitle());
                    params.putDouble("id", marker.getExtraInfo().getDouble("id"));

                    sendEvent(mContext, "markerClick", params);

                    return false;

                }
            };
            //mapView.getMap().setOnMarkerClickListener(omcl);
        }
        mapView.getMap().setOnMarkerClickListener(omcl);

        //mapView.getMap().removeMarkerClickListener(omcl);


//        else{
//            mapView.getMap().removeMarkerClickListener(omcl);
//            omcl = new OnMarkerClickListener() {
//                /**
//                 * 地图 Marker 覆盖物点击事件监听函数
//                 *
//                 * @param marker 被点击的 marker
//                 */
//                public boolean onMarkerClick(Marker marker) {
//
//                    WritableMap params = Arguments.createMap();
//
//                    params.putString("title", marker.getTitle());
//                    params.putDouble("id", marker.getExtraInfo().getDouble("id"));
//
//                    sendEvent(mContext, "markerClick", params);
//
//                    return false;
//
//                }
//            };
//        }



    }


    /**
     * 实时道路热力图
     *
     * @param mapView
     * @param isEnabled
     */
    @ReactProp(name="heatMapEnabled", defaultBoolean = false)
    public void setHeatMapEnabled(MapView mapView, boolean isEnabled) {
        Log.d(TAG, "heatMapEnabled" + isEnabled);
        mapView.getMap().setBaiduHeatMapEnabled(isEnabled);
    }

    @ReactProp(name="zoomBtnVisibility",defaultBoolean = false)
    public void setZoomBtnVisibility(MapView mapView, boolean isEnabled){

        // 隐藏缩放控件

        mapView.showZoomControls(false);


    }


    /**
     * 显示地理标记
     *
     * @param mapView
     * @param array
     */
    @ReactProp(name="marker")
    public void setMarker(MapView mapView, ReadableArray array) {
        Log.d(TAG, "marker:" + array);
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                ReadableArray sub = array.getArray(i);
                //定义Maker坐标点
                LatLng point = new LatLng(sub.getDouble(0), sub.getDouble(1));
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
                Bundle bd = new Bundle();
                //bd.putChar("id","a")
                bd.putDouble("id",sub.getDouble(2));

                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap)
                        .draggable(true)
                        .title("a").extraInfo(bd);

                //在地图上添加Marker，并显示
                mapView.getMap().addOverlay(option);
            }
        }
    }

    //设置地图中心点的位置
    @ReactProp(name="center")
    public void setCenter(MapView mapView,ReadableArray array){
        Log.d(TAG, "marker:" + array);
        if (array != null &&array.size() == 2) {
            LatLng point = new LatLng(array.getDouble(0), array.getDouble(1));

            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(point)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mapView.getMap().setMapStatus(mMapStatusUpdate);

        }

    }

    @Override
    public String getName() {
        return RCT_CLASS;
    }

    @Override
    public MapView createViewInstance(ThemedReactContext reactContext) {

        mContext = reactContext;

        reactContext.addActivityEventListener(this);
        reactContext.addLifecycleEventListener(this);

        if(!isInited) {
            SDKInitializer.initialize(reactContext.getApplicationContext());
            isInited = true;
        }

        mv = new MapView(mActivity);
        return mv;
    }
}
