package org.skyfw.base.thread.async;

import org.skyfw.base.result.TResult;

import java.util.concurrent.atomic.AtomicBoolean;

public class TAsyncHandlerBlocker<RES> implements TAsyncResultHandler<RES> {

    private final /*volatile*/ AtomicBoolean done= new AtomicBoolean(false);
    private TResult<RES> result;

    public void reset(){
        this.done.set(false);
        this.result= null;
    }


    @Override
    public void onSuccess(TResult<RES> result) {

        synchronized (this.done) {
            this.result= result;
            this.done.set(true);
            this.done.notify();
        }
    }

    @Override
    public boolean onFailure(TResult result) {

        synchronized (this.done) {
            this.result= result;
            this.done.set(true);
            this.done.notify();
        }
        return false;
    }


    public synchronized TResult<RES> waitForResult(){

        /*synchronized (this.done)*/{

            try {
                synchronized (this.done) {
                    while (! this.done.get())
                        this.done.wait();
                }

                return this.result;

            }catch (Throwable e){
                System.out.println(e);
                return null;
            }
        }
    }


}
