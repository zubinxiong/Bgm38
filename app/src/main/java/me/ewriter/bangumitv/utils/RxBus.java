package me.ewriter.bangumitv.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Zubin on 2016/9/5.
 * http://www.jianshu.com/p/ca090f6e2fe2#
 */
public class RxBus {
    private static volatile RxBus instance;

    private final Subject<Object, Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例
    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    // 发送一个新的事件
    public void post(Object object) {
        bus.onNext(object);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> tObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
