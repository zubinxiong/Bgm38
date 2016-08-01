package me.ewriter.bangumitv.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.utils.BlurUtil;

/**
 * Created by Zubin on 2016/8/1.
 * 番剧详情页
 */
public class BangumiDetailActivity extends BaseActivity {

    int mBangumiId;
    String mCommonImageUrl;
    String mLargeImageUrl;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bangumi_detail;
    }

    @Override
    protected void initViews() {
        ImageView test = (ImageView) findViewById(R.id.fengmian);
        final ViewGroup testGroup = (ViewGroup) findViewById(R.id.group);

        Picasso.with(this).load(mLargeImageUrl).into(test);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = BlurUtil.blur(BangumiDetailActivity.this, Picasso.with(BangumiDetailActivity.this).load(mCommonImageUrl).get());

                    BangumiDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testGroup.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void initIntent() {
        if (getIntent() != null) {
            mBangumiId = getIntent().getIntExtra("bangumiId", 0);
            mCommonImageUrl = getIntent().getStringExtra("common_url");
            mLargeImageUrl = getIntent().getStringExtra("large_url");
        }
    }

    @Override
    protected boolean isSubscribeEvent() {
        return false;
    }
}
