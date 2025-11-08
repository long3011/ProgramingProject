import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

public final class AgeSampler {
    private final int[] ages;     // table of unique, sorted ages
    private final double[] cdf;   // cumulative probabilities aligned with ages[]

    public AgeSampler(int[] ages, int[] counts) {
        if (ages == null || counts == null || ages.length == 0 || ages.length != counts.length) {
            throw new IllegalArgumentException("ages and counts must be non-null and same positive length");
        }
        // Copy and sort by age while keeping counts aligned
        int n = ages.length;
        int[] a = Arrays.copyOf(ages, n);
        int[] c = Arrays.copyOf(counts, n);
        sortByAge(a, c);

        long total = 0;
        for (int cnt : c) {
            if (cnt < 0) throw new IllegalArgumentException("counts must be non-negative");
            total += cnt;
        }
        if (total <= 0) throw new IllegalArgumentException("sum(counts) must be > 0");

        this.ages = a;
        this.cdf = new double[n];
        double run = 0.0;
        for (int i = 0; i < n; i++) {
            run += c[i] / (double) total;
            cdf[i] = run;
        }
        cdf[n - 1] = 1.0; // guard against FP drift
    }

    public int[] sample(int draws) {
        if (draws <= 0) throw new IllegalArgumentException("draws must be > 0");
        int[] out = new int[draws];
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < draws; i++) {
            double u = rnd.nextDouble(); // [0,1)
            int idx = binarySearchCDF(u);
            out[i] = ages[idx];
        }
        return out;
    }

    public int[] getAges() {
        return Arrays.copyOf(ages, ages.length);
    }

    private int binarySearchCDF(double u) {
        int lo = 0, hi = cdf.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (u <= cdf[mid]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static void sortByAge(int[] ages, int[] counts) {
        // Simple index sort to keep arrays aligned
        Integer[] idx = new Integer[ages.length];
        for (int i = 0; i < ages.length; i++) idx[i] = i;
        Arrays.sort(idx, (i, j) -> Integer.compare(ages[i], ages[j]));
        int[] a2 = new int[ages.length];
        int[] c2 = new int[counts.length];
        for (int k = 0; k < idx.length; k++) {
            a2[k] = ages[idx[k]];
            c2[k] = counts[idx[k]];
        }
        System.arraycopy(a2, 0, ages, 0, ages.length);
        System.arraycopy(c2, 0, counts, 0, counts.length);
    }
}
