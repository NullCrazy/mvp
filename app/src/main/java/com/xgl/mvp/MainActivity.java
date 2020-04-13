package com.xgl.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @author xingguolei
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable.concat(testA(), testB())
                .firstElement()
                .toObservable()
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        Log.i("TAGS", "重试" + System.currentTimeMillis());
                        return Observable.error(new Exception());
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("TAGS", String.valueOf(integer));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAGS", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAGS", "onComplete");
                    }
                });

    }

    private Observable<Integer> testA() {
        if (System.currentTimeMillis() % 2 == 0) {
            return Observable.empty();
        } else {
            return Observable.just(1);
        }
    }

    private Observable<Integer> testB() {
        return Observable.just(100);
    }
}
