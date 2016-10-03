package me.ewriter.bangumitv.ui.picture;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.utils.LogUtil;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/29.
 */

public class PicturePresenter implements PictureContract.Presenter {

    CompositeSubscription mSubscriptions;
    PictureContract.View mPictureView;

    public PicturePresenter(PictureContract.View mPictureView) {
        this.mPictureView = mPictureView;
        mPictureView.setPresenter(this);
    }

    @Override
    public void checkPermission(final String imageUrl, final String name) {
        Subscription subscription = RxPermissions
                .getInstance(BangumiApp.sAppCtx)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(LogUtil.ZUBIN, "request onError" + e.getMessage());
                        mPictureView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            downLoadImage(imageUrl, name);
                        } else {
                            mPictureView.showToast(BangumiApp.sAppCtx.getString(R.string.permission_retry_title));
                        }
                        LogUtil.d(LogUtil.ZUBIN, "request onNext = " + aBoolean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void downLoadImage(final String imageUrl, final String name) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                Bitmap bitmap = null;

                try {
                    bitmap = Picasso.with(BangumiApp.sAppCtx).load(imageUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

                File appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }

                File file = new File(appDir, name);
                if (file.exists()) {
                    subscriber.onError(new IllegalArgumentException("图像已经存在"));
                } else {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        // 其次把文件插入到系统图库
                        MediaStore.Images.Media.insertImage(BangumiApp.sAppCtx.getContentResolver(),
                                file.getAbsolutePath(), name, null);

                        // 最后通知图库更新
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.parse("file://" + file.getAbsolutePath()));

                        BangumiApp.sAppCtx.sendBroadcast(intent);

                        subscriber.onNext(null);
                        subscriber.onCompleted();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPictureView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        mPictureView.showToast(BangumiApp.sAppCtx.getString(R.string.save_ok));
                    }
                });
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
