package cc.brainbook.study.myrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import kotlin.Unit;

public class MainActivityClickEvent extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_click_event);

        Button btn = findViewById(R.id.btn);
        Observable<Long> clicks = RxView.clicks(btn)
                .map(new Function<Unit, Long>() {
                    @Override
                    public Long apply(Unit o) throws Exception {
                        return System.currentTimeMillis();
                    }
                })
                .share();

        clicks.zipWith(clicks.skip(1), new BiFunction<Long, Long, Long>() {
            @Override
            public Long apply(Long t1, Long t2) throws Exception {
                return t2 - t1;
            }
        })
        .filter(new Predicate<Long>() {
            @Override
            public boolean test(Long interval) throws Exception {
                return interval < 500;
            }
        })
        .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                // handle double click
                Log.d(TAG, "accept: double click");
            }
        });
    }
}
