package com.dkproject.basketballsns.ui.theme

import android.app.Application
import android.util.Log
import com.dkproject.presentation.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,BuildConfig.KAKAO_API_KEY)
    }
}