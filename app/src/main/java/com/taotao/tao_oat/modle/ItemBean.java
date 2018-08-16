package com.taotao.tao_oat.modle;

/**
 * @package com.taotao.tao_oat.modle
 * @file ItemBean
 * @date 2018/8/16  下午2:32
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class ItemBean {
    private String name;
    private int type;

    public ItemBean(String name, int type) {
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
