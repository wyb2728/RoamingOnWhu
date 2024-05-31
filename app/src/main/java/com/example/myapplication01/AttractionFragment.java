package com.example.myapplication01;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.CameraUpdate;

import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.CameraUpdateFactory;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AttractionFragment extends Fragment implements
        AMapLocationListener,LocationSource, OnMapClickListener,
        AMap.InfoWindowAdapter,AMap.OnInfoWindowClickListener{
    MapView mapView = null;
    AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private static final int REQUEST_PERMISSIONS = 9527;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private MarkerOptions markerOption;
    private OnMapClickListener mClickListener;
    private OnCameraChangeListener cameraChangeListener;
    private TextView mTapTextView;
    double latitude;
    double longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attraction, container, false);
        initMap(savedInstanceState,rootView);
        initLocation();
        checkingAndroidVersion();
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(15);
        return rootView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在fragment执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        // 在fragment执行onResume时执行mMapView.onResume()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        // 在fragment执行onPause时执行mMapView.onPause()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 在fragment执行onSaveInstanceState时执行mMapView.onSaveInstanceState(outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }
    private void checkingAndroidVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Android6.0及以上先获取权限再定位
            requestPermission();
        }else {
            //Android6.0以下直接定位
            mLocationClient.startLocation();
        }
    }
    @AfterPermissionGranted(REQUEST_PERMISSIONS)
    private void requestPermission() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (EasyPermissions.hasPermissions(getContext(), permissions)) {
            //true 有权限 开始定位
            showMsg("已获得权限，可以定位啦！");
            mLocationClient.startLocation();
        } else {
            //false 无权限
            EasyPermissions.requestPermissions(this, "需要权限", REQUEST_PERMISSIONS, permissions);
        }
    }
    public void showMsg(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    private void initLocation() {
        //初始化定位
        try {
            mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mLocationClient != null) {
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取最近3s内精度最高的一次定位结果：
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位请求超时时间，单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);
            //关闭缓存机制，高精度定位会产生缓存。
            mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //地址
                String address = aMapLocation.getAddress();
                Log.d("Location", "定位成功，地址：" + address);
                mLocationClient.startLocation();
                if(mListener!=null){
                    mListener.onLocationChanged(aMapLocation);
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient != null) {
            mLocationClient.startLocation();//启动定位
        }
    }
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
    private void initMap(Bundle savedInstanceState,View rootView) {
        mapView = (MapView) rootView.findViewById(R.id.amapView0);
        // 在fragment执行onCreateView时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            addMarkersToMap();
            // 设置定位监听
            aMap.setLocationSource(this);
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            aMap.setInfoWindowAdapter(this);
            aMap.setOnInfoWindowClickListener(this);
        }
    }
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图后清理图层插上图标，在将其移动到中心位置
        aMap.clear();
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        MarkerOptions otMarkerOptions = new MarkerOptions();
        otMarkerOptions.position(latLng);
        aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }
    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);

        render(marker, infoWindow);
        return infoWindow;
    }
    private void render(Marker marker, View view) {
        ((ImageView) view.findViewById(R.id.badge))
                .setImageResource(R.drawable.icon_city);
        //修改InfoWindow标题内容样式
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(20);
            titleUi.setText(titleText+"  ");
        } else {
            titleUi.setText("");
        }
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        //1、初始化Dialog
        DetailDialog dialog=new DetailDialog(getContext(),R.style.DialogTheme);
        //获取Dialogwindow对象
        Window window=dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置动画
        window.setWindowAnimations(R.style.dialog_menu_animStyle);
        //设置对话框大小
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        //设置宽度和高度
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        //显示Dialog
        dialog.show();
    }
    private void addMarkersToMap() {
        aMap.addMarker(new MarkerOptions()
                .position(Constants.xinghu).title("星湖")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.pailou).title("国立武汉大学牌楼")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.mozi).title("墨子雕像")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.lidajiuju).title("李达旧居")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.banshanlu).title("半山庐")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.wenyiduojng).title("闻一多纪念馆")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.jianhu).title("鉴湖")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.luojiaguangchang).title("珞珈广场")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.yinghuacb).title("樱花城堡")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.lidadx).title("李达雕像")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.xingzhenglou).title("行政楼")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.shijigc).title("世纪广场")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.yuehu).title("月湖")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.zhuyuan).title("竹园")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.fengyuan).title("枫园")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.meiyuan).title("梅园")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.songyuan).title("松园")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.zhangzhidongdx).title("张之洞塑像")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.xiaoguishan).title("小龟山")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.lisiguangdx).title("李四光雕像")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.xiajianbai).title("夏坚白雕像")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.luojiashan).title("珞珈山")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.kunpenggc).title("鲲鹏广场")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.wangshijiedx).title("王世杰雕像")
                .snippet("").draggable(false));
        aMap.addMarker(new MarkerOptions()
                .position(Constants.yingyuan).title("樱园")
                .snippet("").draggable(false));
    }
}
