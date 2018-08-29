package com.taotao.tao_oat.wedget;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.taotao.tao_oat.R;
import com.taotao.tao_oat_library.weight.MToast;


/**
 * @package com.guotaijun.gtj.guotaijun.wedget
 * @file MyVoiceButton
 * @date 2018/8/12  上午8:46
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class MyVoiceButton extends AppCompatButton {

    private Context mContext;
    public boolean isStartListener = false;
    public boolean isCanListener = false;
    private SpeechUtility mUtility;
    private SpeechSynthesizer mSynthesizer;
    private StringBuffer speakSb = new StringBuffer();

    private int maxLeng = 256;

    //语音播放的其实位置；
    private int start = 0;
    private int end = 0;
    private com.iflytek.cloud.SpeechSynthesizer mSynthesizerNew;
    private SpeechUtility mUtilitySpeaker;

    public MyVoiceButton(Context context) {
        super(context);
        init();
    }

    public MyVoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyVoiceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mContext = getContext();

    }

    SpeechRecognizer recognizer = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("voice", "MotionEvent.ACTION_DOWN   ");

                recognizer = voiceInit();


                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("voice", "MotionEvent.ACTION_MOVE   ");

                break;
            case MotionEvent.ACTION_UP:
                Log.e("voice", "MotionEvent.ACTION_UP   ");

                if (recognizer != null) {
                    MToast.showToast(mContext, "语音录入结束");
                    recognizer.stopListening();
                }
                break;

        }


        return super.onTouchEvent(event);
    }

    private SpeechRecognizer voiceInit() {
        /**
         * 获取权限
         */

        isCanListener = mPermissionsMark.getPermissionsMark();


        if (!isStartListener)
            return null;
        if (!isCanListener)
            return null;


        //语音识别  旧版
        if (SpeechUtility.getUtility() == null) {
            StringBuffer paramOld = new StringBuffer();
            paramOld.append(SpeechConstant.APPID + "=pc20onli");
            paramOld.append(",");
            SpeechUtility.createUtility(getContext(), paramOld.toString());

        }


        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer(getContext(), mInitListener);
        setParameter(recognizer);
        // 不显示听写对话框
        recognizer.cancel();
        recognizer.stopListening();
        int ret = recognizer.startListening(mRecognizerListener);


        if (mContext != null) {
            if (ret != ErrorCode.SUCCESS) {
                MToast.showToast(mContext, "语音初始化失败");
            } else {
                MToast.showToast(mContext, "语音录入中");

            }
        }

        return recognizer;
    }

    /**
     * 语音识别 参数设置
     */


    /**
     * 语音识别回调接口
     */

    RecognizerListener mRecognizerListener = new RecognizerListener() {

        /**
         * 音量改变
         * @param i
         * @param bytes
         */
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            Log.e("voice", "onVolumeChanged   " + i);

        }

        /**
         * 录音机开启后调用
         */
        @Override
        public void onBeginOfSpeech() {
            Log.e("voice", "onBeginOfSpeech");

        }


        /**
         * 录音自动停止回调
         */
        @Override
        public void onEndOfSpeech() {
            Log.e("voice", "onEndOfSpeech");


        }

        /**
         * 返回识别结果,结果可能为空，请增加判空处理 说明：1、SpeechRecognizer采用边录音边发送的方式，可能会多次返回结果。
         * @param result
         * @param b
         *
         *
         */

        @Override
        public void onResult(RecognizerResult result, boolean b) {
            Log.e("voice", "RecognizerResult+   " + result.getResultString());
            //防止重复监听，
//            recognizer.stopListening();
            if (mPermissionsMark != null && result != null) {
                JSONObject object = JSON.parseObject(result.getResultString());
                String message = object.getString("result");

                if (TextUtils.isEmpty(message)) {
                    MToast.showToast(getContext(), getContext().getResources().getString(R.string.voice_remind_1));
                } else {
                    mPermissionsMark.getMessage(message);
                }
                if (!TextUtils.isEmpty(message) && mSynthesizerNew != null) {
                    mSynthesizerNew.stopSpeaking();
                }

            }

        }

        /**
         * 错误回调
         */

        @Override
        public void onError(SpeechError error) {
            Log.e("voice", "SpeechError+   " + error.getErrorDescription());
            MToast.showToast(getContext(), getContext().getResources().getString(R.string.voice_remind_1));

        }


        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            Log.e("voice", "onEvent+   " + i);

        }
    };


    //**************************************************************语音合成***********************************************************

    /**
     * pauseSpeaking() 暂停合成。
     * resumeSpeaking() 暂停后重新开始播放。
     *
     * @return
     */

    public SpeechSynthesizer SpeakerInit(String str) {
        start = 0;
        speakSb.setLength(0);
        speakSb.append(str);
        if (speakSb.length() < maxLeng) {
            return SpeakerInit(str, 0, speakSb.length());
        } else {
            return SpeakerInit(str, 0, maxLeng);
        }
    }


    public SpeechSynthesizer SpeakerInit(String str, int start, int end) {

//        /**
//         * 获取权限
//         */
//
//        isCanListener = mPermissionsMark.getPermissionsMark();
//        if (!isCanListener)
//            return null;
//
//
//        if (SpeechUtility.getUtility() == null) {
//            SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=pc20onli" + ","
//                    + SpeechConstant.URL_ADDRESS + "=114.141.180.226:1028/iat");
//
//        }
//        mSynthesizer = SpeechSynthesizer.createSynthesizer(getContext(), mInitListener);
//
//
//        setSynthesizerParameter(mSynthesizer);
//
//
//        mSynthesizer.stopSpeaking();
//
//        int code = mSynthesizer.startSpeaking(str, mSynthesizerListener);
//
//
//        if (code != ErrorCode.SUCCESS) {
//            Toast.makeText(getContext(), "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
//        }
// 初始化合成对象


        //语音合成 新版

        if (com.iflytek.cloud.SpeechUtility.getUtility() == null) {
            StringBuffer param = new StringBuffer();
            param.append("appid=" + "58cf38ca");
            param.append(",");
            // 设置使用v5+
            param.append(com.iflytek.cloud.SpeechConstant.ENGINE_MODE + "=" + com.iflytek.cloud.SpeechConstant.MODE_MSC);
            com.iflytek.cloud.SpeechUtility.createUtility(getContext(), param.toString());


        }
//
//
        mSynthesizerNew = com.iflytek.cloud.SpeechSynthesizer.createSynthesizer(getContext(), mTtsInitListener);


        setSpeakParam(mSynthesizerNew);


        int code = mSynthesizerNew.startSpeaking(speakSb.substring(start, end), mSpeakListener);

        if (code != ErrorCode.SUCCESS) {
            Toast.makeText(getContext(), "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
        }

        return mSynthesizer;

    }


    /**
     * 新版讯飞语音
     *
     * @param synthesizer
     */
    private void setSpeakParam(com.iflytek.cloud.SpeechSynthesizer synthesizer) {
        // 清空参数
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.PARAMS, null);
        //设置合成
        //设置使用云端引擎
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置发音人
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.SPEED, "50");
        //设置合成音调
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.PITCH, "50");
        //设置合成音量
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.VOLUME, "20");
        //设置播放器音频流类型
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.STREAM_TYPE, "3");

        // 设置播放合成音频打断音乐播放，默认为true
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.AUDIO_FORMAT, "wav");
        synthesizer.setParameter(com.iflytek.cloud.SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }


    /**
     * 语音合成  新版
     */


    com.iflytek.cloud.SynthesizerListener mSpeakListener = new com.iflytek.cloud.SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            Log.e("voice", "onSpeakBegin+   ");

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
            Log.e("voice", "onBufferProgress   " + s);

        }

        @Override
        public void onSpeakPaused() {
            Log.e("voice", "onSpeakPaused   ");

        }

        @Override
        public void onSpeakResumed() {
            Log.e("voice", "onSpeakResumed   ");

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            Log.e("voice", "onSpeakProgress   " + i + "     " + i1 + "      " + i2);

        }

        @Override
        public void onCompleted(com.iflytek.cloud.SpeechError error) {
            Log.e("voice", "onCompleted   ");

//            if (start <= speakSb.length())
//                return;
            //开始位置偏移
            start += maxLeng;
            end = start + maxLeng;
            if (end > speakSb.length()) {
                if (start < speakSb.length() && !TextUtils.isEmpty(speakSb.subSequence(start, speakSb.length()).toString()))
                    SpeakerInit(speakSb.toString(), start, speakSb.length());
                else {
                    start = 0;
                }
            } else {
                if (!TextUtils.isEmpty(speakSb.subSequence(start, end)))
                    SpeakerInit(speakSb.toString(), start, end);
                else
                    start = 0;
            }

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            Log.e("voice", "onEvent   " + i);

        }
    };

    com.iflytek.cloud.InitListener mTtsInitListener = new com.iflytek.cloud.InitListener() {
        @Override
        public void onInit(int i) {

        }
    };


    InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS && mContext != null) {
                Toast.makeText(mContext, "语音初始化失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public boolean isStartListener() {
        return isStartListener;
    }

    public void setStartListener(boolean startListener) {
        isStartListener = startListener;
    }

    public boolean isCanListener() {
        return isCanListener;
    }

    public void setCanListener(boolean canListener) {
        isCanListener = canListener;
    }

    public void setParameter(SpeechRecognizer parameter) {
        // 清空参数
        parameter.setParameter(SpeechConstant.PARAMS, null);
        String lag = "mandarin";
        // 设置引擎
        parameter.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        parameter.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言
        parameter.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        parameter.setParameter(SpeechConstant.ACCENT, lag);


        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        parameter.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        parameter.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        parameter.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        parameter.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        parameter.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }


    public interface IsCanListerner {
        boolean getPermissionsMark();

        void getMessage(String str);
    }

    public IsCanListerner mPermissionsMark;


    public void setPermissionsMark(IsCanListerner permissionsMark) {
        mPermissionsMark = permissionsMark;
    }


    public void voiceCancel() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.destroy();
        }
        if (mSynthesizerNew != null) {
            mSynthesizerNew.stopSpeaking();
            mSynthesizerNew.destroy();
        }
    }
}
