package com.xgl.libs.network.respone;

import com.xgl.libs.network.exception.BusinessException;

import rx.Observable;
import rx.Subscriber;

/**
 * @author xingguolei
 */
public final class ResponseFlatResult {

    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = 0;

    public static <T> Observable<T> flatResult(final BaseResponse<T> result) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                switch (result.getStatus()) {
                    case SUCCESS_CODE:
                        subscriber.onNext(result.getResult());
                        break;
                    case ERROR_CODE:
                        subscriber.onError(new BusinessException(result.getMessage()));
                        break;
                    default:
                        subscriber.onError(new BusinessException(result.getMessage()));
                        break;
                }
                subscriber.onCompleted();
            }
        });
    }
}
