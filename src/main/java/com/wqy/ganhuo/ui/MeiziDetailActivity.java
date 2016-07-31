package com.wqy.ganhuo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by weiquanyun on 16/7/3.
 */
public class MeiziDetailActivity extends BaseActivity {

    public static final String TRANSITION_NAME = "meizi_image";
    private static final String DATA_URL = "url";


    @Bind(R.id.meizi_image_detail)
    ImageView meiziDetailImg;

    private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        setupPhotoAttacher();
        setToolbarAlpha(0.7f);
        setToolbarTitle("妹子图");
        if(intent != null) {
            String url = intent.getStringExtra(DATA_URL);
            if (!TextUtils.isEmpty(url)) {
                Glide.with(this)
                        .load(url)
                        .dontAnimate()
                        .centerCrop()
                        .into(meiziDetailImg);
            }
        }
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, MeiziDetailActivity.class);
        intent.putExtra(DATA_URL, url);
        return intent;
    }

    private void setupPhotoAttacher() {
        photoViewAttacher = new PhotoViewAttacher(meiziDetailImg);
        photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolbar();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public int provideContentLayout() {
        return R.layout.activity_meizi_detail;
    }

    @Override
    public boolean canGoBack() {
        return true;
    }
}
