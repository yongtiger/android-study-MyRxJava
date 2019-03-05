package cc.brainbook.study.myrxjava.study1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cc.brainbook.study.myrxjava.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity1Observable extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(): ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* -------------- 订阅下游方式一：Observer -------------- */

        ///创建上游：Observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(1): ");
                emitter.onNext(1);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(2): ");
                emitter.onNext(2);

                ///模拟发射错误异常
//                emitter.onError(new Exception("some exception")); ///正确
//                emitter.tryOnError(new Exception());  ///'tryOnError' is marked unstable
//                throw new Exception();    ///错误: 无法访问的语句

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(3): ");
                emitter.onNext(3);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onComplete(): ");
                emitter.onComplete();
            }
        })
//        .subscribe();   ///没有下游，上游一样发送
        .subscribe(new Observer<Integer>() {  ///订阅下游方式一：Observer
            @Override
            public void onSubscribe(Disposable d) { ///相当于RxJava1的onStart()
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onSubscribe(" + d + "): ");
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
        });


        /* -------------- 订阅下游方式二：Consumer -------------- */

        ///创建上游：Observable
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(1): ");
                emitter.onNext(1);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(2): ");
                emitter.onNext(2);

                ///模拟发射错误异常
//                emitter.onError(new Exception("some exception")); ///正确
//                emitter.tryOnError(new Exception());  ///'tryOnError' is marked unstable
//                throw new Exception();    ///错误: 无法访问的语句

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(3): ");
                emitter.onNext(3);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onComplete(): ");
                emitter.onComplete();
            }
        })
//        .subscribe();   ///没有下游，上游一样发送
        .subscribe(new Consumer<Integer>() {  ///订阅下游方式二：Consumer
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] accept(" + integer + "): ");
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
