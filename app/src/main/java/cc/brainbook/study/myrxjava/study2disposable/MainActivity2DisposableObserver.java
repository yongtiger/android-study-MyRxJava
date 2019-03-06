package cc.brainbook.study.myrxjava.study2disposable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cc.brainbook.study.myrxjava.R;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity2DisposableObserver extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(): ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* ------ DisposableObserver allows asynchronous cancellation by implementing Disposable ------ */
        ///https://juejin.im/post/5b0cdcac518825155e4d655f

//        ///方法一：subscribe(disposableObserver)
//        DisposableObserver disposableObserver = new DisposableObserver<Integer>() {
//            @Override
//            public void onNext(Integer integer) {
//                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onNext(" + integer + "): ");
//            }
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onError(" + e + "): ");
//            }
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "Thread[" + Thread.currentThread().getName() + "] onComplete(): ");
//            }
//        };
//        ///开始订阅
//        Observable.just(1).subscribe(disposableObserver);
//        ///在需要取消订阅的地方对这个observer进行取消订阅即可
//        disposableObserver.dispose();


        ///方法二：subscribeWith
        DisposableObserver disposableObserver = Observable.just(1).subscribeWith(new DisposableObserver<Integer>() {
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
        ///在需要取消订阅的地方对这个observer进行取消订阅即可
        disposableObserver.dispose();

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
