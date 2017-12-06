package com.mysql.qi_fu.librarymanage.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mysql.qi_fu.librarymanage.R;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static android.provider.UserDictionary.Words.APP_ID;

/**
 * Created by qi_fu on 2017/12/4.
 */

public class WeChatManager {
    private Context mContext;
    private IWXAPI wxapi;
    private static WeChatManager mInstance;
    private static final int THUMB_SIZE = 150;
    public static final int WECHAT_SHARE_WAY_TEXT = 1;   //文字
    public static final int WECHAT_SHARE_WAY_PICTURE = 2; //图片
    public static final int WECHAT_SHARE_WAY_WEBPAGE = 3;  //链接
    public static final int WECHAT_SHARE_WAY_VIDEO = 4; //视频
    public static final int WECHAT_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  //会话
    public static final int WECHAT_SHARE_TYPE_FRIENDS = SendMessageToWX.Req.WXSceneTimeline; //朋友圈
    private ShareContent mShareContentText, mShareContentPicture, mShareContentWebpag, mShareContentVideo;

    public WeChatManager(Context context) {

        this.mContext = context;
        initWxApi();
    }

    private void initWxApi() {
        wxapi = WXAPIFactory.createWXAPI(mContext, APP_ID, true);
        wxapi.registerApp(APP_ID);
    }
    public void loginWX(){
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo"; //授权域，snsapi_userinfo 表示获取用户个人信息
        req.state = "wechat_sdk_demo_test";
        wxapi.sendReq(req);
    }

//    /**
//     * 通过微信分享
//     *
//     * @param shareContent 分享的方式（文本、图片、链接）
//     * @param shareType    分享的类型（朋友圈，会话）
//     */
//    public void shareByWebchat(ShareContent shareContent, int shareType) {
//        switch (shareContent.getShareWay()) {
//            case WECHAT_SHARE_WAY_TEXT:
//                shareText(shareContent, shareType);
//                break;
//            case WECHAT_SHARE_WAY_PICTURE:
//                sharePicture(shareContent, shareType);
//                break;
//            case WECHAT_SHARE_WAY_WEBPAGE:
//                shareWebPage(shareContent, shareType);
//                break;
//            case WECHAT_SHARE_WAY_VIDEO:
//                shareVideo(shareContent, shareType);
//                break;
//        }
//    }

    private abstract class ShareContent {
        protected abstract int getShareWay();

        protected abstract String getContent();

        protected abstract String getTitle();

        protected abstract String getURL();

        protected abstract byte[] getPictureResource();
    }

    /**
     * 设置分享链接的内容
     *
     * @author chengcj1
     */
    public class ShareContentWebpage extends ShareContent {
        private String title;
        private String content;
        private String url;
        private byte[] pictureResource;

        public ShareContentWebpage(String title, String content, String url, byte[] pictureResource) {
            this.title = title;
            this.content = content;
            this.url = url;
            this.pictureResource = pictureResource;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_WEBPAGE;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return title;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected byte[] getPictureResource() {
            return pictureResource;
        }
    }

    /*
     * 获取网页分享对象
     */
    public ShareContent getShareContentWebpag(String title, String content, String url, byte[] pictureResource) {
        if (mShareContentWebpag == null) {
            mShareContentWebpag = new ShareContentWebpage(title, content, url, pictureResource);
        }
        return (ShareContentWebpage) mShareContentWebpag;
    }

//    /*
//    * 分享链接
//    */
//    private void shareWebPage(ShareContent shareContent, int shareType) {
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = shareContent.getURL();
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = shareContent.getTitle();
//        msg.description = shareContent.getTitle();
//
//        if (shareContent.getPictureResource() != null) {
//            msg.thumbData = shareContent.getPictureResource();
//
//
//        } else {
//            Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//            Bitmap thumbBitmap = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
//            thumb.recycle();
//            msg.thumbData = bitmapToByteArray(thumbBitmap);
//        }
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = shareType;
//        wxapi.sendReq(req);
//    }
}

