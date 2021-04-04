import java.util.concurrent.*;
import java.util.regex.Pattern;

class Matcher {

    public static boolean matches(String text, String regex) {
        Callable<Boolean> matcher = (() -> Pattern.compile(regex).matcher(new UpgradedCharSequence(text)).matches());
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Boolean> task = executor.submit(matcher);
        executor.shutdown();
        try {
            return task.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            if (!task.isDone()) {
                System.out.println("Too many backtrack operations!");
            }
            else {
                System.out.println("Incorrect regex");
            }
            task.cancel(true);
            return false;
        }
    }
}

class UpgradedCharSequence implements CharSequence {

    CharSequence superClass;

    UpgradedCharSequence(CharSequence sequence) {
        superClass = sequence;
    }

    @Override
    public int length() {
        return superClass.length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return superClass.subSequence(start, end);
    }

    @Override
    public char charAt(int i) {
        if (Thread.currentThread().isInterrupted()) {
            throw new RuntimeException("Exception");
        }
        return superClass.charAt(i);
    }
}