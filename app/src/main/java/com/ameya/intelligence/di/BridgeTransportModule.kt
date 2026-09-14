package com.ameya.intelligence.di

import com.ameya.intelligence.impl.bridge.windows.tools.OpencodeBridgeTransport
import com.ameya.intelligence.impl.bridge.windows.tools.WindowsBridgeController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds the [OpencodeBridgeTransport] surface to the concrete controller.
 * Keeping this in its own module lets tests provide a fake transport without
 * depending on the full controller graph.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class BridgeTransportModule {

    @Binds
    @Singleton
    abstract fun bindOpencodeTransport(
        controller: WindowsBridgeController
    ): OpencodeBridgeTransport
}
