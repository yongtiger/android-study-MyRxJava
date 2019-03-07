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
 * 3）退避策略参数可灵活传入：retryDelayTime * Math.pow(baseNumber, retryCount - 1)
 *      注意：重试次数可以为0（表示不重试）
 *      注意：重试次数可以为1（表示retryDelayTime后重试）
 * 4）过滤指定类型的Exception
 * 5）重试完成后回调onError()
 * 因为range()中的数字耗尽而重试完成后，range()隐式调用了onCompleted()，
 * 所以range()中的数字要比重试次数多一次，在最后一次时回调onError()
 *
 * 参考：https://www.jianshu.com/p/023a5f60e6d0
 */
public class RetryWithTime implements Function<Observable<Throwable>, ObservableSource<?>> {
    private static final String TAG = "TAG";

    private final int RETRY_START = 1;  ///[range()不应从0开始，否则无法重试]

    private int mRetryCount;
    private long mRetryBaseNumber;
    private long mRetryDelayTime;
    private TimeUnit mRetryDelayTimeUnit;
    private Class<?> mRetryException;

    public RetryWithTime(int mRetryCount, long mRetryBaseNumber, long mRetryDelayTime, TimeUnit mRetryDelayTimeUnit, Class<?> mRetryException) {
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
                ///[重试完成后回调onError()]当mRetryCount为0（表示不重试），发送range(1,1)
                .zipWith(Observable.range(RETRY_START, mRetryCount + 1), new BiFunction<Throwable, Integer, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable, Integer retryCount) throws Exception {
                        ///[range()不应从0开始，否则无法重试]当前是第几次重试，因为range()不应从0开始，所以需要做减一的调整
                        retryCount--;
                        Log.d(TAG, "apply: zipWith: 现在是第" + retryCount + "次重试");

                        ///[重试完成后回调onError()]
                        ///注意：range()中的数字耗尽而重试完成后，range()隐式调用了onCompleted()
                        ///如果重试完成后要回调onError()，必须range中额外加多一次，在最后一次时回调onError()
                        if (retryCount == mRetryCount) {
                            throw new Exception(throwable);
                        }

                        return retryCount;
                    }
                }).concatMap(new Function<Integer, Observable<? extends Long>>() {
                    @Override
                    public Observable<? extends Long> apply(Integer retryCount) throws Exception {
                        long retryInterval = mRetryDelayTime * (long) Math.pow(mRetryBaseNumber, retryCount);
                        Log.d(TAG, "apply: concatMap: 现在是第" + retryCount + "次重试, 下次需等待: " + retryInterval);
                        return Observable.timer(retryInterval, mRetryDelayTimeUnit);
                    }
                });
    }

}
