package com.pharmai.core.di

import android.content.Context
import com.pharmai.ml.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MLModule {
    @Provides @Singleton fun provideTFLiteEngine(@ApplicationContext ctx: Context) = TFLiteEngine(ctx)
    @Provides @Singleton fun provideMedicineClassifier(@ApplicationContext ctx: Context) = MedicineClassifier(ctx)
    @Provides @Singleton fun provideTextRecognizer(@ApplicationContext ctx: Context) = TextRecognizer(ctx)
    @Provides @Singleton fun provideExpiryDetector(textRecognizer: TextRecognizer) = ExpiryDateDetector(textRecognizer)
    @Provides @Singleton fun provideBarcodeScanner() = BarcodeScanner()
}