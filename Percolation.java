import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int[][] id;
	private WeightedQuickUnionUF link;
	private int numberOfOpenSites;
	private int size;
	private int virtualTop = 0;
	private int virutalBottom = 0;
	
	public Percolation(int n)                // create n-by-n grid, with all sites blocked
	{
		if (n<= 0){
			throw new java.lang.IllegalArgumentException();
		}
		else{
			id = new int[n][n];
			link = new WeightedQuickUnionUF(n*n);
			size = n;
			virutalBottom = n * n - 1;			
		}
		
	}
	
	public void open(int row, int col)    // open site (row, col) if it is not open already
	{
		
		if(row + 1 <=0 || col + 1 <=0 || row > size || col > size){
			throw new java.lang.IndexOutOfBoundsException();
		}
		else{
			if(! isOpen(row,col)){
				
				id[row][col] = 1;
				numberOfOpenSites += 1;
				int val = size * row+col;
				
				if (row == 0) {
		            link.union(val, virtualTop);
		        }
		        if (row == size-1) {
		            link.union(val, virutalBottom);
		        }
							
				if(row > 0 && isOpen(row-1,col)){
					link.union(val,size *(row-1)+col);
				}
					
				if (col > 0 && isOpen(row,col-1)){
					link.union(val, size *row+(col-1));
				}
					
				if (row < size-1 && isOpen(row+1,col)){
					link.union(val,size*(row+1)+col);
				}	
					
				if (col < size-1 && isOpen(row,col+1)){
					link.union(val, size*row+(col+1));
				}
			}			
		}
		
	}
	
	public boolean isOpen(int row, int col)  // is site (row, col) open?
	{
		if(row + 1 <=0 || col + 1 <=0 || row > size || col > size){
			throw new java.lang.IndexOutOfBoundsException();
		}
		else{
			return id[row][col] != 0;
		}
	}
	
	public boolean isFull(int row, int col)  // is site (row, col) full?
	{
		if(row + 1 <=0 || col + 1 <=0 || row > size + 1 || col > size){
			throw new java.lang.IndexOutOfBoundsException();
		}
		else{
			int val = size * row + col;
			return link.connected(virtualTop, val);
		}
				
	}
	
	public int numberOfOpenSites()       // number of open sites
	{
		return numberOfOpenSites;
	}
	
	public boolean percolates()              // does the system percolate?
	{
		
		return link.connected(virtualTop, virutalBottom);
	}
	
	public void printLink(){
		System.out.println("**********************************");
		
		for(int i=0;i<size;i++){		
			System.out.print(padLeftSpaces(Integer.toString(i),4) + "    ");
		}
		System.out.println("\n----------------------------------");
		
		int j = 0;
		for(int i=0;i<size*size;i++){		
			System.out.print(padLeftSpaces(Integer.toString(link.find(i)),4) + "    ");
			if (i % size == size-1){
				System.out.println("| " + j + "\n");
				j+=1;
			}
		}
	}
	
	public static String padLeftSpaces(String str, int n) {
	    return String.format("%1$" + n + "s", str);
	  }
	
	public static void main(String[] args)   // test client (optional)
	{
		Percolation obj = new Percolation(4);
		//obj.printLink();
		obj.open(0, 1);
		obj.open(2, 3);
//		obj.open(3, 3);
		obj.open(1, 1);
		obj.open(1, 4);
		obj.open(2, 2);
//		System.out.println(obj.id[0][1]);
//		System.out.println(obj.id[0][2]);
//		System.out.println(obj.id[1][1]);
		System.out.println(obj.isFull(2, 3));
//		System.out.println(obj.numberOfOpenSites());
		obj.printLink();
		System.out.println(obj.isOpen(2, 3));
		System.out.println(obj.percolates());
		
	}
}
