1. Measure the empirical age distribution: for each integer age a, count how many students have that age, count[a].
2. Normalize to probabilities: p[a] = count[a] / sum(count[⋅]).
3. Build a cumulative distribution (CDF) over sorted ages: cdf[i] = sum of p up to ages[i].
4. For each of the 1000 draws:
   - Sample u uniformly in [0,1).
   - Find the smallest index i with cdf[i] ≥ u (binary search).
   - Output ages[i] as the sampled age.
5. If you only have age intervals, use the interval endpoints as bins, assign each bin a probability mass, pick a bin via the CDF, then sample uniformly within that bin (round to nearest integer if needed).