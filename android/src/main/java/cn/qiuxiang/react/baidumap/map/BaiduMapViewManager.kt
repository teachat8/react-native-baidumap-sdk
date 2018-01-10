package cn.qiuxiang.react.baidumap.map

import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap.MAP_TYPE_NORMAL
import com.baidu.mapapi.map.BaiduMap.MAP_TYPE_SATELLITE
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp

@Suppress("unused")
class BaiduMapViewManager : ViewGroupManager<BaiduMapView>() {
    override fun getName(): String {
        return "BaiduMapView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): BaiduMapView {
        SDKInitializer.initialize(reactContext.applicationContext)
        return BaiduMapView(reactContext)
    }

    override fun onDropViewInstance(mapView: BaiduMapView) {
        super.onDropViewInstance(mapView)
        mapView.destroy()
    }

    companion object {
        val ANIMATE_TO = 1
    }

    override fun getCommandsMap(): Map<String, Int> {
        return mapOf("animateTo" to ANIMATE_TO)
    }

    override fun receiveCommand(mapView: BaiduMapView, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            ANIMATE_TO -> mapView.animateTo(args)
        }
    }

    @ReactProp(name = "satellite")
    fun setSatellite(mapView: BaiduMapView, satellite: Boolean) {
        mapView.map.mapType = if (satellite) MAP_TYPE_SATELLITE else MAP_TYPE_NORMAL
    }

    @ReactProp(name = "trafficEnabled")
    fun setTrafficEnabled(mapView: BaiduMapView, enabled: Boolean) {
        mapView.map.isTrafficEnabled = enabled
    }

    @ReactProp(name = "baiduHeatMapEnabled")
    fun setBaiduHeatMapEnabled(mapView: BaiduMapView, enabled: Boolean) {
        mapView.map.isBaiduHeatMapEnabled = enabled
    }

    @ReactProp(name = "indoorEnabled")
    fun setIndoorEnabled(mapView: BaiduMapView, enabled: Boolean) {
        mapView.map.setIndoorEnable(enabled)
    }

    @ReactProp(name = "buildingsDisabled")
    fun setBuildingsDisabled(mapView: BaiduMapView, disabled: Boolean) {
        mapView.map.isBuildingsEnabled = !disabled
    }

    @ReactProp(name = "scaleBarDisabled")
    fun setScaleBarEnabled(mapView: BaiduMapView, disabled: Boolean) {
        mapView.mapView.showScaleControl(!disabled)
    }

    @ReactProp(name = "compassDisabled")
    fun setCompassEnabled(mapView: BaiduMapView, disabled: Boolean) {
        mapView.map.uiSettings.isCompassEnabled = !disabled
    }

    @ReactProp(name = "zoomControlsDisabled")
    fun setZoomControlsDisabled(mapView: BaiduMapView, disabled: Boolean) {
        mapView.mapView.showZoomControls(!disabled)
    }

    @ReactProp(name = "center")
    fun setCenter(mapView: BaiduMapView, center: ReadableMap) {
        mapView.map.setMapStatus(MapStatusUpdateFactory.newLatLng(LatLng(
            center.getDouble("latitude"),
            center.getDouble("longitude")
        )))
    }

    @ReactProp(name = "zoomLevel")
    fun setZoomLevel(mapView: BaiduMapView, zoomLevel: Float) {
        mapView.map.setMapStatus(MapStatusUpdateFactory.zoomTo(zoomLevel))
    }

    @ReactProp(name = "rotation")
    fun setRotate(mapView: BaiduMapView, rotation: Float) {
        mapView.map.setMapStatus(MapStatusUpdateFactory.newMapStatus(
            MapStatus.Builder().rotate(rotation).build()
        ))
    }

    @ReactProp(name = "overlook")
    fun setOverlook(mapView: BaiduMapView, overlook: Float) {
        mapView.map.setMapStatus(MapStatusUpdateFactory.newMapStatus(
            MapStatus.Builder().overlook(overlook).build()
        ))
    }
}