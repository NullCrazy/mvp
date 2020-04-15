package com.xgl.libs.network.respone;

import com.xgl.libs.network.exception.BusinessException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xingguolei
 */
public final class ResponseFlatResult {

    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = 0;

    private static <T> T flatResult(final BaseResponse<T> result) throws Exception {
        if (result.getStatus() == SUCCESS_CODE) {
            return result.getResult();
        } else {
            throw new BusinessException(result.getMessage());
        }
    }

    public static <T> ObservableTransformer<BaseResponse<T>, T> flatObservableResult() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(BaseResponse<T> result) throws Exception {
                        return flatResult(result);
                    }
                });
            }
        };
    }

    public static <T> SingleTransformer<BaseResponse<T>, T> flatSingleResult() {
        return new SingleTransformer<BaseResponse<T>, T>() {
            @Override
            public SingleSource<T> apply(Single<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(BaseResponse<T> result) throws Exception {
                        return flatResult(result);
                    }
                });
            }
        };
    }

    public static <T> MaybeTransformer<BaseResponse<T>, T> flatMaybeResult() {
        return new MaybeTransformer<BaseResponse<T>, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(BaseResponse<T> result) throws Exception {
                        return flatResult(result);
                    }
                });
            }
        };
    }

    public static <T> FlowableTransformer<BaseResponse<T>, T> flatFlowResult() {
        return new FlowableTransformer<BaseResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(BaseResponse<T> result) throws Exception {
                        return flatResult(result);
                    }
                });
            }
        };
    }
}
