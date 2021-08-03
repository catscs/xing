package es.webandroid.xing

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import androidx.test.runner.AndroidJUnitRunner
import com.karumi.shot.ShotTestRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner: ShotTestRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
