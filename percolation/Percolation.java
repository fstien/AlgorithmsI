import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private final int n;
    private final int n2;
    private final WeightedQuickUnionUF sites;
    private boolean[] openSites;
    private int openSiteCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be negative.");
        }

        this.n = n;
        this.n2 = this.n * this.n;
        sites = new WeightedQuickUnionUF(this.n2 + 2);
        openSites = new boolean[this.n2 + 2];

        for (int i = 1; i <= this.n; i++) {
            sites.union(0, i);
            sites.union(this.n2 + 1, this.n2 - this.n + i);
        }
    }

    private void validateRange(int row, int col) {
        if (row > this.n || col > this.n
           || row < 1 || col < 1) {
            throw new IllegalArgumentException("Argument is out of range.");
        }
    }

    private int index(int row, int col) {
        return n*(row-1) + col;
    }

    private boolean inRange(int i) {
        return i >= 1 && i <= n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRange(row, col);

        if (!isOpen(row, col)) {
            openSiteCount++;
            openSites[index(row, col)] = true;

            if (inRange(row + 1)) {
                if (openSites[index(row + 1, col)]) {
                    sites.union(index(row, col), index(row+1, col));
                }
            }
            if (inRange(col + 1)) {
                if (openSites[index(row, col + 1)]) {
                    sites.union(index(row, col), index(row, col + 1));
                }
            }
            if (inRange(row - 1)) {
                if (openSites[index(row - 1, col)]) {
                    sites.union(index(row, col), index(row - 1, col));
                }
            }
            if (inRange(col - 1)) {
                if (openSites[index(row, col - 1)]) {
                    sites.union(index(row, col), index(row, col - 1));
                }
            }

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRange(row, col);
        return openSites[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRange(row, col);
        return isOpen(row, col) && sites.connected(0, index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.connected(0, this.n2 + 1);
    }
}
