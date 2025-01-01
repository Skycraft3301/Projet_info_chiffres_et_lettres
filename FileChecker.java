import java.util.Objects;

public class FileChecker {
    public static long waitForUpdate(String file, int numeroDeLigne, String modifiedText) {
        long referenceTime = Utils.updateFile(file, numeroDeLigne, modifiedText);
        return waitForUpdate(referenceTime, file);
    }

    public static long waitForUpdate(long referenceTime, String file) {
        long updateTime = Utils.getLastUpdate(file);
        while (referenceTime == updateTime) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException _) {

            }

            updateTime = Utils.getLastUpdate(file);
        }
        return updateTime;
    }

    public static long waitForUpdate(long referenceTime, String fileA, String fileB) {
        long updateTimeA = Utils.getLastUpdate(fileA);
        long updateTimeB = Utils.getLastUpdate(fileB);
        while (referenceTime == updateTimeA || referenceTime == updateTimeB) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException _) {

            }
            updateTimeA = Utils.getLastUpdate(fileA);
            updateTimeB = Utils.getLastUpdate(fileB);
        }
        return Math.max(updateTimeA, updateTimeB);
    }

    public static void checkForUpdate(String file, int numeroDeLigne, long referenceTime) {
        long updateTime = Utils.getLastUpdate(file);
        boolean ready = false;
        while (!ready) {
            while (referenceTime == updateTime) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException _) {

                }
                updateTime = Utils.getLastUpdate(file);
            }
            if (!Objects.equals(Utils.getLine(numeroDeLigne, file), "")) {
                ready = true;
            } else {
                referenceTime = updateTime;
            }
        }
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
                if (!Objects.equals(Utils.getLine(numeroDeLigne, fileA), "")) {
                    readyA = true;
                } else {
                    referenceTimeA = updateTimeA;
                }
            }
            if (!readyB) {
                if (!Objects.equals(Utils.getLine(numeroDeLigne, fileB), "")) {
                    readyB = true;
                } else {
                    referenceTimeB = updateTimeB;
                }
            }
        }
    }


}
