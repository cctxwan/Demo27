package com.cc.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.cc.R;
import com.cc.activity.ChangePSDActivity;
import com.cc.activity.FontChangeActivity;
import com.cc.activity.FootBallActivity;
import com.cc.activity.HisTodayActivity;
import com.cc.activity.HomePageActivity;
import com.cc.activity.HoroscopeActivity;
import com.cc.activity.JokesActivity;
import com.cc.activity.LoginActivity;
import com.cc.activity.NBAActivity;
import com.cc.activity.QQHMCJXActivity;
import com.cc.activity.SmallSelActivity;
import com.cc.activity.StandardCodeActivity;
import com.cc.activity.WXFeaturedActivity;
import com.cc.activity.XHDictionaryActivity;
import com.cc.activity.ZGDreamActivity;
import com.cc.adapter.FourGVAdapter;
import com.cc.adapter.FourLVAdapter;
import com.cc.db.SQLiteDB;
import com.cc.dialog.CommomDialog;
import com.cc.model.FourGridViewInfo;
import com.cc.model.FourListViewInfo;
import com.cc.model.User_Account_Info;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.Helper;
import com.cc.utils.RoundImageView;
import com.cc.utils.StatusBarUtil;
import com.cc.view.MyGridView;
import com.cc.view.MyListView;
import com.cc.view.MyScrollView;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 第四个fragment
 */
public class FourFragment extends Fragment implements View.OnClickListener {

    private boolean isHide = false;

    View view;

    RoundImageView img;

    ImageView myself_bg;

    private Bitmap head;// 头像Bitmap

    private static String path = "/sdcard/myHead/";// sd路径

    Uri imageUri;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    Handler handler2 = new Handler();
    Handler handler3 = new Handler();

    TextView txt_useraccount_id, txt_title;

    MyGridView four_gv;

    List<FourGridViewInfo> datas = new ArrayList<>();
    List<FourListViewInfo> lvDatas = new ArrayList<>();

    FourGVAdapter fourGVAdapter;

    FourLVAdapter fourLVAdapter;

    int imageHeight;

    MyScrollView mySV;

    MyListView four_lv;

    FourLVAdapter fourListView;

    List<User_Account_Info> users = null;

    private int mOffset = 0;
    private int mScrollY = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_four, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //标题栏
        final Toolbar frag_four_toolbar = view.findViewById(R.id.frag_four_toolbar);
        //下拉的背景图
        final View frag_four_parallax = view.findViewById(R.id.frag_four_parallax);
        //
        final View frag_four_bt_BarLayout = view.findViewById(R.id.frag_four_bt_BarLayout);

        final NestedScrollView frag_four_scrollView = view.findViewById(R.id.frag_four_scrollView);

        /**
         * hd
         */
        frag_four_scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(getActivity(), R.color.cyan)&0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    frag_four_bt_BarLayout.setAlpha(1f * mScrollY / h);
                    frag_four_toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    frag_four_parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
        //状态栏透明和间距处理
        StatusBarUtil.immersive(getActivity());
        StatusBarUtil.setPaddingSmart(getActivity(), frag_four_toolbar);
        frag_four_bt_BarLayout.setAlpha(0);
        frag_four_toolbar.setBackgroundColor(0);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        initView();
//        initListeners();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(users != null) return;
        users = SQLiteDB.getInstance(getActivity()).getUsers();
        A.C_Log(getActivity(), users.size()+"");
        txt_useraccount_id.setText(users.size() == 0
                ? getActivity().getResources().getString(R.string.notlogin)
                : users.get(0).getAccountid());
        A.C_Log(getActivity(), "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        A.C_Log(getActivity(), "onStop()");
    }

    @Override
    public void onPause() {
        super.onPause();
        A.C_Log(getActivity(), "onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        A.C_Log(getActivity(), "onResume()");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.translucentStatusBar(getActivity(), isHide);
            isHide = !isHide;
        }
    }



    private void initData() {
        handler.post(islogin_Runnable);
        handler2.post(getLvData);
        handler3.post(getGvData);

        if(WXFeaturedActivity.add_data){

        }
    }

