package com.xwc.smokerapp.ui.weight

import android.view.View
import com.amap.api.navi.INaviInfoCallback
import com.amap.api.navi.model.AMapNaviLocation

/**
 * Description：
 * author：Smoker
 * 2019/3/23 15:18
 */
class Navigation : INaviInfoCallback {
    override fun onGetNavigationText(p0: String?) {

    }

    override fun onCalculateRouteSuccess(p0: IntArray?) {
    }

    override fun onInitNaviFailure() {
    }

    override fun onStrategyChanged(p0: Int) {
    }

    override fun onReCalculateRoute(p0: Int) {
    }

    override fun getCustomNaviView(): View? {
        return null
    }

    override fun onCalculateRouteFailure(p0: Int) {
    }

    override fun onLocationChange(p0: AMapNaviLocation?) {
    }

    override fun getCustomNaviBottomView(): View? {
        return null
    }

    override fun onArrivedWayPoint(p0: Int) {
    }

    override fun onArriveDestination(p0: Boolean) {
    }

    override fun onStartNavi(p0: Int) {
    }

    override fun onStopSpeaking() {
    }

    override fun onExitPage(p0: Int) {
    }
}