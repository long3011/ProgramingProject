Example system: Bank branch (service desks + queueing + ATM)

- Variability
  - Customer arrival times (bursty morning peaks, lunch-time, end-of-day).

  - Transaction durations (simple deposit vs. loan consultation).

    - Staff availability (breaks, shift changes).

- Complexity

    - Combinatorial: multiple service desks, each capable of handling many transaction types; assignment rules create many possible combinations.

    - Dynamic: delays cascade (if teller is slow, queue grows; receptionist redirects customers), and feedback can cause different behavior at different times.

- Interconnectedness

    - Tellers, ATMs, back-office processing, and appointment scheduling interact — e.g., slow back-office verification increases teller service time.

    - Policy changes (e.g., one teller shifts to loan consultations) affect queue lengths everywhere.

- Why simulation helps

    - Hard to analytically derive waiting times given the variability; simulation can estimate customer wait distribution, resource utilization, and test scheduling policies.