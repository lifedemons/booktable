package com.bookatable.domain.usecases;

import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

  private final Scheduler executionScheduler;
  private final Scheduler observingScheduler;
  private Subscription subscription = Subscriptions.empty();

  protected UseCase(Scheduler executionScheduler, Scheduler observingScheduler) {
    this.executionScheduler = executionScheduler;
    this.observingScheduler = observingScheduler;
  }

  @SuppressWarnings("unchecked") public void execute(Subscriber UseCaseSubscriber) {
    this.subscription = this.call()
        .subscribeOn(executionScheduler)
        .observeOn(observingScheduler)
        .subscribe(UseCaseSubscriber);
  }

  protected abstract Single<T> call();

  public void unsubscribe() {
    if (!subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
