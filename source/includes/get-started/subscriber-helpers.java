package helpers;

import com.mongodb.MongoTimeoutException;
import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.mongodb.internal.thread.InterruptionUtil.interruptAndCreateMongoInterruptedException;

/**
 *  Subscriber helper implementations for the tutorial.
 */
public final class SubscriberHelpers {

    /**
     * A Subscriber that stores the publishers results and provides a latch so can block on completion.
     *
     * @param <T> The publishers result type
     */
    public abstract static class ObservableSubscriber<T> implements Subscriber<T> {
        private final List<T> received;
        private final List<RuntimeException> errors;
        private final CountDownLatch latch;
        private volatile Subscription subscription;
        private volatile boolean completed;

        /**
         * Construct an instance
         */
        public ObservableSubscriber() {
            this.received = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.latch = new CountDownLatch(1);
        }

        @Override
        public void onSubscribe(final Subscription s) {
            subscription = s;
        }

        @Override
        public void onNext(final T t) {
            received.add(t);
        }

        @Override
        public void onError(final Throwable t) {
            if (t instanceof RuntimeException) {
                errors.add((RuntimeException) t);
            } else {
                errors.add(new RuntimeException("Unexpected exception", t));
            }
            onComplete();
        }

        @Override
        public void onComplete() {
            completed = true;
            latch.countDown();
        }

        /**
         * Gets the subscription
         *
         * @return the subscription
         */
        public Subscription getSubscription() {
            return subscription;
        }

        /**
         * Get received elements
         *
         * @return the list of received elements
         */
        public List<T> getReceived() {
            return received;
        }

        /**
         * Get error from subscription
         *
         * @return the error, which may be null
         */
        public RuntimeException getError() {
            if (errors.size() > 0) {
                return errors.get(0);
            }
            return null;
        }

        /**
         * Get received elements.
         *
         * @return the list of receive elements
         */
        public List<T> get() {
            return await().getReceived();
        }

        /**
         * Get received elements.
         *
         * @param timeout how long to wait
         * @param unit the time unit
         * @return the list of receive elements
         */
        public List<T> get(final long timeout, final TimeUnit unit) {
            return await(timeout, unit).getReceived();
        }


        /**
         * Get the first received element.
         *
         * @return the first received element
         */
        public T first() {
            List<T> received = await().getReceived();
            return received.size() > 0 ? received.get(0) : null;
        }

        /**
         * Await completion or error
         *
         * @return this
         */
        public ObservableSubscriber<T> await() {
            return await(60, TimeUnit.SECONDS);
        }

        /**
         * Await completion or error
         *
         * @param timeout how long to wait
         * @param unit the time unit
         * @return this
         */
        public ObservableSubscriber<T> await(final long timeout, final TimeUnit unit) {
            subscription.request(Integer.MAX_VALUE);
            try {
                if (!latch.await(timeout, unit)) {
                    throw new MongoTimeoutException("Publisher onComplete timed out");
                }
            } catch (InterruptedException e) {
                throw interruptAndCreateMongoInterruptedException("Interrupted waiting for observeration", e);
            }
            if (!errors.isEmpty()) {
                throw errors.get(0);
            }
            return this;
        }
    }

    /**
     * A Subscriber that immediately requests Integer.MAX_VALUE onSubscribe
     *
     * @param <T> The publishers result type
     */
    public static class OperationSubscriber<T> extends ObservableSubscriber<T> {

        @Override
        public void onSubscribe(final Subscription s) {
            super.onSubscribe(s);
            s.request(Integer.MAX_VALUE);
        }
    }

    /**
     * A Subscriber that prints the json version of each document
     */
    public static class PrintDocumentSubscriber extends ConsumerSubscriber<Document> {
        /**
         * Construct a new instance
         */
        public PrintDocumentSubscriber() {
            super(t -> System.out.println(t.toJson()));
        }
    }

    /**
     * A Subscriber that processes a consumer for each element
     * @param <T> the type of the element
     */
    public static class ConsumerSubscriber<T> extends OperationSubscriber<T> {
        private final Consumer<T> consumer;

        /**
         * Construct a new instance
         * @param consumer the consumer
         */
        public ConsumerSubscriber(final Consumer<T> consumer) {
            this.consumer = consumer;
        }


        @Override
        public void onNext(final T document) {
            super.onNext(document);
            consumer.accept(document);
        }
    }

    private SubscriberHelpers() {
    }
}