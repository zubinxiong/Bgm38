package me.ewriter.bangumitv.ui.picture;

import android.Manifest;
import android.os.Build;
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

import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/29.
 */

public class PictureViewActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TEXT = "image_text";

    String mImageUrl, mImageText;
    boolean isHidden = false;

    private Toolbar mToolbar;
    private ImageView mPicture;
    private TextView mDetailText;
    private ViewGroup mPicGroup;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_picture_view;
    }

    @Override
    protected void initView() {
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
//                requestWES();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
    protected void initBefore() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Fade());
        }
        mImageUrl = getIntent().getStringExtra(PictureViewActivity.EXTRA_IMAGE_URL);
        mImageText = getIntent().getStringExtra(PictureViewActivity.EXTRA_IMAGE_TEXT);
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
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mDetailText.animate()
                .translationY(isHidden? 0 : +mDetailText.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        isHidden = !isHidden;
    }
}
