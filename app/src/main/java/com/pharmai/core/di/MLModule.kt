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

    @Provides
    @Singleton
    fun provideTFLiteEngine(
        @ApplicationContext context: Context
    ): TFLiteEngine = TFLiteEngine(context)

    @Provides
    @Singleton
    fun provideMedicineClassifier(
        @ApplicationContext context: Context
    ): MedicineClassifier = MedicineClassifier(context)

    @Provides
    @Singleton
    fun providePackageClassifier(
        @ApplicationContext context: Context
    ): PackageClassifier = PackageClassifier(context)

    @Provides
    @Singleton
    fun provideTextRecognizer(
        @ApplicationContext context: Context
    ): TextRecognizer = TextRecognizer(context)

    @Provides
    @Singleton
    fun provideExpiryDateDetector(
        textRecognizer: TextRecognizer
    ): ExpiryDateDetector = ExpiryDateDetector(textRecognizer)

    @Provides
    @Singleton
    fun provideBarcodeScanner(): BarcodeScanner = BarcodeScanner()
}