package cc.brainbook.study.myrxjava.study2disposable;

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

public class MainActivity1Disposable extends AppCompatActivity {
    private static final String TAG = "TAG";

    ///取消订阅
    private Disposable mDisposable;

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

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(3): ");
                emitter.onNext(3);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onComplete(): ");
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {  ///订阅下游方式一：Observer

            ///取消订阅
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) { ///相当于RxJava1的onStart()
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onSubscribe(" + d + "): ");

                ///取消订阅
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onNext(" + integer + "): ");

                if (integer == 2) {
                    Log.d(TAG, "onNext: dispose(): ");
                    ///取消订阅
                    if(mDisposable != null && !mDisposable.isDisposed()){
                        mDisposable.dispose();
                    }
                    Log.d(TAG, "onNext: isDisposed(): " + mDisposable.isDisposed());
                }
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
        mDisposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(1): ");
                emitter.onNext(1);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(2): ");
                emitter.onNext(2);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onNext(3): ");
                emitter.onNext(3);

                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] Observable.create()# subscribe(): emitter.onComplete(): ");
                emitter.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {  ///订阅下游方式二：Consumer
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] accept(" + integer + "): ");

                if (integer == 2) {
                    Log.d(TAG, "accept(): dispose(): ");
                    ///取消订阅
                    if(mDisposable != null && !mDisposable.isDisposed()){
                        mDisposable.dispose();
                    }
                    Log.d(TAG, "accept(): isDisposed(): " + mDisposable.isDisposed());
                }
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

        ///取消订阅
        Log.d(TAG, "onDestroy(): dispose(): ");
        ///取消订阅
        if(mDisposable != null && !mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        Log.d(TAG, "onDestroy(): isDisposed(): " + mDisposable.isDisposed());
    }

}
