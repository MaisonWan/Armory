package com.domker.armory.log;

import android.util.Log;
import android.util.SparseArray;

import com.domker.armory.BuildConfig;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wanlipeng on 2019/4/8 5:29 PM
 */
public class PerformanceLogger {
    private static final String LOG_TAG = "PerformanceTag";
    private static final boolean ENABLE = BuildConfig.DEBUG;

    private static DecimalFormat sMillisFormatter = new DecimalFormat("###.###ms");
    private static Map<String, PerformanceLogger> sPool = new HashMap<>();

    private String mName;

    public PerformanceLogger(String name) {
        mName = name;
    }

    private SparseArray<Long> mStartRecord = new SparseArray<>();
    private SparseArray<Long> mEndRecord = new SparseArray<>();
    private AtomicInteger mCounter = new AtomicInteger();
    private long mErrBase = Long.MAX_VALUE;
    private long mWarnBase = mErrBase;

    public PerformanceLogger start() {
        if (ENABLE) {
            mStartRecord.append(mCounter.incrementAndGet(), System.nanoTime());
        }
        return this;
    }

    public PerformanceLogger end() {
        if (ENABLE) {
            mEndRecord.append(mCounter.get(), System.nanoTime());
        }
        return this;
    }

    public PerformanceLogger wran(long millis) {
        if (ENABLE) {
            mWarnBase = TimeUnit.MILLISECONDS.toNanos(millis);
        }
        return this;
    }

    public PerformanceLogger err(long millis) {
        if (ENABLE) {
            mErrBase = TimeUnit.MILLISECONDS.toNanos(millis);
        }
        return this;
    }

    public PerformanceLogger show() {
        if (ENABLE) {
            int size = mEndRecord.size();
            if (size == 0) {
                return this;
            }

            if (size == 1) {
                long duration = getDuration(0);
                Log.println(level(duration), LOG_TAG, mName + " 耗时：" + millis(duration));
                return this;
            }

            long total = 0;
            long average;
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                long it = getDuration(i);
                max = Math.max(max, it);
                min = Math.min(min, it);
                total += it;
            }
            average = total / size;
            String log = String.format(Locale.getDefault(), "%s 总耗时：%s, 执行次数：%d, 最多耗时：%s, 最少耗时：%s, 平均耗时：%s",
                    mName, millis(total), size, millis(max), millis(min), millis(average));
            Log.println(level(average), LOG_TAG, log);
            return this;
        }
        return this;
    }

    public PerformanceLogger reset() {
        if (ENABLE) {
            mStartRecord.clear();
            mEndRecord.clear();
            mErrBase = Long.MAX_VALUE;
            mWarnBase = mErrBase;
        }
        return this;
    }

    private long getDuration(int index) {
        return mEndRecord.valueAt(index) - mStartRecord.get(mEndRecord.keyAt(index));
    }

    private int level(long duration) {
        if (duration > mErrBase) {
            return Log.ERROR;
        } else if (duration > mWarnBase) {
            return Log.WARN;
        }
        return Log.DEBUG;
    }

    public static PerformanceLogger get(String id) {
        PerformanceLogger duration = sPool.get(id);
        if (duration == null) {
            duration = new PerformanceLogger(id);
            sPool.put(id, duration);
        }
        return duration;
    }

    public static void showStatistic() {
        for (PerformanceLogger performanceLogger : sPool.values()) {
            performanceLogger.show();
        }
    }

    public static void clear() {
        sPool.clear();
    }

    private static String millis(long nanoTime) {
        return sMillisFormatter.format(nanoTime * 0.000001);
    }
}
