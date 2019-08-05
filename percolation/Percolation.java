import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int n;
    private int n2;
    private WeightedQuickUnionUF sites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        this.n2 = this.n * this.n;
        sites = new WeightedQuickUnionUF(this.n2 + 1);

        for (int i = 1; i <= this.n; i++) {
            sites.union(0, i);
            sites.union(this.n2 + 1, this.n2 - this.n + i);
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

    }

    // returns the number of open sites
    public int numberOfOpenSites() {

    }

    // does the system percolate?
    public boolean percolates() {

    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
