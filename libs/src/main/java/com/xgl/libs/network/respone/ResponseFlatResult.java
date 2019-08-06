package com.xgl.libs.network.respone;

import com.xgl.libs.network.exception.BusinessException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author xingguolei
 */
public final class ResponseFlatResult {

    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = 0;

    public static <T> Observable<T> flatResult(final BaseResponse<T> result) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                switch (result.getStatus()) {
                    case SUCCESS_CODE:
                        emitter.onNext(result.getResult());
                        break;
                    case ERROR_CODE:
                        emitter.onError(new BusinessException(result.getMessage()));
                        break;
                    default:
                        emitter.onError(new BusinessException(result.getMessage()));
                        break;
                }
                emitter.onComplete();
            }
        });
    }
}
