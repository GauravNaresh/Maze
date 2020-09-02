package project5;

public class DisjSets {
	
	
	 public DisjSets( int numElements )
	    {
	        s = new int [ numElements ];
	        for( int i = 0; i < s.length; i++ )
	            s[ i ] = -1;
	    }

	 public void union( int root1, int root2 )
	    {
	        s[ root2 ] = root1;
	    }

	    /**
	     * Perform a find.
	     * Error checks omitted again for simplicity.
	     * @param x the element being searched for.
	     * @return the set containing x.
	     */
	    public int find( int x )
	    {
	        if( s[ x ] < 0 )
	            return x;
	        else
	            return find( s[ x ] );
	    }
	    private int [ ] s;

}
