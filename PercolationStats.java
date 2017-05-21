import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private int size;
  private int noOfTrials;
  private double[] thresholdTracker;

     
  public PercolationStats(int n, int trials) {
    size = n;
    noOfTrials = trials;
    thresholdTracker = new double[size];
  
    for (int i = 0;i < noOfTrials;i++) {
      StdRandom.setSeed(7);
      Percolation circuit = new Percolation(size);
      int row = StdRandom.uniform(1,size);
      int col = StdRandom.uniform(1,size);
   
      while (!circuit.isOpen(row, col)) {
        circuit.open(row, col);
      }
   
      thresholdTracker[i] = (double) (circuit.numberOfOpenSites()) / (double) (n * n);
    }
  }
 
  public double mean() {
    return StdStats.mean(thresholdTracker);
  }
 
  public double stddev() {
    return StdStats.stddev(thresholdTracker);
  }
 
  public double confidenceLo() {
    return mean() - 1.96 * stddev() / Math.sqrt(noOfTrials);
  }
 
  public double confidenceHi() {
    return mean() + 1.96 * stddev() / Math.sqrt(noOfTrials);
  }
 
  public static void main(String[] args) {
    int noOfGrids = StdIn.readAllInts()[0];
    int noOfTrials = StdIn.readAllInts()[1];
  
    PercolationStats obj = new PercolationStats(noOfGrids,noOfTrials);
  
    StdOut.println("mean                         = " + obj.mean());
    StdOut.println("stddev                       = " + obj.mean());
    StdOut.println("95% confidence interval      = [" + obj.confidenceLo() + "," 
        + obj.confidenceHi() + "]"); ;
  }
}
