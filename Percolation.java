import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation { 
  private boolean[][] id;
  private WeightedQuickUnionUF link;
  private int numberOfOpenSites;
  private int size;
  private int virtualTop = 0;
  private int virutalBottom = 0;
  
  /**
   * Initializes an empty union–find data structure with {@code n*n} sites
   * Initializes an empty two-dimensional boolean array sites
   * Initializes virtual bottom variable and size
   * {@code 0} through {@code n-1}. Each site is initially in its own 
   * component.
   *
   * @param  n the number of sites
   * @throws IllegalArgumentException if {@code n < 0}
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new java.lang.IllegalArgumentException();
    } else {
      id = new boolean[n][n];
      link = new WeightedQuickUnionUF(n * n);
      size = n;
      virutalBottom = n * n - 1;  
    
    }
    
  }
  
  
  /**
   * Accepts row and column and returns the corresponding 
   * integer value for weighted union data structure.
   * 
   *@param row row index, col column index
   */
  public int findLinkValue(int row,int col) {
    return size * (row - 1) + (col - 1);
  }
  
  
  /**
   * Accepts row and column and opens the site if it is not already open
   * and links current opened site to the adjacent open sites .
   * 
   *@param row row index, col column index
   *@throws IndexOutOfBoundsException for invalid row and column values
   */
  public void open(int row, int col) {
    
    if (row <= 0 || col <= 0 || row > size || col > size) {
      throw new java.lang.IndexOutOfBoundsException();
    } else {
      if (! isOpen(row,col)) {
        
        id[row - 1][col - 1] = true;
        numberOfOpenSites += 1;
        int val = findLinkValue(row,col);
        
        if (row == 1) {
          link.union(val, virtualTop);
        }
        if (row == size) {
          link.union(val, virutalBottom);
        }
              
        if (row > 1 && isOpen(row - 1,col)) {
          link.union(val,findLinkValue((row - 1),col));
        }
          
        if (col > 1 && isOpen(row,col - 1)) {
          link.union(val, findLinkValue(row,(col - 1)));
        }
          
        if (row < size && isOpen(row + 1,col)) {
          link.union(val,findLinkValue((row + 1),col));
        }  
          
        if (col < size && isOpen(row,col + 1)) {
          link.union(val, findLinkValue(row,(col + 1)));
        }
      }      
    }
    
  }
  
  /**
   * Accepts row and column index and return boolean value
   * based on the site openness.
   *
   *@param row row index, col column index
   *@throws IndexOutOfBoundsException for invalid row and column values
   */
  public boolean isOpen(int row, int col) {
    if (row <= 0 || col <= 0 || row > size || col > size) {
      throw new java.lang.IndexOutOfBoundsException();
    } else {
      return id[row - 1][col - 1];
    }
  }
  
  /**
   * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input, 
   * where each integer represents some object;
   * if the sites are in different components, merge the two components
   * and print the pair to standard output.
   *
   */
  public boolean isFull(int row, int col) {
    if (row <= 0 || col <= 0 || row > size || col > size) {
      throw new java.lang.IndexOutOfBoundsException(); 
    } else {
      int val = findLinkValue(row , col);
      return link.connected(virtualTop, val);
    }
        
  }
  
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }
  
  public boolean percolates() {
    return link.connected(virtualTop, virutalBottom);
  }
  
  /**
   * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input, 
   * where each integer represents some object;
   * if the sites are in different components, merge the two components
   * and print the pair to standard output.
   *
   */
  public void printLink() {
    System.out.println("**********************************");
    
    for (int i = 1;i <= size;i++) {    
      System.out.print(padLeftSpaces(Integer.toString(i),4) + "  ");
    }
    System.out.println("\n----------------------------------");
    
    int j = 0;
    for (int i = 0;i < size * size;i++) {    
      System.out.print(padLeftSpaces(Integer.toString(link.find(i)),4) + "  ");
      if (i % size == size -  1) {
        System.out.println("| " + (j + 1) + "\n");
        j += 1;
      }
    }
  }
  
  public static String padLeftSpaces(String str, int n) {
    return String.format("%1$" + n + "s", str);
  }
  
  /**
   * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input, 
   * where each integer represents some object;
   * if the sites are in different components, merge the two components
   * and print the pair to standard output.
   *
   */
  public static void main(String[] args) {
    Percolation obj = new Percolation(4);
    obj.open(1, 1);
    obj.open(2, 3);
    obj.open(3, 3);
    obj.open(1, 3);
    obj.open(2, 2);
    obj.open(4, 3);
    obj.printLink();
    System.out.println("System percolates? " + obj.percolates());
    
  }
}
