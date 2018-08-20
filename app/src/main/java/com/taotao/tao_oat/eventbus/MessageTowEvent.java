package com.taotao.tao_oat.eventbus;

/**
 * @package com.taotao.tao_oat.eventbus
 * @file MessageTowEvent
 * @date 2018/8/16  下午4:25
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class MessageTowEvent {
    String  message;

    public MessageTowEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
