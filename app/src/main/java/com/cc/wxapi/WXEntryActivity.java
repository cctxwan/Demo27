package com.cc.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.cc.utils.A;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * WX
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private final String APP_ID= "wxdaf01e3da90f40a9";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }

    //微信发送消息给app，app接受并处理的回调函数
    @Override
    public void onReq(BaseReq req) {

    }

    //app发送消息给微信，微信返回的消息回调函数,根据不同的返回码来判断操作是否成功
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        A.C_Log(this, "resp=" + resp);

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                A.C_Log(this, "SUCC");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                A.C_Log(this, "CANCEL");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                A.C_Log(this, "DENIED");
                break;
            default:
                A.C_Log(this, "default");
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//        finish();
    }

}