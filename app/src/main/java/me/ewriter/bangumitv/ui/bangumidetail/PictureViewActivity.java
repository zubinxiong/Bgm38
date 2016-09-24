package me.ewriter.bangumitv.ui.bangumidetail;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.event.SaveImageEvent;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Zubin on 2016/8/12.
 */
public class PictureViewActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TEXT = "image_text";

    private Toolbar mToolbar;
    private ImageView mPicture;
    private TextView mDetailText;
    private ViewGroup mPicGroup;

    String mImageUrl, mImageText;
    boolean isHidden = false;

    private static final int RC_EXTERNAL_PERM = 123;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_picture_view;
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPicture = (ImageView) findViewById(R.id.picture);
        mDetailText = (TextView) findViewById(R.id.picture_detail);
        mPicGroup = (ViewGroup) findViewById(R.id.picture_group);

        setupToolbar();
        mPicGroup.setOnClickListener(this);

        if (!TextUtils.isEmpty(mImageUrl)) {
            Picasso.with(this).load(mImageUrl).
                    error(R.drawable.img_on_error).
                    into(mPicture);
        } else {
            Picasso.with(this).load(R.drawable.img_on_error).into(mPicture);
        }

        if (!TextUtils.isEmpty(mImageText)) {
            mDetailText.setText(mImageText);
        }
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.shadow_color));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveToPhone();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @AfterPermissionGranted(RC_EXTERNAL_PERM)
    private void saveToPhone() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (TextUtils.isEmpty(mImageUrl)) {
                ToastUtils.showShortToast(this, R.string.save_disable);
            } else {
                String name;
                if (TextUtils.isEmpty(mImageText)) {
                    name = System.currentTimeMillis() + ".jpg";
                } else {
                    name = mImageText.replace("\n", ",") + ".jpg";
                }
                EventBus.getDefault().post(new SaveImageEvent(mImageUrl, name));
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_retry_title),
                    RC_EXTERNAL_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSaveImg(SaveImageEvent event) {
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.with(this).load(event.getmImageUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            return;
        }

        File appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!appDir.exists())
            appDir.mkdirs();

        File file = new File(appDir, event.getName());
        if (file.exists()) {
            ToastUtils.showShortToast(PictureViewActivity.this, R.string.image_already_exist);
            return;
        } else {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                ToastUtils.showShortToast(PictureViewActivity.this, R.string.save_ok);
                // 其次把文件插入到系统图库
                MediaStore.Images.Media.insertImage(PictureViewActivity.this.getContentResolver(),
                        file.getAbsolutePath(), event.getName(), null);

                // 最后通知图库更新
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + file.getAbsolutePath()));

                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.d(LogUtil.ZUBIN, e.getMessage());
                ToastUtils.showShortToast(PictureViewActivity.this, R.string.save_failed);
            }
        }

    }

    @Override
    protected void initBeforeCreate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Fade());
        }
        mImageUrl = getIntent().getStringExtra(PictureViewActivity.EXTRA_IMAGE_URL);
        mImageText = getIntent().getStringExtra(PictureViewActivity.EXTRA_IMAGE_TEXT);
    }

    @Override
    protected boolean isSubscribeEvent() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture_group:
                hideOrShowMask();
                break;

            default:
                break;
        }
    }

    private void hideOrShowMask() {
        mToolbar.animate()
                .translationY(isHidden ? 0 : -mToolbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(3))
                .start();

        mDetailText.animate()
                .translationY(isHidden? 0 : +mDetailText.getHeight())
                .setInterpolator(new DecelerateInterpolator(3))
                .start();

        isHidden = !isHidden;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtil.d(LogUtil.ZUBIN, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtil.d(LogUtil.ZUBIN, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied permissions and checked NEVER ASK AGAIN.
        // This will display a dialog directing them to enable the permission in app settings.
        ToastUtils.showShortToast(PictureViewActivity.this, R.string.save_failed);
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.permission_retry_title),
                R.string.permission_go, R.string.cancel, null, perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
