package com.example.myapplication01;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.CameraUpdateFactory;

import com.example.myapplication01.R;
import com.example.myapplication01.data.Constants;

import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomActivity extends AppCompatActivity implements
        AMapLocationListener, LocationSource, AMap.OnMapClickListener,
        AMap.InfoWindowAdapter {

    MapView mapView = null;
    AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private static final int REQUEST_PERMISSIONS = 9527;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private MarkerOptions markerOption;
    double latitude;
    double longitude;
    String id;
    String key;
    boolean isJoined = false;

    private RoomApi roomApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        OkHttpClient client = getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.211.8:5004/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        roomApi = retrofit.create(RoomApi.class);

        Intent intent = getIntent();
        initPara(intent);
        initMap(savedInstanceState);
        initLocation();
        checkingAndroidVersion();
    }
    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // 创建一个不验证证书链的信任管理器
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // 安装全信任的信任管理器
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // 创建一个不验证主机名的主机名验证器
            HostnameVerifier hostnameVerifier = (hostname, session) -> true;

            // 构建 OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(hostnameVerifier);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);

            // 添加日志拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
        leaveRoom();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    private void checkingAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        } else {
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

        if (EasyPermissions.hasPermissions(this, permissions)) {
            showMsg("已获取定位权限！");
            mLocationClient.startLocation();
        } else {
            EasyPermissions.requestPermissions(this, "需要权限", REQUEST_PERMISSIONS, permissions);
        }
    }

    public void showMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initLocation() {
        try {
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mLocationClient != null) {
            mLocationClient.setLocationListener(this);
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocationLatest(true);
            mLocationOption.setNeedAddress(true);
            mLocationOption.setHttpTimeOut(20000);
            mLocationOption.setLocationCacheEnable(false);
            mLocationClient.setLocationOption(mLocationOption);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                String address = aMapLocation.getAddress();
                Log.d("Location", "定位成功，地址：" + address);
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                if (mListener != null) {
                    mListener.onLocationChanged(aMapLocation);
                }
                mLocationClient.stopLocation();

                // 获取位置成功后执行加入房间操作
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                joinOrUpdateRoom();

            } else {
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient != null) {
            mLocationClient.startLocation();
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

    private void initMap(Bundle savedInstanceState) {
        mapView = findViewById(R.id.aMapView3);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            addMarkersToMap();
            aMap.setLocationSource(this);
            aMap.setMyLocationEnabled(true);
            aMap.setInfoWindowAdapter(this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        aMap.clear();
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        MarkerOptions otMarkerOptions = new MarkerOptions();
        otMarkerOptions.position(latLng);
        aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        updateLocation();
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        return infoContent;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        return infoWindow;
    }

    private void addMarkersToMap() {
        //每次添加前将清除原有标点
        //添加所有成员的位置标点，包括自己的位置
        roomApi.getMembersLocation(key).enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    aMap.clear();
                    for (Member member : response.body()) {
                        LatLng latLng = new LatLng(member.getLatitude(), member.getLongitude());
                        aMap.addMarker(new MarkerOptions().position(latLng).title(member.getId()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                Log.e("RoomActivity", "Failed to get members' location", t);
            }
        });
    }

    private void initPara(Intent intent) {
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getStringExtra("id");
            if (id == null) {
                showMsg("id为空");
                finish(); // 关闭Activity
                return;
            }
        } else {
            showMsg("未传递id参数");
            finish(); // 关闭Activity
            return;
        }

        if (intent.hasExtra("key")) {
            key = intent.getStringExtra("key");
            if (key == null) {
                showMsg("key为空");
                finish(); // 关闭Activity
            }
        } else {
            showMsg("未传递key参数");
            finish(); // 关闭Activity
        }
    }

    private void joinOrUpdateRoom() {
        if (isJoined) {
            updateLocation();
        } else {
            roomApi.createOrJoinRoom(key, id, latitude, longitude).enqueue(new Callback<Room>() {
                @Override
                public void onResponse(Call<Room> call, Response<Room> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        isJoined = true;
                        showMsg("成功加入房间!!!!");
                        addMarkersToMap();
                    } else {
                        showMsg("无法加入房间");
                    }
                }

                @Override
                public void onFailure(Call<Room> call, Throwable t) {
                    showMsg("请求房间失败");
                    Log.e("RoomActivity", "Failed to join room", t);
                }
            });
        }
    }

    private void updateLocation() {
        roomApi.updateLocation(key, id, latitude, longitude).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showMsg("位置更新成功");
                } else {
                    showMsg("无法更新位置");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showMsg("请求更新失败");
                Log.e("RoomActivity", "Failed to update location", t);
            }
        });
    }

    private void leaveRoom() {
        roomApi.leaveRoom(key, id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showMsg("已退出房间");
                } else {
                    showMsg("无法退出房间");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showMsg("请求退出失败");
                Log.e("RoomActivity", "Failed to leave room", t);
            }
        });
    }
}
