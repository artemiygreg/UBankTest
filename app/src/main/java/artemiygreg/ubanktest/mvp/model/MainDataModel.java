package artemiygreg.ubanktest.mvp.model;


import java.util.List;

import artemiygreg.ubanktest.model.Data;
import artemiygreg.ubanktest.utils.DataHelper;
import rx.Observable;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class MainDataModel {

    public Observable<List<Data>> loadDataAsObseravble() {
        return Observable.create(subscriber -> {
            if(!subscriber.isUnsubscribed()) {
                try {
                    subscriber.onNext(DataHelper.buildData(25));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
