
public class Maze extends Board {
	
	private ExtendedGraph graph;
	private DisjointSets set;
  		
    public Maze( int rows, int cols ) {
    	super(rows,cols);
    	set = new DisjointSets(rows*cols);
    	graph = new ExtendedGraph();

    }
    
    public void create() { //not completed
    	 setChanged();
    	 notifyObservers();
    	 
    	 int knockedWalls = 0;
    	 while(knockedWalls < (maxCell-1))
    }
    
    public void search() { //not completed
    	 graph.unweighted(0);
    	 List<Integer> list = graph.getPath(maxCell-1);
    	 for(Integer i : list){
    		 setChanged();
    		 notifyObservers(i);
    	 }
    }
    
//    ...
}
