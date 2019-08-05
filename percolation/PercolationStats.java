import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double[] observedThresholds;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        Percolation pc;

        observedThresholds = new double[trials];

        for(int i = 0; i<trials; i++) {
            pc = new Percolation(n);
            observedThresholds[i] = (double)pc.numberOfOpenSites()/(double)(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(observedThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(observedThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }


    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + percStats.mean());
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
    }

}
