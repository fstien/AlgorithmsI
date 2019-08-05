import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int n;
    private int n2;
    private WeightedQuickUnionUF sites;
    private boolean[] openSites;
    private int openSiteCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if(n <= 0) {
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

        while(!percolates()) {
            int[] siteToOpen = randomClosedSite();
            open(siteToOpen[0], siteToOpen[1]);
        }
    }

    private int[] randomSite() {
        return new int[] { StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1)};
    }

    private int[] randomClosedSite() {
        int[] site = randomSite();

        while(isOpen(site[0], site[1])) {
            site = randomSite();
        }

        return site;
    }

    private void validateRange(int row, int col) {
        if(row > this.n || col > this.n
           || row < 1 || col <1) {
            throw new IllegalArgumentException("Argument is out of range.");
        }
    }

    private int index(int row, int col) {
        return n*(row-1) + col;
    }

    private boolean inRange(int i) {
        return i >= 1 && i <= n;
    }

    private ArrayList<Integer> adjacentSites(int row, int col) {
        ArrayList<Integer> adjSites = new ArrayList<>();

        if (inRange(row + 1)) {
            adjSites.add(index(row + 1, col));
        }
        if(inRange(col+1)) {
            adjSites.add(index(row, col + 1));
        }
        if(inRange(row-1)) {
            adjSites.add(index(row - 1, col));
        }
        if(inRange(col-1)) {
            adjSites.add(index(row, col - 1));
        }

        return adjSites;
    }

    private ArrayList<Integer> openAdjSites(int row, int col) {
        ArrayList<Integer> adjSites = adjacentSites(row, col);
        ArrayList<Integer> opAdjSites = new ArrayList<>();

        for(int i = 0; i < adjSites.size(); i++) {
            if(openSites[adjSites.get(i)]) {
                opAdjSites.add(adjSites.get(i));
            }
        }

        return opAdjSites;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRange(row, col);

        if(!isOpen(row, col)) {
            openSiteCount++;
            openSites[index(row, col)] = true;

            ArrayList<Integer> openAdjacentSites = openAdjSites(row, col);

            for(int i = 0; i < openAdjacentSites.size(); i++) {
                sites.union(index(row, col), openAdjacentSites.get(i));
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
        return sites.connected(0, index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.connected(0, this.n2 + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation pc = new Percolation(Integer.parseInt(args[0]));
    }
}
