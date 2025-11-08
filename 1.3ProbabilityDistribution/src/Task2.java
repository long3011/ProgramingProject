import java.util.Arrays;

public class Task2 {
    public static void main(String[] args) {
        // Scenario A: Typical student group around 19-24
        runScenario(
                "A: unimodal 19-24",
                new int[]{19, 20, 21, 22, 23, 24},
                new int[]{12, 35, 41, 28, 14, 6}
        );

        // Scenario B: Bimodal (younger and older clusters)
        runScenario(
                "B: bimodal",
                new int[]{18, 19, 20, 21, 22, 27, 28, 29, 30},
                new int[]{15, 30, 25, 10, 5, 5, 15, 20, 25}
        );

        // Scenario C: Uniform across 18-30
        runScenario(
                "C: uniform 18-30",
                range(18, 30),
                fill(30 - 18 + 1, 1)
        );
    }

    private static void runScenario(String name, int[] ages, int[] counts) {
        System.out.println("------------------------------------------------------------");
        System.out.println("Scenario " + name);

        AgeSampler sampler = new AgeSampler(ages, counts);
        final int N = 1000;
        int[] draws = sampler.sample(N);

        // Compute observed frequencies aligned with sorted ages from sampler
        int[] sortedAges = sampler.getAges();
        int[] obsFreq = new int[sortedAges.length];
        for (int x : draws) {
            int i = Arrays.binarySearch(sortedAges, x);
            if (i >= 0) obsFreq[i]++;
        }

        // Expected probabilities from counts
        double total = 0.0;
        for (int c : counts) total += c;
        // Sort the original counts to align with sortedAges for fair comparison
        int[] countsAligned = alignCounts(ages, counts, sortedAges);

        // Print summary
        double tvd = 0.0; // total variation distance
        System.out.printf("%6s  %10s  %10s  %8s%n", "Age", "Exp p", "Obs p", "Obs n");
        for (int i = 0; i < sortedAges.length; i++) {
            double expP = countsAligned[i] / total;
            double obsP = obsFreq[i] / (double) N;
            tvd += Math.abs(expP - obsP);
            System.out.printf("%6d  %10.3f  %10.3f  %8d%n",
                    sortedAges[i], expP, obsP, obsFreq[i]);
        }
        tvd *= 0.5;
        System.out.printf("N = %d, total variation distance = %.4f%n", N, tvd);
    }

    private static int[] alignCounts(int[] origAges, int[] origCounts, int[] sortedAges) {
        int[] aligned = new int[sortedAges.length];
        for (int i = 0; i < origAges.length; i++) {
            int idx = Arrays.binarySearch(sortedAges, origAges[i]);
            if (idx >= 0) aligned[idx] = origCounts[i];
        }
        return aligned;
    }

    private static int[] range(int start, int endInclusive) {
        int n = endInclusive - start + 1;
        int[] r = new int[n];
        for (int i = 0; i < n; i++) r[i] = start + i;
        return r;
    }

    private static int[] fill(int n, int value) {
        int[] a = new int[n];
        Arrays.fill(a, value);
        return a;
    }
}
