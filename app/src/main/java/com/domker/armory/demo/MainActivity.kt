package com.domker.armory.demo

import android.os.Bundle
import android.os.Process
import android.support.v7.app.AppCompatActivity
import com.domker.armory.framework.DexPath
import com.domker.armory.log.Logger.log
import com.domker.armory.log.PerformanceLogger
import com.domker.armory.util.FileUtils.readFile
import com.domker.armory.view.ViewPrinter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.image_sub_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PerformanceLogger.get("on_create").start()
        // Example of a call to a native method
//        sample_text.text = NativeProxy.stringFromJNI()
        ViewPrinter.printViewTree(window.decorView)
        ViewPrinter.printViewPath(sample_text)

        val start2 = System.currentTimeMillis()
        PerformanceLogger.get("on_create").end()
        imageSubView.inflate()
        imageView.setOnClickListener {
            PerformanceLogger.get("on_click").start()
            DexPath.printDexPathList(classLoader)
            sample_text.text = "compress done."
            PerformanceLogger.get("on_click").end().show()
        }
        val end = System.currentTimeMillis()
        println("inflate ${end - start2}")
        PerformanceLogger.showStatistic()

    }

    private fun compress() {
        val content = readFile("/proc/${Process.myPid()}/sched")
        log(content)

        Thread.getAllStackTraces().entries.forEach {
            try {
                val threadCpu = readFile("/proc/${Process.myPid()}/task/${it.key.id}/stat/")
                log(threadCpu)
            } catch (e: Exception) {

            }
        }
    }
}
