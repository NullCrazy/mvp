package com.xgl.libs.wapper;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * @Author: xingguo.lei@luckincoffee.com
 * @Date: 2019-08-05 14:35
 */
public class BuglyWrapper {

    public static void initBugly(Context context, String appId) {
        CrashReport.initCrashReport(context.getApplicationContext(),
                appId, false);
    }
}
