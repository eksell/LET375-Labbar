package maxsum;


import java.util.Random;

public final class MaxSumOneDimension
{
    static private int seqStart = 0;
    static private int seqEnd = -1;

    /**
     * Cubic maximum contiguous subsequence sum algorithm.
     * seqStart and seqEnd represent the actual best sequence.
     */
    public static int maxSubSum1( int [ ] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++ )
            for( int j = i; j < a.length; j++ )
            {
                int thisSum = 0;

                for( int k = i; k <= j; k++ )
                    thisSum += a[ k ];

                if( thisSum > maxSum )
                {
                    maxSum   = thisSum;
                    seqStart = i;
                    seqEnd   = j;
                }
            }

        return maxSum;
    }

    /**
     * Quadratic maximum contiguous subsequence sum algorithm.
     * seqStart and seqEnd represent the actual best sequence.
     */
    public static int maxSubSum2( int [ ] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++ )
        {
            int thisSum = 0;
            for( int j = i; j < a.length; j++ )
            {
                thisSum += a[ j ];

                if( thisSum > maxSum )
                {
                    maxSum = thisSum;
                    seqStart = i;
                    seqEnd   = j;
                }
            }
        }

        return maxSum;
    }

    /**
     * Linear-time maximum contiguous subsequence sum algorithm.
     * seqStart and seqEnd represent the actual best sequence.
     */
    public static int maxSubSum3( int [ ] a )
    {
        int maxSum = 0;
        int thisSum = 0;

        for( int i = 0, j = 0; j < a.length; j++ )
        {
            thisSum += a[ j ];

            if( thisSum > maxSum )
            {
                maxSum = thisSum;
                seqStart = i;
                seqEnd   = j;
            }
            else if( thisSum < 0 )
            {
                i = j + 1;
                thisSum = 0;
            }
        }

        return maxSum;
    }

    /**
     * Recursive maximum contiguous subsequence sum algorithm.
     * Finds maximum sum in subarray spanning a[left..right].
     * Does not attempt to maintain actual best sequence.
     */
    private static int maxSumRec( int [ ] a, int left, int right )
    {
        int maxLeftBorderSum = 0, maxRightBorderSum = 0;
        int leftBorderSum = 0, rightBorderSum = 0;
        int center = ( left + right ) / 2;

        if( left == right )  // Base case
            return a[ left ] > 0 ? a[ left ] : 0;

        int maxLeftSum  = maxSumRec( a, left, center );
        int maxRightSum = maxSumRec( a, center + 1, right );

        for( int i = center; i >= left; i-- )
        {
            leftBorderSum += a[ i ];
            if( leftBorderSum > maxLeftBorderSum )
                maxLeftBorderSum = leftBorderSum;
        }

        for( int i = center + 1; i <= right; i++ )
        {
            rightBorderSum += a[ i ];
            if( rightBorderSum > maxRightBorderSum )
                maxRightBorderSum = rightBorderSum;
        }

        return max3( maxLeftSum, maxRightSum,
                     maxLeftBorderSum + maxRightBorderSum );
    }

    /**
     * Return maximum of three integers.
     */
    private static int max3( int a, int b, int c )
    {
        return a > b ? a > c ? a : c : b > c ? b : c;
    }

    /**
     * Driver for divide-and-conquer maximum contiguous
     * subsequence sum algorithm.
     */
    public static int maxSubSum4( int [ ] a )
    {
        return a.length > 0 ? maxSumRec( a, 0, a.length - 1 ) : 0;
    }

    public static void getTimingInfo( int n, int alg )
    {
        int [] test = new int[ n ];

        long startTime = System.currentTimeMillis( );
        long totalTime = 0;

        int i;
        for( i = 0; totalTime < 4000; i++ )
        {
            for( int j = 0; j < test.length; j++ )
                test[ j ] = rand.nextInt( 100 ) - 50;

            switch( alg )
            {
              case 1:
                maxSubSum1( test );     // O(N^3)
                break;
              case 2:
                maxSubSum2( test );     // O(N^2)
                break;
              case 3:
                maxSubSum3( test );     // O(N)
                break;
              case 4:
                maxSubSum4( test );     // O(NlogN)
                break;
            }

            totalTime = System.currentTimeMillis( ) - startTime;
        }

        System.out.println( "Algorithm #" + alg + "\t"
             + "N = " + test.length
             + ", time = " + ( totalTime * 1000 / i ) + " microsec" );
    } 
    
    private static Random rand = new Random( );
    
    /**
     * Simple test program.
     */
    public static void main( String [ ] args )
    {
        int a[ ] = { 4, -3, 5, -2, -1, 2, 6, -2 };
        int maxSum;

        maxSum = maxSubSum3( a );
        System.out.println( "Max sum is " + maxSum + "; it goes"
                       + " from " + seqStart + " to " + seqEnd );
        maxSum = maxSubSum4( a );
        System.out.println( "Max sum is " + maxSum + "; it goes"
                       + " from " + seqStart + " to " + seqEnd );
        maxSum = maxSubSum2( a );
        System.out.println( "Max sum is " + maxSum + "; it goes"
                       + " from " + seqStart + " to " + seqEnd );
        maxSum = maxSubSum1( a );
        System.out.println( "Max sum is " + maxSum );
        
          // Get some timing info
        final int[] testorder = new int[] {3,4,2,1};
        for( int n = 10; n <= 1000000; n *= 10 ) {
            for ( int alg : testorder )
            {
                if( alg == 1 && n > 50000 )
                    continue;    
                getTimingInfo( n, alg );
            }
            System.out.println();
        }
    }
}

