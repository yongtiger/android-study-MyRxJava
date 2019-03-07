package cc.brainbook.study.myrxjava.study4retry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cc.brainbook.study.myrxjava.BuildConfig;
import cc.brainbook.study.myrxjava.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2RetryWhen extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(): ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///添加OkHttp拦截器HttpLoggingInterceptor（方便调试）
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG){
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(logInterceptor).build();

        ///步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ljdy.tv/test/") ///设置网络请求 Url
                .client(okHttpClient)   ///添加OkHttp拦截器HttpLoggingInterceptor（方便调试）
                .addConverterFactory(GsonConverterFactory.create()) ///设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) ///支持RxJava
                .build();

        ///步骤2：创建网络请求接口的实例
        MyService request = retrofit.create(MyService.class);

        ///步骤3：采用Observable<...>形式对网络请求进行封装
        Observable<ResultBean> observable = request.getCall();

        ///步骤4：发送网络请求，通过retryWhen()进行重试
        ///注：主要异常才会回调retryWhen()进行重试
        ///mRetryCount不可以为0！
//        observable.retryWhen(new RetryWithTime0(1, 2, 5, TimeUnit.SECONDS, IOException.class))
        ///mRetryCount可以为0（表示不重试）
        observable.retryWhen(new RetryWithTime(3, 2, 5, TimeUnit.SECONDS, IOException.class))
                .subscribeOn(Schedulers.io())             ///切换到IO线程进行网络请求
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

}
