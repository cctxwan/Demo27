package com.cc.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.R;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.utils.ThreadManager;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

public class ShareListActivity extends BaseActivity implements View.OnClickListener {

    //QQ
    public static String QQ_APP_ID = "101496221";
    public static String QQ_APP_Key = "9689fd47f2d4443b9ab685dfb2f43220";

    //WX
    private final String W_APPID = "wxdaf01e3da90f40a9";

    Activity activity = ShareListActivity.this;

    ImageView tvFriend, tvTimeline, tvQrcode, tvCopylink;

    TextView tvCode = null;

    int screenWidth = 0;
    ViewGroup rootView;
    static final int ANIM_TIME = 500;
    int mExtarFlag = 0x00;
    OvershootInterpolator overshootInterpolator = new OvershootInterpolator();

    //URL
    public static String URL = "";
    //title
    public static String TITLE = "";
    //IMG
    public static String IMG = "";

    public static Tencent mTencent;
    int shareType = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_share_list;
    }

    @Override
    public void initData() {
        screenWidth = getScreenWidth(activity);

        Intent intent = getIntent();
        if(intent == null) return;
        URL = intent.getStringExtra("url");
        TITLE = intent.getStringExtra("title");
        IMG = intent.getStringExtra("img");
    }

    @Override
    public void initView() {
        tvFriend = findViewById(R.id.wxFriend);
        tvTimeline = findViewById(R.id.wxTimeline);
        tvQrcode = findViewById(R.id.qrcode);
        tvCopylink = findViewById(R.id.copyLink);


        tvFriend.setOnClickListener(this);
        tvTimeline.setOnClickListener(this);
        tvQrcode.setOnClickListener(this);
        tvCopylink.setOnClickListener(this);


        findViewById(R.id.parent).setOnClickListener(this);
        findViewById(R.id.qqFriend).setOnClickListener(this);
        findViewById(R.id.qzone).setOnClickListener(this);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        tvFriend.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tvFriend.getViewTreeObserver().removeOnPreDrawListener(this);
                tvFriend.setTranslationX(-screenWidth / 2);
                tvFriend.setTranslationY(-tvFriend.getHeight() * 2);
                return false;
            }
        });
        tvTimeline.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tvTimeline.getViewTreeObserver().removeOnPreDrawListener(this);
                tvTimeline.setTranslationX(screenWidth / 2);
                tvTimeline.setTranslationY(-tvFriend.getHeight() * 2);
                return false;
            }
        });
        tvQrcode.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tvQrcode.getViewTreeObserver().removeOnPreDrawListener(this);
                tvQrcode.setTranslationX(-screenWidth / 2);
                tvQrcode.setTranslationY(tvFriend.getHeight() * 2);
                return false;
            }
        });
        tvCopylink.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tvCopylink.getViewTreeObserver().removeOnPreDrawListener(this);
                tvCopylink.setTranslationX(screenWidth / 2);
                tvCopylink.setTranslationY(tvFriend.getHeight() * 2);
                return false;
            }
        });

        tvFriend.post(new Runnable() {
            @Override
            public void run() {
                moveInAnim(false);
            }
        });
    }

    /**
     * 获取宽度
     * @param c
     * @return
     */
    private int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }
        return screenWidth;
    }

    private void moveInAnim(boolean isHideCode) {
        ObjectAnimator friendAnimatorX = ObjectAnimator.ofFloat(tvFriend, "TranslationX", 0);
        ObjectAnimator friendAnimatorY = ObjectAnimator.ofFloat(tvFriend, "TranslationY", 0);
        ObjectAnimator timelineAnimatorX = ObjectAnimator.ofFloat(tvTimeline, "TranslationX", 0);
        ObjectAnimator timelineAnimatorY = ObjectAnimator.ofFloat(tvTimeline, "TranslationY", 0);
        ObjectAnimator qrcodeAnimatorX = ObjectAnimator.ofFloat(tvQrcode, "TranslationX", 0);
        ObjectAnimator qrcodeAnimatorY = ObjectAnimator.ofFloat(tvQrcode, "TranslationY", 0);
        ObjectAnimator copyAnimatorX = ObjectAnimator.ofFloat(tvCopylink, "TranslationX", 0);
        ObjectAnimator copyAnimatorY = ObjectAnimator.ofFloat(tvCopylink, "TranslationY", 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME);

        if (isHideCode) {
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(tvCode, "ScaleX", 0.1f);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(tvCode, "ScaleY", 0.1f);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvCode.setVisibility(View.INVISIBLE);
                }
            });
            set.playTogether(friendAnimatorX, friendAnimatorY, timelineAnimatorX, timelineAnimatorY
                    , qrcodeAnimatorX, qrcodeAnimatorY, copyAnimatorX, copyAnimatorY, animatorX, animatorY);
        } else {
            set.setInterpolator(new FastOutSlowInInterpolator());
            set.playTogether(friendAnimatorX, friendAnimatorY, timelineAnimatorX, timelineAnimatorY
                    , qrcodeAnimatorX, qrcodeAnimatorY, copyAnimatorX, copyAnimatorY);
        }

        set.start();
    }

    private void addQrcode() {
        if (tvCode != null) {
            tvCode.setVisibility(View.VISIBLE);
            return;
        }
        tvCode = new TextView(activity);
        tvCode.setBackgroundColor(Color.WHITE);
        tvCode.setGravity(Gravity.CENTER_HORIZONTAL);
        tvCode.setText("请扫描二维码分享");
        tvCode.setPadding(80, 40, 80, 40);
        tvCode.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.icon_scan);
        tvCode.setCompoundDrawablePadding(20);
        tvCode.setTextColor(Color.BLACK);
        tvCode.setTextSize(20);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        tvCode.setScaleX(0.1f);
        tvCode.setScaleY(0.1f);
        tvCode.setId(R.id.tvScan);
        tvCode.setOnClickListener(this);
        rootView.addView(tvCode, params);
        tvCode.setOnClickListener(this);
    }

    private void moveOutAnim(boolean isFinishActivity, boolean isShowCode) {
        ObjectAnimator friendAnimatorX = ObjectAnimator.ofFloat(tvFriend, "TranslationX", -screenWidth / 2);
        ObjectAnimator friendAnimatorY = ObjectAnimator.ofFloat(tvFriend, "TranslationY", -tvFriend.getHeight() * 2);
        ObjectAnimator timelineAnimatorX = ObjectAnimator.ofFloat(tvTimeline, "TranslationX", screenWidth / 2);
        ObjectAnimator timelineAnimatorY = ObjectAnimator.ofFloat(tvTimeline, "TranslationY", -tvFriend.getHeight() * 2);
        ObjectAnimator qrcodeAnimatorX = ObjectAnimator.ofFloat(tvQrcode, "TranslationX", -screenWidth / 2);
        ObjectAnimator qrcodeAnimatorY = ObjectAnimator.ofFloat(tvQrcode, "TranslationY", tvFriend.getHeight() * 2);
        ObjectAnimator copyAnimatorX = ObjectAnimator.ofFloat(tvCopylink, "TranslationX", screenWidth / 2);
        ObjectAnimator copyAnimatorY = ObjectAnimator.ofFloat(tvCopylink, "TranslationY", tvFriend.getHeight() * 2);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME);

        if (isShowCode) {
            addQrcode();
            ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(tvCode, "ScaleX", 1f);
            ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(tvCode, "ScaleY", 1f);
            animatorScaleX.setInterpolator(overshootInterpolator);
            animatorScaleY.setInterpolator(overshootInterpolator);
            set.playTogether(friendAnimatorX, friendAnimatorY, timelineAnimatorX, timelineAnimatorY
                    , qrcodeAnimatorX, qrcodeAnimatorY, copyAnimatorX, copyAnimatorY, animatorScaleX, animatorScaleY);
        } else {
            set.playTogether(friendAnimatorX, friendAnimatorY, timelineAnimatorX, timelineAnimatorY
                    , qrcodeAnimatorX, qrcodeAnimatorY, copyAnimatorX, copyAnimatorY);
        }

        if (isFinishActivity) {
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ActivityMan.getmInstance().finishActivity(activity);
                    overridePendingTransition(0, 0);
                }
            });
        }

        set.start();
    }

    /**
     * 复制
     */
    private void copyToClipBoard() {
        ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", "http://daijiushi.com/");
        myClipboard.setPrimaryClip(clip);
        Toast.makeText(activity, "分享链接已复制到剪切板", Toast.LENGTH_SHORT).show();
    }

    private void back() {
        if (tvCode != null && tvCode.isShown()) {
            moveInAnim(true);
            return;
        }
        moveOutAnim(true, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wxFriend:
                //发送给朋友
                wxShare(0);
                break;
            case R.id.wxTimeline:
                //分享到朋友圈
                wxShare(1);
                break;
            case R.id.qrcode:
                //二维码分享
                moveOutAnim(false, true);
                break;
            case R.id.copyLink:
                //复制链接
                copyToClipBoard();
                break;
            case R.id.parent:
                //返回
                back();
                break;
            case R.id.tvScan:
                //二维码点击
                moveInAnim(true);
                break;
            case R.id.qqFriend:
                //发送给QQ朋友
                qqFriend();
                break;
            case R.id.qzone:
                //分享到QQ空间
                qzone();
                break;
            default:
                break;
        }
    }

    /**
     * 分享到QQ空间
     */
    private void qzone() {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, TITLE);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, URL);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, URL);
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,imgUrlList);// 图片地址
        if (shareType == QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT) {
            doShareToQzone(params);
        }
    }

    /**
     * 用异步方式启动分享
     * @param params
     */
    private void doShareToQzone(final Bundle params) {
        mTencent = Tencent.createInstance(QQ_APP_ID, this);
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQzone(activity, params, qZoneShareListener);
                }
            }
        });
    }

    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            Toast.makeText(activity, R.string.qq_qzone_cancel, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError e) {
            A.C_Log(activity, "" + e.errorMessage);
            Toast.makeText(activity, R.string.qq_qzone_error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object response) {
            Toast.makeText(activity, R.string.qq_qzone_succ, Toast.LENGTH_SHORT).show();
            ActivityMan.getmInstance().finishActivity(activity);
        }

    };

    /**
     * 发送给QQ朋友
     */
    private void qqFriend() {
        final Bundle params = new Bundle();
        if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            //
            params.putString(QQShare.SHARE_TO_QQ_TITLE, TITLE);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, URL);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, URL);
        }
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, IMG);
        params.putString(shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE ? QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL
                : QQShare.SHARE_TO_QQ_IMAGE_URL, IMG);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getPackageName());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag);

        doShareToQQ(params);
        return;
    }

    private void doShareToQQ(final Bundle params) {
        mTencent = Tencent.createInstance(QQ_APP_ID, this);
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(activity, params, qqShareListener);
                }
            }
        });
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
                Toast.makeText(activity, R.string.qqshare_cancel, Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onComplete(Object response) {
            Toast.makeText(activity, R.string.qqshare_succ, Toast.LENGTH_SHORT).show();
            ActivityMan.getmInstance().finishActivity(activity);
        }
        @Override
        public void onError(UiError e) {
            Toast.makeText(activity, R.string.qqshare_error, Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * 分享
     * @param flag 0：分享给朋友   1：朋友圈
     */
    private void wxShare(int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(this, W_APPID);
        api.registerApp(W_APPID);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(activity, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.mojichina.com/";
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "给明桑的测试";
        msg.description = "国服第一帅你寇哥，高端大气上档次，快来看看吧！";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.default_image);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;

        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        api.sendReq(req);
    }

    /**
     * 回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        }else if (requestCode == Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode,resultCode,data,qZoneShareListener);
        }
    }

}
