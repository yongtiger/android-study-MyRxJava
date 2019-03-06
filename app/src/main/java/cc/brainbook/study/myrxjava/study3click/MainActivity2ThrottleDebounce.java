package cc.brainbook.study.myrxjava.study3click;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import cc.brainbook.study.myrxjava.R;

public class MainActivity2ThrottleDebounce extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_click_event);

        Button btnClick = findViewById(R.id.btnClick);


//        /* ------------ 点击防抖（指定时间段内只接受第一次点击，防止短时间内对View的重复点击）------------ */
//        ///throttleFirst(long windowDuration, TimeUnit unit)，设置一定时间内只响应首次(throttleFirst)或者末次(throttleLast)的点击事件。
//        Disposable disposableClicks = RxView.clicks(btnClick)
//                .throttleFirst(2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Unit>() {
//                    @Override
//                    public void accept(Unit o) throws Exception {
//                        Log.d(TAG, "RxView.clicks(btnClick): 2秒内点击防抖");
//                    }
//                });


//        /* ------------ 点击防抖（使用debounce()操作符） 注意：点击后有延迟！放弃！------------ */
//        ///https://juejin.im/post/5b8f5ea8f265da0a9223887e
//        ///debounce()操作符: 产生一个新的 Observable, 这个 Observable 只发射原 Observable 中时间间隔小于指定阈值的最大子序列的最后一个元素。
//        ///只有在空闲了一段时间后才发射数据，通俗的说，就是如果一段时间没有操作，就执行一次操作
//        ///debounce操作符是用来防重复数据的或者(防抖动)
//        Disposable disposableClicks = RxView.clicks(btnClick)
//                .debounce(500, TimeUnit.MILLISECONDS)
//                .subscribe(o -> {
//                    Log.d(TAG, "RxView.clicks(btnClick): 2秒内点击防抖");
//                });


//        /* ------------ 实现双击（只接受前后两次点击时间差小于指定时间（如500ms）的点击）------------ */
//        ///注意：不防抖！
//        Observable<Long> clicks = RxView.clicks(btnClick)
//                .map(o -> System.currentTimeMillis())
//                .share(); ///share 操作符: 用来保证点击事件的 Observable 被转为 Hot Observable (https://juejin.im/post/5b8f5ea8f265da0a9223887e)
//        Disposable disposableClicks = clicks.zipWith(clicks.skip(1), new BiFunction<Long, Long, Long>() {
//            @Override
//            public Long apply(Long t1, Long t2) throws Exception {
//                return t2 - t1;
//            }
//        })
//        .filter(new Predicate<Long>() {   ///前后两次点击时间差小于指定时间（如500ms）
//            @Override
//            public boolean test(Long interval) throws Exception {
//                return interval < 500;
//            }
//        })
//        .subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                // handle double click
//                Log.d(TAG, "RxView.clicks(btnClick): 双击");
//            }
//        });


//        /* ------------ 实现双击防抖（只接受一定时间内（比如2秒）、且前后两次点击时间差小于指定时间（如500ms）的点击）------------ */
//        ///https://juejin.im/post/5b8f5ea8f265da0a9223887e
//        Observable<Long> clicks = RxView.clicks(btnClick)
//                .map(o -> System.currentTimeMillis())
//                .share(); ///share 操作符: 用来保证点击事件的 Observable 被转为 Hot Observable (https://juejin.im/post/5b8f5ea8f265da0a9223887e)
//        Disposable disposableClicks = clicks.zipWith(clicks.skip(1), new BiFunction<Long, Long, Long>() {
//            @Override
//            public Long apply(Long t1, Long t2) throws Exception {
//                return t2 - t1;
//            }
//        })
//        .filter(new Predicate<Long>() { ///前后两次点击时间差小于指定时间（如500ms）
//            @Override
//            public boolean test(Long interval) throws Exception {
//                return interval < 500;
//            }
//        })
//        .throttleFirst(2, TimeUnit.SECONDS) ///只接受一定时间内（比如2秒）
//        .subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long interval) throws Exception {
//                Log.d(TAG, "RxView.clicks(btnClick): 双击防抖: interval: " + interval);
//            }
//        });


//        /* ------------ 实现双击防抖（使用debounce()操作符）注意：双击后有延迟！放弃！------------ */
//        ///https://blog.csdn.net/qq275949034qq/article/details/51597990
//        ///debounce()操作符: 产生一个新的 Observable, 这个 Observable 只发射原 Observable 中时间间隔小于指定阈值的最大子序列的最后一个元素。
//        ///只有在空闲了一段时间后才发射数据，通俗的说，就是如果一段时间没有操作，就执行一次操作
//        ///debounce操作符是用来防重复数据的或者(防抖动)
//        Observable<Unit> observable = RxView.clicks(btnClick).share(); ///share 操作符: 用来保证点击事件的 Observable 被转为 Hot Observable (https://juejin.im/post/5b8f5ea8f265da0a9223887e)
//        Disposable disposableClicks = observable.buffer(observable.debounce(500, TimeUnit.MILLISECONDS))
//                .filter(new Predicate<List<Unit>>() {
//                    @Override
//                    public boolean test(List<Unit> units) throws Exception {
//                        return units.size() >= 2;
//                    }
//                })
//                .subscribe(new Consumer<List<Unit>>() {
//                    @Override
//                    public void accept(List<Unit> o) throws Exception {
//                        //double click detected
//                        Log.d(TAG, "RxView.clicks(btnClick): 双击防抖");
//                    }
//                });
    }

}
