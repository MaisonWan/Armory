package com.domker.armory.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.domker.armory.framework.DexPath
import com.domker.armory.view.ViewPrinter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
        ViewPrinter.printViewTree(window.decorView)
        ViewPrinter.printViewPath(sample_text)
        DexPath.printDexPathList(classLoader)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}