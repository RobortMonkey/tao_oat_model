package com.taotao.tao_oat.eventbus;

/**
 * @package com.taotao.tao_oat.eventbus
 * @file MessageEvent
 * @date 2018/8/16  下午4:10
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class MessageEvent {
    //自定义事件
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
