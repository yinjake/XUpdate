/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xupdatedemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.consts.PermissionConsts;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xupdatedemo.Constants;
import com.xuexiang.xupdatedemo.R;
import com.xuexiang.xupdatedemo.utils.HProgressDialogUtils;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.io.File;

/**
 * @author xuexiang
 * @since 2018/7/24 上午10:38
 */
public class UpdateActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    }

    @Permission(PermissionConsts.STORAGE)
    private void useApkDownLoadFunction() {
        XUpdate.newBuild(this)
                // 注意在Android10及以上存在存储权限问题，不建议设置在外部存储下载目录
                .apkCacheDir(PathUtils.getAppExtCachePath())
                .build()
                .download(Constants.XUPDATE_DEMO_DOWNLOAD_URL, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(UpdateActivity.this,"下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        ToastUtils.toast("apk下载完毕，文件路径：" + file.getPath());
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
//                XUpdate.newBuild(this)
//                        .updateUrl(Constants.DEFAULT_UPDATE_URL)
//                        .update();
                useApkDownLoadFunction();
                break;
//            case R.id.btn_support_background_update:
//                XUpdate.newBuild(this)
//                        .updateUrl(Constants.DEFAULT_UPDATE_URL)
//                        .promptWidthRatio(0.7F)
//                        .supportBackgroundUpdate(true)
//                        .update();
//                break;
//            case R.id.btn_auto_update:
//                XUpdate.newBuild(this)
//                        .updateUrl(Constants.DEFAULT_UPDATE_URL)
//                        //如果需要完全无人干预，自动更新，需要root权限【静默安装需要】
//                        .isAutoMode(true)
//                        .update();
//                break;
//            case R.id.btn_force_update:
//                XUpdate.newBuild(this)
//                        .updateUrl(Constants.FORCED_UPDATE_URL)
//                        .update();
//                break;
            default:
                break;
        }
    }
}
