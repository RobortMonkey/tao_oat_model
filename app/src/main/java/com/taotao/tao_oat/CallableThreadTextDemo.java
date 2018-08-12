package com.taotao.tao_oat;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by space on 2018/7/20 0020.
 */

public class CallableThreadTextDemo implements Callable<String> {


    public static void  main(){
        CallableThreadTextDemo callableThreadTextDemo = new CallableThreadTextDemo();
        FutureTask futureTask=new FutureTask<>(callableThreadTextDemo);
        new Thread(futureTask,"new Thread").start();
    }
    @Override
    public String call() throws Exception {
        return null;
    }
}
