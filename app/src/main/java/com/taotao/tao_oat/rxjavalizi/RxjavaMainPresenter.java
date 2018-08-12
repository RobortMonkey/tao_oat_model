package com.taotao.tao_oat.rxjavalizi;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @package com.taotao.tao_oat.rxjavalizi
 * @file RxjavaMainPresenter
 * @date 2018/5/4  上午11:36
 * @autor wangxiongfeng
 * @org www.miduo.com
 */
public class RxjavaMainPresenter {
    public void main() {
        //被观察者 发射数据 主动
        //方式一
        final Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("lala");
            }
        });


        //方式二  会自动调用next 方法  将会在 Observer的onNext()

        Observable<String> hello = Observable.just("hello");
        //方式三

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }
        Observable.fromIterable((Iterable<String>) list);

        //方式四
        Observable<String> lalal = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.just("lalal");
            }
        });
        //方式五
        Observable.interval(2, TimeUnit.SECONDS);

        //方式 六
        Observable<Integer> range = Observable.range(1, 20);

        Observable.range(0, 20);

        //方式 七
        Observable.timer(2, TimeUnit.SECONDS);


        //对 Objservable 进行再加工 map()   flatMap()    filter()   take()   doOnNext()
        Observable.just("hello").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                int initValue = 0;
                try {
                    initValue = Integer.parseInt(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return initValue;
            }
        });

        Observable.just("hello").flatMap(new Function<String, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(String s) throws Exception {

                int initValue = 0;
                try {
                    initValue = Integer.parseInt(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Observable.just(initValue);

            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return false;
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                    }
                });
            }
        });


        Observable.just("hello").flatMap(new Function<String, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(String s) throws Exception {
                List<String> strList = new ArrayList<>();
                strList.add(s);
                return Observable.just(strList);

            }
        }).take(5).doOnNext(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                Log.i("mark", strings.toArray().toString());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


        //观察者数据接受方被动
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //订阅 建立联系
        observable.subscribe(observer);


        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {

            }
        }, BackpressureStrategy.ERROR);
        /**
         * BUFFER
         所谓BUFFER就是把RxJava中默认的只能存128个事件的缓存池换成一个大的缓存池，支持存很多很多的数据。
         这样，消费者通过request()即使传入一个很大的数字，生产者也会生产事件，并将处理不了的事件缓存。
         但是这种方式任然比较消耗内存，除非是我们比较了解消费者的消费能力，能够把握具体情况，不会产生OOM。
         总之BUFFER要慎用。


         DROP
         看名字就可以了解其作用：当消费者处理不了事件，就丢弃。
         消费者通过request()传入其需求n，然后生产者把n个事件传递给消费者供其消费。其他消费不掉的事件就丢掉。


         LATEST
         LATEST与DROP功能基本一致。
         消费者通过request()传入其需求n，然后生产者把n个事件传递给消费者供其消费。其他消费不掉的事件就丢掉。
         唯一的区别就是LATEST总能使消费者能够接收到生产者产生的最后一个事件。
         还是以上述例子展示，唯一的区别就是Flowable不再无限发事件，只发送1000000个。

         */


        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);


    }
}
