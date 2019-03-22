package com.xwc.smokerapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

import java.io.File

/**
 * Description：导航
 * author：Smoker
 * 2019/3/21 16:06
 */
object MapUtil {

    private const val PN_GAODE_MAP = "com.autonavi.minimap"// 高德地图包名
    private const val PN_BAIDU_MAP = "com.baidu.BaiduMap" // 百度地图包名
    private const val PN_TENCENT_MAP = "com.tencent.map" // 腾讯地图包名

    /**
     * 检查地图应用是否安装
     * @return
     */
    val isGdMapInstalled: Boolean
        get() = isInstallPackage(PN_GAODE_MAP)

    val isBaiduMapInstalled: Boolean
        get() = isInstallPackage(PN_BAIDU_MAP)

    val isTencentMapInstalled: Boolean
        get() = isInstallPackage(PN_TENCENT_MAP)

    private fun isInstallPackage(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }

    /**
     * 百度转高德
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    fun bdToGaoDe(bd_lat: Double, bd_lon: Double): DoubleArray {
        val gd_lat_lon = DoubleArray(2)
        val PI = 3.14159265358979324 * 3000.0 / 180.0
        val x = bd_lon - 0.0065
        val y = bd_lat - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI)
        gd_lat_lon[0] = z * Math.cos(theta)
        gd_lat_lon[1] = z * Math.sin(theta)
        return gd_lat_lon
    }

    /**
     * 高德、腾讯转百度
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    private fun gaoDeToBaidu(gd_lon: Double, gd_lat: Double): DoubleArray {
        val bd_lat_lon = DoubleArray(2)
        val PI = 3.14159265358979324 * 3000.0 / 180.0
        val z = Math.sqrt(gd_lon * gd_lon + gd_lat * gd_lat) + 0.00002 * Math.sin(gd_lat * PI)
        val theta = Math.atan2(gd_lat, gd_lon) + 0.000003 * Math.cos(gd_lon * PI)
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006
        return bd_lat_lon
    }

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     *
     * @param latLng
     * @returns
     *
     * 使用此方法需要下载导入百度地图的BaiduLBS_Android.jar包
     */
/*    fun BD09ToGCJ02(latLng: LatLng): LatLng {
        val x_pi = 3.14159265358979324 * 3000.0 / 180.0
        val x = latLng.longitude - 0.0065
        val y = latLng.latitude - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi)
        val gg_lat = z * Math.sin(theta)
        val gg_lng = z * Math.cos(theta)
        return LatLng(gg_lat, gg_lng)
    }*/

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     *
     * @param latLng
     * @returns
     *
     * 需要百度地图的BaiduLBS_Android.jar包
     */
/*    fun GCJ02ToBD09(latLng: LatLng): LatLng {
        val x_pi = 3.14159265358979324 * 3000.0 / 180.0
        val z = Math.sqrt(latLng.longitude * latLng.longitude + latLng.latitude * latLng.latitude) + 0.00002 * Math.sin(
            latLng.latitude * x_pi
        )
        val theta = Math.atan2(latLng.latitude, latLng.longitude) + 0.000003 * Math.cos(latLng.longitude * x_pi)
        val bd_lat = z * Math.sin(theta) + 0.006
        val bd_lng = z * Math.cos(theta) + 0.0065
        return LatLng(bd_lat, bd_lng)
    }*/

    /**
     * 打开高德地图导航功能
     * @param context
     * @param slat 起点纬度
     * @param slon 起点经度
     * @param sname 起点名称 可不填（0,0，null）
     * @param dlat 终点纬度
     * @param dlon 终点经度
     * @param dname 终点名称 必填
     */
    fun openGaoDeNavi(
        context: Context,
        slat: Double,
        slon: Double,
        sname: String?,
        dlat: Double,
        dlon: Double,
        dname: String
    ) {
        var uriString: String? = null
        val builder = StringBuilder("amapuri://route/plan?sourceApplication=maxuslife")
        if (slat != 0.0) {
            builder.append("&sname=").append(sname)
                .append("&slat=").append(slat)
                .append("&slon=").append(slon)
        }
        builder.append("&dlat=").append(dlat)
            .append("&dlon=").append(dlon)
            .append("&dname=").append(dname)
            .append("&dev=0")
            .append("&t=0")
        uriString = builder.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage(PN_GAODE_MAP)
        intent.data = Uri.parse(uriString)
        context.startActivity(intent)
    }

