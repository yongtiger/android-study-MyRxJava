package cc.brainbook.study.myrxjava.study1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import cc.brainbook.study.myrxjava.R;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class MainActivity2Flowable extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///创建上游：Flowable
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Flowable.create()# subscribe(): emitter.onNext(1): ");
                emitter.onNext(1);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Flowable.create()# subscribe(): emitter.onNext(2): ");
                emitter.onNext(2);

                ///模拟发射错误异常
//                emitter.onError(new Exception("some exception")); ///正确
//                emitter.tryOnError(new Exception());  ///'tryOnError' is marked unstable
//                throw new Exception();    ///错误: 无法访问的语句

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Flowable.create()# subscribe(): emitter.onNext(3): ");
                emitter.onNext(3);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Flowable.create()# subscribe(): emitter.onComplete(): ");
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);    ///Flowable背压策略

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onSubscribe(" + s + "): ");

                ///Subscriber与Observer不同之处
                ///https://www.jianshu.com/p/90e9434b8590
                //这一步是必须，我们通常可以在这里做一些初始化操作
                // 调用request()方法表示初始化工作已经完成
                //调用request()方法，会立即触发onNext()方法
                //在onComplete()方法完成，才会再执行request()后边的代码
                s.request(Long.MAX_VALUE);  ///Flowable解决背压
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onNext(" + integer + "): ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onError(" + e + "): ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onComplete(): ");
            }
        };


        ///开始订阅
        flowable.subscribe(subscriber);

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
