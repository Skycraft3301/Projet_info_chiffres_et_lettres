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
        while (referenceTime == updateTimeA && referenceTime == updateTimeB) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException _) {

            }
            updateTimeA = Utils.getLastUpdate(fileA);
            updateTimeB = Utils.getLastUpdate(fileB);
        }
        return Math.max(updateTimeA, updateTimeB);
    }
}