    /**
     * 打开腾讯地图
     * params 参考http://lbs.qq.com/uri_v1/guide-route.html
     *
     * @param context
     * @param slat 起点纬度
     * @param slon 起点经度
     * @param sname 起点名称 可不填（0,0，null）
     * @param dlat 终点纬度
     * @param dlon 终点经度
     * @param dname 终点名称 必填
     * 驾车：type=drive，policy有以下取值
     * 0：较快捷
     * 1：无高速
     * 2：距离
     * policy的取值缺省为0
     * &from=" + dqAddress + "&fromcoord=" + dqLatitude + "," + dqLongitude + "
     */
    fun openTencentMap(
        context: Context,
        slat: Double,
        slon: Double,
        sname: String?,
        dlat: Double,
        dlon: Double,
        dname: String
    ) {
        var uriString: String? = null
        val builder = StringBuilder("qqmap://map/routeplan?type=drive&policy=0&referer=zhongshuo")
        if (slat != 0.0) {
            builder.append("&from=").append(sname)
                .append("&fromcoord=").append(slat)
                .append(",")
                .append(slon)
        }
        builder.append("&to=").append(dname)
            .append("&tocoord=").append(dlat)
            .append(",")
            .append(dlon)
        uriString = builder.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage(PN_TENCENT_MAP)
        intent.data = Uri.parse(uriString)
        context.startActivity(intent)
    }

    /**
     * 打开百度地图导航功能(默认坐标点是高德地图，需要转换)
     * @param context
     * @param slat 起点纬度
     * @param slon 起点经度
     * @param sname 起点名称 可不填（0,0，null）
     * @param dlat 终点纬度
     * @param dlon 终点经度
     * @param dname 终点名称 必填
     */
    fun openBaiDuNavi(
        context: Context,
        slat: Double,
        slon: Double,
        sname: String?,
        dlat: Double,
        dlon: Double,
        dname: String
    ) {
        var slat = slat
        var slon = slon
        var dlat = dlat
        var dlon = dlon
        var uriString: String? = null
        //终点坐标转换
        //        此方法需要百度地图的BaiduLBS_Android.jar包
        //        LatLng destination = new LatLng(dlat,dlon);
        //        LatLng destinationLatLng = GCJ02ToBD09(destination);
        //        dlat = destinationLatLng.latitude;
        //        dlon = destinationLatLng.longitude;

        val destination = gaoDeToBaidu(dlat, dlon)
        dlat = destination[0]
        dlon = destination[1]

        val builder = StringBuilder("baidumap://map/direction?mode=driving&")
        if (slat != 0.0) {
            //起点坐标转换

            //            LatLng origin = new LatLng(slat,slon);
            //            LatLng originLatLng = GCJ02ToBD09(origin);
            //            slat = originLatLng.latitude;
            //            slon = originLatLng.longitude;

            val origin = gaoDeToBaidu(slat, slon)
            slat = origin[0]
            slon = origin[1]

            builder.append("origin=latlng:")
                .append(slat)
                .append(",")
                .append(slon)
                .append("|relationshipName:")
                .append(sname)
        }
        builder.append("&destination=latlng:")
            .append(dlat)
            .append(",")
            .append(dlon)
            .append("|relationshipName:")
            .append(dname)
        uriString = builder.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage(PN_BAIDU_MAP)
        intent.data = Uri.parse(uriString)
        context.startActivity(intent)
    }
}