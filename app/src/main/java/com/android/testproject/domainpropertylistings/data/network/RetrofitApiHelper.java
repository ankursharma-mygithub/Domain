package com.android.testproject.domainpropertylistings.data.network;

import com.android.testproject.domainpropertylistings.data.network.model.PropertyList;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Uses RxJava and Retrofit library to dfownload and parse JSON file.
 */
public class RetrofitApiHelper implements ApiHelper {
    private final ApiEndPoint mApiEndPoint;

    public RetrofitApiHelper(ApiEndPoint apiEndPoint) {
        mApiEndPoint = apiEndPoint;
    }

    public Subscription getItemsList(final GetPropertyListCallback callback) {

        return mApiEndPoint.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends com.android.testproject.domainpropertylistings.data.network.model.PropertyList>>() {
                    @Override
                    public Observable<? extends PropertyList> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<PropertyList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());

                    }

                    @Override
                    public void onNext(PropertyList galleryItemsList) {
                        callback.onSuccess(galleryItemsList);

                    }
                });
    }

}
