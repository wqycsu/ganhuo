package com.wqy.ganhuo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.model.ContentItem;

import java.util.List;

/**
 * Created by weiquanyun on 15/8/19.
 */
public class TestFastJSON {
    public static void main(String args[]) {

        String jsonStr = "{\"error\":false,\n" +
                "\"results\":[\n" +
                "{\"who\":\"Jason\",\"publishedAt\":\"2015-08-17T03:54:47.361Z\",\"desc\":\"短信开源App\",\"type\":\"Android\",\"url\":\"https://github.com/qklabs/qksms\",\"used\":true,\"objectId\":\"55d14ac760b2b750997bff84\",\"createdAt\":\"2015-08-17T02:45:27.361Z\",\"updatedAt\":\"2015-08-17T03:54:48.445Z\"},\n" +
                "{\"who\":\"Jason\",\"publishedAt\":\"2015-08-17T03:54:47.360Z\",\"desc\":\"Material Design样式的加载效果\",\"type\":\"Android\",\"url\":\"https://github.com/alokvnair/ProgressCircle\",\"used\":true,\"objectId\":\"55d147c500b0de09ab1ce436\",\"createdAt\":\"2015-08-17T02:32:37.725Z\",\"updatedAt\":\"2015-08-17T03:54:48.442Z\"},\n" +
                "{\"who\":\"有时放纵\",\"publishedAt\":\"2015-08-17T03:54:47.359Z\",\"desc\":\"一个Material Design 新手引导页View\",\"type\":\"Android\",\"url\":\"https://github.com/deano2390/MaterialShowcaseView\",\"used\":true,\"objectId\":\"55d13f4800b0de09f8b3e615\",\"createdAt\":\"2015-08-17T01:56:24.436Z\",\"updatedAt\":\"2015-08-17T03:54:48.459Z\"},\n" +
                "{\"who\":\"有时放纵\",\"publishedAt\":\"2015-08-17T03:54:47.357Z\",\"desc\":\"android UI 表单验证库\",\"type\":\"Android\",\"url\":\"https://github.com/ragunathjawahar/android-saripaar\",\"used\":true,\"objectId\":\"55d13efa00b0de09ab1bddf1\",\"createdAt\":\"2015-08-17T01:55:06.383Z\",\"updatedAt\":\"2015-08-17T03:54:48.463Z\"},\n" +
                "{\"who\":\"Jason\",\"publishedAt\":\"2015-08-14T03:56:36.788Z\",\"desc\":\"一个Android库监听网络连接状态，并与RxJava观测量的WiFi信号强度变化。\",\"type\":\"Android\",\"url\":\"https://github.com/pwittchen/ReactiveNetwork\",\"used\":true,\"objectId\":\"55cc52a700b01b7848c502aa\",\"createdAt\":\"2015-08-13T08:17:43.542Z\",\"updatedAt\":\"2015-08-15T03:15:54.345Z\"},{\"who\":\"Jason\",\"publishedAt\":\"2015-08-14T03:56:36.775Z\",\"desc\":\"一个好用的文字APP\",\"type\":\"Android\",\"url\":\"https://github.com/rizhilee/Beautyacticle\",\"used\":true,\"objectId\":\"55cc604760b2d1408c7b7356\",\"createdAt\":\"2015-08-13T09:15:51.494Z\",\"updatedAt\":\"2015-08-15T03:15:54.344Z\"},\n" +
                "{\"who\":\"有时放纵\",\"publishedAt\":\"2015-08-14T03:56:36.691Z\",\"desc\":\"一个简单的RecyclerView items动画库\",\"type\":\"Android\",\"url\":\"https://github.com/gabrielemariotti/RecyclerViewItemAnimators\",\"used\":true,\"objectId\":\"55cbff6760b215d66cdceeb1\",\"createdAt\":\"2015-08-13T02:22:31.396Z\",\"updatedAt\":\"2015-08-15T03:15:54.337Z\"},{\"who\":\"YJX\",\"publishedAt\":\"2015-08-14T03:56:36.687Z\",\"desc\":\"material design 风格的文件管理器\",\"type\":\"Android\",\"url\":\"https://github.com/arpitkh96/AmazeFileManager\",\"used\":true,\"objectId\":\"55cd502860b2b750991ff672\",\"createdAt\":\"2015-08-14T02:19:20.333Z\",\"updatedAt\":\"2015-08-15T03:15:54.344Z\"},{\"who\":\"mthli\",\"publishedAt\":\"2015-08-14T03:56:33.059Z\",\"desc\":\"有趣的环形滚动Progress\",\"type\":\"Android\",\"url\":\"https://github.com/Fichardu/CircleProgress\",\"used\":true,\"objectId\":\"55cc259e60b215d66ce4358f\",\"createdAt\":\"2015-08-13T05:05:34.090Z\",\"updatedAt\":\"2015-08-15T03:15:54.344Z\"},\n" +
                "{\"who\":\"有时放纵\",\"publishedAt\":\"2015-08-13T03:58:48.306Z\",\"desc\":\"一个漂亮的android tag group\",\"type\":\"Android\",\"url\":\"https://github.com/2dxgujun/AndroidTagGroup\",\"used\":true,\"objectId\":\"55cc00e360b2597462ad46de\",\"createdAt\":\"2015-08-13T02:28:51.599Z\",\"updatedAt\":\"2015-08-15T03:15:54.343Z\"}]}";

        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject(jsonStr);
        JSONObject resultObject = null;
        if(jsonObject.containsKey("results")){
            resultObject = (JSONObject)jsonObject.get("results");
        }
        if(resultObject!=null) {
            List<AndroidContentItem> list = JSON.parseArray(resultObject.toJSONString(), AndroidContentItem.class);
            for(int i=0;i<list.size();i++){
                System.out.println(list.get(i).getWho());
            }
        }
    }
}
