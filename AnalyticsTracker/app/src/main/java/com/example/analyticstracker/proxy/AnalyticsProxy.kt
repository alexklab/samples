package com.example.analyticstracker.proxy

import com.example.analyticstracker.AnalyticsService
import java.lang.reflect.Proxy

class AnalyticsProxy constructor(
    private val analyticsService: AnalyticsService,
    private val cached: Boolean
) {

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> create(clazz: Class<T>): T {
        return Proxy.newProxyInstance(
            clazz.classLoader,
            arrayOf(clazz),
            when {
                cached -> AnalyticsProxyInvocationHandlerWithCache(analyticsService)
                else -> AnalyticsProxyInvocationHandler(analyticsService)
            }
        ) as T
    }

    inline fun <reified T : Any> create(): T {
        return create(T::class.java)
    }

}