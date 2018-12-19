package ua.mainacad.maintest.maintest.workers;

class SAV {
    private boolean a = false;

    public synchronized void release() {
        a = true;
        notifyAll();
    }

    public synchronized void lock() throws InterruptedException {
        while (!a) {
            wait();
        }
    }
}