    Runnable getLvData = new Runnable() {
        @Override
        public void run() {
            FourListViewInfo lvinfo;
            lvinfo = new FourListViewInfo("1", R.mipmap.grzx, "个人主页", "1");
            lvDatas.add(lvinfo);
            lvinfo = new FourListViewInfo("2", R.mipmap.xgmm, "修改密码", "1");
            lvDatas.add(lvinfo);
            fourLVAdapter = new FourLVAdapter(getActivity(), lvDatas, four_lv);
            four_lv.setAdapter(fourLVAdapter);
            fourLVAdapter.setOnItemClickLiester(new FourLVAdapter.onItemClickLiester() {
                @Override
                public void succ(View view, int postion) {
                    if(!A.isUserLogin(getActivity())) return;
                    if(lvDatas.get(postion).getName().equals("修改密码")){
                        Intent intent = new Intent(getActivity(), ChangePSDActivity.class);
                        startActivity(intent);
                    } else if(lvDatas.get(postion).getName().equals("个人主页")){
                        Intent intent = new Intent(getActivity(), HomePageActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    };

    Runnable getGvData = new Runnable() {
        @Override
        public void run() {
            FourGridViewInfo info;
            info = new FourGridViewInfo("1", R.mipmap.nba, "NBA赛事", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("2", R.mipmap.zq, "足球联赛", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("3", R.mipmap.wxjx, "微信精选", "1", 1);
            datas.add(info);
            info = new FourGridViewInfo("4", R.mipmap.xhzd, "新华字典", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("5", R.mipmap.qqhmcjx, "QQ号码测吉凶", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("6", R.mipmap.bzdm, "标准电码查询", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("7", R.mipmap.xzys, "星座运势", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("5", R.mipmap.zgjm, "周公解梦", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("6", R.mipmap.fontchange, "字体转换", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("7", R.mipmap.xhdq, "笑话大全", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("5", R.mipmap.lssdjt, "历史上的今天", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("6", R.mipmap.sz, "小查询", "1", 0);
            datas.add(info);
            info = new FourGridViewInfo("7", R.mipmap.sz, "设置", "1", 0);
            datas.add(info);
            fourGVAdapter = new FourGVAdapter(getActivity(), datas, four_gv);
            four_gv.setAdapter(fourGVAdapter);
            fourGVAdapter.setOnItemClickLiester(new FourGVAdapter.onItemClickLiester() {
                @Override
                public void succ(View view, int postion) {
                    if(!A.isUserLogin(getActivity())) return;
                    A.C_Log(getActivity(), datas.get(postion).getName());
                    if(datas.get(postion).getName().equals("历史上的今天")){
                        Intent intent = new Intent(getActivity(), HisTodayActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("笑话大全")){
                        Intent intent = new Intent(getActivity(), JokesActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("微信精选")){
                        Intent intent = new Intent(getActivity(), WXFeaturedActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("QQ号码测吉凶")){
                        Intent intent = new Intent(getActivity(), QQHMCJXActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("NBA赛事")){
                        Intent intent = new Intent(getActivity(), NBAActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("足球联赛")){
                        Intent intent = new Intent(getActivity(), FootBallActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("新华字典")){
                        Intent intent = new Intent(getActivity(), XHDictionaryActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("标准电码查询")){
                        Intent intent = new Intent(getActivity(), StandardCodeActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("字体转换")){
                        Intent intent = new Intent(getActivity(), FontChangeActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("周公解梦")){
                        Intent intent = new Intent(getActivity(), ZGDreamActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("星座运势")){
                        Intent intent = new Intent(getActivity(), HoroscopeActivity.class);
                        startActivity(intent);
                    } else if (datas.get(postion).getName().equals("小查询")){
                        Intent intent = new Intent(getActivity(), SmallSelActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    };

    /**
     * 操作数据库，判断当前userid是否存在（登录）
     */
    Runnable islogin_Runnable = new Runnable() {
        @Override
        public void run() {
            users = SQLiteDB.getInstance(getActivity()).getUsers();
            A.C_Log(getActivity(), users.size() + "");
            txt_useraccount_id.setText(users.size() == 0
                    ? getActivity().getResources().getString(R.string.notlogin)
                    : users.get(0).getAccountid());
        }
    };

    /**
     * 初始化视图
     */
    private void initView() {
        myself_bg = view.findViewById(Helper.getResId(getActivity(), "myself_bg"));
        img = view.findViewById(Helper.getResId(getActivity(), "myself_bg_img"));
        txt_useraccount_id = view.findViewById(Helper.getResId(getActivity(), "txt_useraccount_id"));
        four_lv = view.findViewById(Helper.getResId(getActivity(), "frag_four_lv"));
        four_gv = view.findViewById(Helper.getResId(getActivity(), "frag_four_gv"));

        img.setOnClickListener(this);
        txt_useraccount_id.setOnClickListener(this);
        txt_useraccount_id.setText("admin");
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == Helper.getResId(getActivity(), "myself_bg_img")){
            changeImg();
        } else if( temdId == Helper.getResId(getActivity(), "txt_useraccount_id")){
            if(users.size() == 0){
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 切换圆形img
     */
    private void changeImg() {
        new CommomDialog(getActivity(), R.style.dialog, new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, String content) {
                if(content.equals("photo")){
                    dialog.dismiss();
                    A.C_Log(getActivity(), "photo");
                    photo();
                }else if(content.equals("album")){
                    dialog.dismiss();
                    A.C_Log(getActivity(), "album");
                    album();
                }
            }
        }, 1).show();
    }

    /**
     * 相册
     */
    private void album() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        //打开文件
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);
    }

    /**
     * 拍照
     */
    private void photo() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "tempImage" + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent2, 2);// 采用ForResult打开
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == getActivity().RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if(extras == null)return;
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        img.setImageBitmap(head);// 用ImageButton显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
