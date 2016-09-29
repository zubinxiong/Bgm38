package me.ewriter.bangumitv.ui.picture;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;

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
