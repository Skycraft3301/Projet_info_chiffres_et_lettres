import java.util.List;
import java.util.Objects;

public class FileChecker {
    public static long waitForUpdate(String file, int numeroDeLigne, String modifiedText) {
        long referenceTime = Utils.updateFile(file, numeroDeLigne, modifiedText);
        return waitForUpdate(referenceTime, file);
    }

    public static long waitForUpdate(long referenceTime, String file) {
        long updateTime = Utils.getLastUpdate(file);
        updateTime = getUpdateTime(file, referenceTime, updateTime);
        return updateTime;
    }

    public static void checkForUpdate(String file, int numeroDeLigne, long referenceTime) {
        long updateTime = Utils.getLastUpdate(file);
        boolean ready = false;
        while (!ready) {
            updateTime = getUpdateTime(file, referenceTime, updateTime);
            if (isLineNotEmpty(file, numeroDeLigne)) {
                ready = true;
            } else {
                referenceTime = updateTime;
            }
        }
    }

    private static boolean isLineNotEmpty(String file, int numeroDeLigne) {
        return !Objects.equals(Utils.getLine(numeroDeLigne, file), "");
    }

    public static void checkForUpdate(String file, List<Integer> lineNumbers, long referenceTime) {
        long updateTime = Utils.getLastUpdate(file);
        boolean ready = false;
        while (!ready) {
            updateTime = getUpdateTime(file, referenceTime, updateTime);
            if (lineNumbers.stream().allMatch(lineNumber -> isLineNotEmpty(file, lineNumber))) {
                ready = true;
            } else {
                referenceTime = updateTime;
            }
        }
    }

    private static long getUpdateTime(String file, long referenceTime, long updateTime) {
        while (referenceTime == updateTime) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException _) {

            }
            updateTime = Utils.getLastUpdate(file);
        }
        return updateTime;
    }

    public static void checkForUpdate(String fileA, String fileB, int numeroDeLigne, long referenceTimeA, long referenceTimeB) {
        long updateTimeA = Utils.getLastUpdate(fileA);
        long updateTimeB = Utils.getLastUpdate(fileB);
        boolean readyA = false;
        boolean readyB = false;
        while (!readyA || !readyB) {
            while (referenceTimeA == updateTimeA && referenceTimeB == updateTimeB) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException _) {

                }
                updateTimeA = Utils.getLastUpdate(fileA);
                updateTimeB = Utils.getLastUpdate(fileB);
            }
            if (!readyA) {
                if (isLineNotEmpty(fileA, numeroDeLigne)) {
                    readyA = true;
                } else {
                    referenceTimeA = updateTimeA;
                }
            }
            if (!readyB) {
                if (isLineNotEmpty(fileB, numeroDeLigne)) {
                    readyB = true;
                } else {
                    referenceTimeB = updateTimeB;
                }
            }
        }
    }


}