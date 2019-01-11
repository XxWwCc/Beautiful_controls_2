package com.qcloud.myapplication.net

import com.qcloud.myapplication.beans.FruitBean
import retrofit2.http.POST
import retrofit2.http.QueryMap
import java.util.*
import io.reactivex.Observable

/**
 * Description：
 * author：Smoker
 * 2018/12/21 13:39
 */
interface ServiceApi {

    @POST("http://www.baidu.com")
    fun getFruitDetail(@QueryMap map: HashMap<String, Any>) : Observable<BaseResponse<FruitBean>>
}