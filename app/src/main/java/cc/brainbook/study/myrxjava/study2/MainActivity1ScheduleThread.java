package cc.brainbook.study.myrxjava.study2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import cc.brainbook.study.myrxjava.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity1ScheduleThread extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(): ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        })

//        /* ---------- 指定上游发送事件的线程（只第一次指定线程有效！指定线程的位置随意）---------- */
//
//        ///主线程，指定的操作将在Android 主线程运行
//        .subscribeOn(AndroidSchedulers.mainThread())
//
//
//        /* ---------- 指定下游接收事件的线程（可多次切换指定线程，指定线程的位置之后生效）---------- */
//
//        ///主线程，指定的操作将在Android 主线程运行
//        .observeOn(AndroidSchedulers.mainThread())
//
//        ///这个是最简单的一种Scheduler，就是一个单线程的实现
//        .observeOn(Schedulers.single())       ///没有线程池
//
//        ///总是启用新线程，并在新线程执行操作
//        .observeOn(Schedulers.newThread())    ///没有线程池
//
//        ///在当前线程立即执行任务，如果当前线程有任务在执行，则会将其暂停，等插入进来的任务执行完之后，再将未完成的任务接着执行
//        .observeOn(Schedulers.trampoline())   ///没有线程池
//
//        ///计算所使用的 Scheduler
//        // 这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算
//        // 这个 Scheduler 使用的固定的线程池，大小为 CPU 核数
//        // 不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU
//        .observeOn(Schedulers.computation())  ///系统自行管理线程池
//
//        ///I/O 操作（读写文件、数据库、网络信息交互等）所使用的 Scheduler
//        // 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池
//        // 可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率
//        // 不要把计算工作放在 io() 中，可以避免创建不必要的线程
//        .observeOn(Schedulers.io())           ///系统自行管理线程池
//
//        ///ExecutorService是Java的线程池的一个管理接口
//        .observeOn(Schedulers.from(Executors.newCachedThreadPool()))
//        .observeOn(Schedulers.from(Executors.newSingleThreadExecutor(new ThreadFactory() {
//                    @Override
//                    public Thread newThread(Runnable r) {
//                        return new Thread(r, "new single thread executor");
//                    }
//        })))

        .subscribe(new Observer<Integer>() {
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
