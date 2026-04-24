package com.pharmai.core.di

import android.content.Context
import com.pharmai.features.scanner.ml.BarcodeScanner
import com.pharmai.features.scanner.ml.ExpiryDateDetector
import com.pharmai.features.scanner.ml.MedicineClassifier
import com.pharmai.features.scanner.ml.TFLiteEngine
import com.pharmai.features.scanner.ml.TextRecognizer
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
    ): TFLiteEngine {
        return TFLiteEngine(context)
    }

    @Provides
    @Singleton
    fun provideMedicineClassifier(
        @ApplicationContext context: Context
    ): MedicineClassifier {
        return MedicineClassifier(context)
    }

    @Provides
    @Singleton
    fun provideTextRecognizer(
        @ApplicationContext context: Context
    ): TextRecognizer {
        return TextRecognizer(context)
    }

    @Provides
    @Singleton
    fun provideExpiryDateDetector(
        textRecognizer: TextRecognizer
    ): ExpiryDateDetector {
        return ExpiryDateDetector(textRecognizer)
    }

    @Provides
    @Singleton
    fun provideBarcodeScanner(): BarcodeScanner {
        return BarcodeScanner()
    }
}