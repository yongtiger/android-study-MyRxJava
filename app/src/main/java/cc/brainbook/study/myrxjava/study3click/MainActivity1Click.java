package cc.brainbook.study.myrxjava.study3click;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;

import cc.brainbook.study.myrxjava.R;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class MainActivity1Click extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_click_event);

        Button btnClick = findViewById(R.id.btnClick);


        /* ------------ 点击: share() ------------ */
//        Disposable disposableClicks = RxView.clicks(btnClick)
////                .share()  ///???
//                .subscribe(click -> Log.d(TAG, "RxView.clicks(btnClick): 点击"));


//        /* ------------ 点击: takeUntil() ------------ */
//        Disposable disposableClicks = RxView.clicks(btnClick)
//                .takeUntil(RxView.detaches(btnClick))   ///???
//                .subscribe(click -> Log.d(TAG, "RxView.clicks(btnClick): 点击: takeUntil"));
//
//
//        /* ------------ 点击: 上下游分开 ------------ */
//        Observable<Unit> clicks = RxView.clicks(btnClick);
//        Disposable disposableClicks = clicks.subscribe(new Consumer<Unit>() {
//            @Override
//            public void accept(Unit o) throws Exception {
//                Log.d(TAG, "RxView.Clicks(btnClick): 点击: 上下游分开");
//            }
//        });
//
//
//        /* ------------ 长按点击 ------------ */
//        Disposable disposableLongClicks = RxView.longClicks(btnClick)
//                .subscribe(new Consumer<Unit>() {
//                    @Override
//                    public void accept(Unit o) throws Exception {
//                        Log.d(TAG, "RxView.longClicks(btnClick): 长按点击");
//                    }
//                });
//
//
        /* ------------ 长按点击: 上下游分开 ------------ */
        Observable<Unit> longClicks = RxView.longClicks(btnClick);
        Disposable disposableLongClicks = longClicks.subscribe(new Consumer<Unit>() {
            @Override
            public void accept(Unit o) throws Exception {
                Log.d(TAG, "RxView.longClicks(btnClick): 长按点击: 上下游分开");
            }
        });
    }

}
