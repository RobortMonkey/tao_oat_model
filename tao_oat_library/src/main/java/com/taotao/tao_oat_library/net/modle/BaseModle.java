package com.taotao.tao_oat_library.net.modle;

import java.io.Serializable;

/**
 * @package com.taotao.tao_oat_library.net.modle
 * @file BaseModle
 * @date 2018/4/26  下午5:05
 * @autor wangxiongfeng
 * @org www.miduo.com
 */
public class BaseModle<T> implements Serializable {

    public int state;
    public String msg;
    public T data;

}