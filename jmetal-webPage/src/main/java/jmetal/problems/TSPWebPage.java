package jmetal.problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.JMetalException;

@SuppressWarnings("serial")
public class TSPWebPage extends AbstractIntegerPermutationProblem {
	  private int         numberOfCities ;
	  private double [][] distanceMatrix ;

	  /**
	   * Creates a new problem instance
	   */
	  public TSPWebPage(String distanceFile) throws IOException {
	    distanceMatrix = readProblem(distanceFile) ;

	    setNumberOfVariables(numberOfCities);
	    setNumberOfObjectives(1);
	    setName("TSP");
	  }

	  /** Evaluate() method */
	  public void evaluate(PermutationSolution<Integer> solution){
	    double fitness1   ;

	    fitness1 = 0.0 ;

	    for (int i = 0; i < (numberOfCities - 1); i++) {
	      int x ;
	      int y ;

	      x = solution.getVariableValue(i) ;
	      y = solution.getVariableValue(i+1) ;

	      fitness1 += distanceMatrix[x][y] ;
	    }
	    int firstCity ;
	    int lastCity  ;

	    firstCity = solution.getVariableValue(0) ;
	    lastCity = solution.getVariableValue(numberOfCities - 1) ;

	    fitness1 += distanceMatrix[firstCity][lastCity] ;

	    solution.setObjective(0, fitness1);
	  }

	  private double [][] readProblem(String file) throws IOException {
	    double [][] matrix = null;

	    BufferedReader br = new BufferedReader(new FileReader(file));

	    StreamTokenizer token = new StreamTokenizer(br);
	    try {
	      boolean found ;
	      found = false ;

	      token.nextToken();
	      while(!found) {
	        if ((token.sval != null) && ((token.sval.compareTo("DIMENSION") == 0)))
	          found = true ;
	        else
	          token.nextToken() ;
	      }

	      token.nextToken() ;
	      token.nextToken() ;

	      numberOfCities =  (int)token.nval ;

	      matrix = new double[numberOfCities][numberOfCities] ;

	      // Find the string SECTION  
	      found = false ;
	      token.nextToken();
	      while(!found) {
	        if ((token.sval != null) &&
	            ((token.sval.compareTo("SECTION") == 0)))
	          found = true ;
	        else
	          token.nextToken() ;
	      }

	      double [] c = new double[2*numberOfCities] ;

	      for (int i = 0; i < numberOfCities; i++) {
	        token.nextToken() ;
	        int j = (int)token.nval ;

	        token.nextToken() ;
	        c[2*(j-1)] = token.nval ;
	        token.nextToken() ;
	        c[2*(j-1)+1] = token.nval ;
	      } // for

	      double dist ;
	      for (int k = 0; k < numberOfCities; k++) {
	        matrix[k][k] = 0;
	        for (int j = k + 1; j < numberOfCities; j++) {
	          dist = Math.sqrt(Math.pow((c[k*2]-c[j*2]),2.0) +
	              Math.pow((c[k*2+1]-c[j*2+1]), 2));
	          dist = (int)(dist + .5);
	          matrix[k][j] = dist;
	          matrix[j][k] = dist;
	        }
	      }
	    } catch (Exception e) {
	      new JMetalException("TSP.readProblem(): error when reading data file " + e);
	    }
	    return matrix;
	  }

	  @Override public int getPermutationLength() {
	    return numberOfCities ;
	  }
}
