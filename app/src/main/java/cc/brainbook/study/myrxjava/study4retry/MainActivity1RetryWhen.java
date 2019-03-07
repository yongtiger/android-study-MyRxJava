package cc.brainbook.study.myrxjava.study4retry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cc.brainbook.study.myrxjava.R;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * https://juejin.im/entry/5a1cb9476fb9a0451463c5b1
 */
public class MainActivity1RetryWhen extends AppCompatActivity {
    private static final String TAG = "TAG";

    ///可重试次数
    private int maxConnectCount = 10;
    ///当前已重试次数
    private int currentRetryCount = 0;
    ///重试等待时间
    private int waitRetryTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(): ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ljdy.tv/test/") ///设置网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) ///设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) ///支持RxJava
                .build();

        ///步骤2：创建网络请求接口的实例
        MyService request = retrofit.create(MyService.class);

        ///步骤3：采用Observable<...>形式对网络请求进行封装
        Observable<ResultBean> observable = request.getCall();

        ///步骤4：发送网络请求，通过retryWhen()进行重试
        ///注：主要异常才会回调retryWhen()进行重试
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                ///参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                        ///输出异常信息
                        Log.d(TAG,  "发生异常： "+ throwable.toString());

                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常（IO异常）才选择重试
                         */
                        if (throwable instanceof IOException){

                            Log.d(TAG,  "属于IO异常，需重试" );

                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的重试次数，才选择重试
                             */
                            if (currentRetryCount < maxConnectCount){

                                // 记录重试次数
                                currentRetryCount++;
                                Log.d(TAG,  "重试次数： " + currentRetryCount);

                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen()重订阅，最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符: 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：退避策略：遇到的异常越多，时间越长
                                 * 在delay()操作符的等待时间内设置，每重试1次，增多延迟重试时间1s
                                 */
                                ///设置等待时间
                                waitRetryTime = 1000 + currentRetryCount* 1000;
                                Log.d(TAG,  "等待时间 =" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);


                            }else{
                                ///若重试次数已 > 设置重试次数，则不重试
                                ///通过发送error来停止重试（可在观察者的onError()中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数: " +currentRetryCount  + "，不再重试"));

                            }
                        }

                        ///若发生的异常不属于I/O异常，则不重试
                        ///通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                        else{
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())             ///切换到IO线程进行网络请求
        .observeOn(AndroidSchedulers.mainThread())  ///切换回到主线程 处理请求结果
        .subscribe(new Observer<ResultBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,  "onSubscribe(): ");
            }

            @Override
            public void onNext(ResultBean result) {
                Log.d(TAG,  "onNext(): " + result.toString());
            }

            @Override
            public void onError(Throwable e) {
                ///获取停止重试的信息
                Log.d(TAG,  "onError(): " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,  "onComplete(): ");
            }
        });

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart(): ");

        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume(): ");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause(): ");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop(): ");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy(): ");

        super.onDestroy();
    }

}
