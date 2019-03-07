package cc.brainbook.study.myrxjava.study4retry;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 特点：
 *
 * 1）使用.flatMap()/concatMap + .timer()/interval()实现延迟重订阅
 * 2）使用.zipWith() + .range()实现有限次数的重订阅
 *      注意：range()中的数字耗尽而重试完成后，range()隐式调用了onCompleted()
 * 3）退避策略参数可灵活传入：retryDelayTime * Math.pow(baseNumber, retryCount)
 *      注意：重试次数不可以为0    ///[range()不应从0开始，否则无法重试]
 *      注意：重试次数可以为1（表示retryDelayTime后重试）
 * 4）过滤指定类型的Exception
 *
 * 缺点：
 * 1）range()中的数字耗尽而重试完成后，range()隐式调用了onCompleted()
 * 2）重试次数无法为0，无法表示不重试的边缘情况
 *
 * 参考：https://www.jianshu.com/p/023a5f60e6d0
 */
public class RetryWithTime0 implements Function<Observable<Throwable>, ObservableSource<?>> {
    private static final String TAG = "TAG";

    private final int RETRY_START = 1;  ///[range()不应从0开始，否则无法重试]

    private int mRetryCount;
    private long mRetryBaseNumber;
    private long mRetryDelayTime;
    private TimeUnit mRetryDelayTimeUnit;
    private Class<?> mRetryException;

    public RetryWithTime0(int mRetryCount, long mRetryBaseNumber, long mRetryDelayTime, TimeUnit mRetryDelayTimeUnit, Class<?> mRetryException) {
        this.mRetryCount = mRetryCount;
        this.mRetryBaseNumber = mRetryBaseNumber;
        this.mRetryDelayTime = mRetryDelayTime;
        this.mRetryDelayTimeUnit = mRetryDelayTimeUnit;
        this.mRetryException = mRetryException;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
                ///过滤指定类型的Exception
                .filter(new Predicate<Throwable>() {
                    @Override
                    public boolean test(Throwable throwable) throws Exception {
                        ///https://stackoverflow.com/questions/5734720/test-if-object-is-instanceof-a-parameter-type/5734764
                        return mRetryException.isInstance(throwable);
                    }
                })
                .zipWith(Observable.range(RETRY_START, mRetryCount), new BiFunction<Throwable, Integer, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable, Integer retryCount) throws Exception {
                        Log.d(TAG, "apply: zipWith: 等待一会后开始第" + retryCount + "次重试");
                        return retryCount;
                    }
                }).concatMap(new Function<Integer, Observable<? extends Long>>() {
                    @Override
                    public Observable<? extends Long> apply(Integer retryCount) throws Exception {
                        long retryInterval = mRetryDelayTime * (long) Math.pow(mRetryBaseNumber, retryCount - 1);
                        Log.d(TAG, "apply: concatMap: 等待 " + retryInterval + " 后开始第" + retryCount + "次重试");
                        return Observable.timer(retryInterval, mRetryDelayTimeUnit);
                    }
                });
    }

}
