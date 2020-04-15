package com.xgl.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xgl.libs.network.HttpClient;
import com.xgl.libs.network.HttpLog;
import com.xgl.libs.network.respone.ResponseFlatResult;
import com.xgl.libs.utils.MD5Util;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author xingguolei
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HttpClient httpClient = new HttpClient.Builder()
                .baseUrl("http://111.160.120.118:8092/")
                .log(new HttpLog.ILog() {
                    @Override
                    public void d(String string) {
                        Log.i("MainActivity", string);
                    }

                    @Override
                    public void e(String error) {
                        Log.i("MainActivity", error);
                    }

                    @Override
                    public void wtf(Throwable tr) {

                    }
                })
                .build();
        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("account", "test");
                map.put("password", MD5Util.get32MD5Lower("1231"));
                httpClient.getInnerRetrofit().create(TestApi.class)
                        .login(map)
                        .compose(ResponseFlatResult.<LoginBean>flatObservableResult())
                        .subscribe(new Observer<LoginBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.i("MainActivity", "onSubscribe");
                            }

                            @Override
                            public void onNext(LoginBean loginBean) {
                                Log.i("MainActivity", loginBean.getName());
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.i("MainActivity", "onError");
                            }

                            @Override
                            public void onComplete() {
                                Log.i("MainActivity", "onComplete");
                            }
                        });
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
