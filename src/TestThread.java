import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

/**
 * Created by Tanaphon on 8/18/2016.
 */
public class TestThread {

    interface TestCallBack<CCCC> {
        void callDone(CCCC aaaa);
    }

    static class MyCallDone implements TestThread.TestCallBack<Integer> {
        final List<Integer> completedList = new ArrayList<>();

        @Override
        public void callDone(Integer aaaa) {
            completedList.add(aaaa);
            System.out.println(Thread.currentThread().getName() + " I am done my " + aaaa + " round(s) done - " + completedList.size());
        }
    }

    public static void main(String[] args) {

        System.out.println("Hello ");

        HelloThread helloThread1 = new HelloThread("HelloThread na", 10);
        HelloThread helloThread2 = new HelloThread("HelloThread ja", 10);
        MyCallDone myCallDone = new MyCallDone();
        helloThread1.setCallBack(myCallDone);
        helloThread2.setCallBack(myCallDone);

        Thread t1 = new Thread(helloThread1);
        Thread t2 = new Thread(helloThread2);


        t1.start();
        t2.start();


        System.out.println("Good bye ");
    }

    static class HelloThread implements Runnable {
        private final int mRound;
        private final String mName;
        private TestCallBack<Integer> mCallBack;

        HelloThread(String name, int round) {
            mRound = round;
            mName = name;
        }

        void setCallBack(TestCallBack<Integer> a) {
            mCallBack = a;
        }


        static Random mRandom = new Random(220);

        @Override
        public void run() {
            for (int i = 0; i < mRound; i++) {
                System.out.println("[" + mName + "]" + "Run i = " + (i));
            }

            try {
                int randomSec = mRandom.nextInt() % 9;
                Thread.sleep(Math.abs(randomSec));
            } catch (InterruptedException e) {

            }
            if (mCallBack != null) {
                mCallBack.callDone(mRound);
            }
        }
    }
}
