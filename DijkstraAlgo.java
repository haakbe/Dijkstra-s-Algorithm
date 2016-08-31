import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;

// Dijkstra's Algorithm performed on an undirected weighted graph
public class DijkstraAlgo {

	// Constant keeping the minimum shortest distance between two
	// vertices that can't be reached by any path
	private static final int INFINITY = 1000000;
	
	private static class Graph
	{
		private HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
		private ArrayList<Edge> allEdges = new ArrayList<Edge>();
		
		// Method that adds a vertex to the vertices hashMap 
		public void addVertex (Vertex vertex)
		{
			vertices.put(vertex.label, vertex);
		} // addVertex
		
		// Method to put a vertex into a graph in case there are duplicates
		// Also returns the element if it already exists
		public Vertex putVertex(int label)
		{
			Vertex v;
			if ((v = vertices.get(label)) == null)
			{
				v = new Vertex(label);
				addVertex(v);
			}
			return v;
		} // getVertex
		
		public Vertex getVertex(int label)
		{
			if (!vertices.containsKey(label))
				throw new IllegalArgumentException("The label you want to get "
			+ "doesn't exist in the graph. Provide a valid label!");
			return vertices.get(label);
		}
		
		// Print all the elements of the graph - vertices, edges and distances.
		// Used for debugging
		public void printGraph()
		{
			Collection<Vertex> newCollection = vertices.values();
			Iterator<Vertex> iterator = newCollection.iterator(); 
			while(iterator.hasNext())
				System.out.println(iterator.next().toString());
		} // printGraph
		
		// Print the allEdges array of the graph containing all the edges.
		// Used for debugging
		public void printEdges()
		{
			for(int i = 0; i < allEdges.size(); i++)
				System.out.println(allEdges.get(i).toString());
		} // printEdges
		
	} // Graph
	
	private static class Vertex
	{
		private int label;
        private Set<Edge> edges = new HashSet<Edge>();
        
        // Constructor of a Vertex object that has this label
        public Vertex(int label)
        {
        	this.label = label;
        } // Vertex
        
        // Method that adds an edge to the graph
        public void addEdge(Edge edge)
        {
        	edges.add(edge);
        } // addEdge
        
        // Access the label of the vertex
        public int getLabel()
        {
        	return label;
        } // getLabel
        
        // Method that returns a String representation of a vertex
        public String toString()
        {
        	String text = "";
        	if (edges.size() == 0)
        		text = "Vertex " + label + " has no edges. \n";
        	else
        	{
	        	text = "Vertex " + label + " has edges: \n";
	        	
	        	// Convert the hashset into an array of edges
	        	Edge[] array = new Edge[edges.size()];
	        	edges.toArray(array);
	
	        	// Print each edge, one by one.
	        	for(int i = 0; i < edges.size(); i++)
	        		text += array[i].toString();
        	} // else
        	return text;
        } // printVertex
        
	} // Vertex
	
	private static class Edge
	{
		private ArrayList<Vertex> margins = new ArrayList<Vertex>();
		private int distance;
		
		// Constructor method for an edge - needs to have exactly 2 vertexes and a distance.
		public Edge(Vertex first, Vertex second, int distance)
		{
			if(first == null || second == null)
				throw new IllegalArgumentException("You need to provide both vertices!" );
			
			if(distance < 0)
				throw new IllegalArgumentException("You need to provide a non-negative distance!");
			
			// Add the first and the second end of an edge to the margins array
			margins.add(first);
			margins.add(second);
			// Set the distance
			this.distance = distance;
		} // Edge
		
		// Method that provides access to the first end of the edge
		public Vertex getFirstEnd()
		{
			return margins.get(0);
		} // getFirstEnd
		
		
		// Method that provides access to the second end of the edge
		public Vertex getSecondEnd()
		{
			return margins.get(1);
		} // getSecondEnd
		
		// Method that returns a String representation of an Edge
		public String toString()
		{
			if(margins.isEmpty())
				return "This edge is empty.";
			else
				return "(" + margins.get(0).getLabel() 
						+ "," +  margins.get(1).getLabel() 
						+ ") with distance = " 
						+ distance + "\n";
		} // printEdge
	} // Edge
	
	// Method that creates all the necessary vectors and variables needed by Dijkstra's Algorithm
	// It also calls its method and prepares the ground for it, keeping track of the work done. 
	private static void computeShortestPath(Graph g, Vertex sourceVertex)
	{		
		// Create a HashMap that maps a vertex to an integer = the shortest path from source vertex.
		// In this way we remember the shortest path between vertices.
		HashMap<Vertex, Integer> shortestPath = new HashMap<Vertex, Integer>();
		
		// Create a HashSet that keeps track of the vertices visited so far.
		HashSet<Vertex> X = new HashSet<Vertex>(1);
		X.add(sourceVertex);
		
		// Iterate through the vertices of the graph to set the shortestPath map.
		Collection<Vertex> newCollection = g.vertices.values();
		Iterator<Vertex> iterator = newCollection.iterator();
		
		
		// Initialize all shorestPath entries to be INFINITY
		while(iterator.hasNext())
			shortestPath.put(iterator.next(), INFINITY);
		
		// Set the source vertex to have 0 as the shortest distance to itself.
		shortestPath.put(sourceVertex, 0);

		// Call Dijkstra's Algorithm to compute the Shortest path
		dijkstra(g, sourceVertex, shortestPath, X);

		
	} // computeShortestPath
	
	// Dijkstra's Algorithm
	private static void dijkstra(Graph g, Vertex sourceVertex, 
			HashMap<Vertex, Integer> shortestPath,
			HashSet<Vertex> X)
	{
		// As long as there are unvisited vertices
		while(X.size() != g.vertices.size())
		{
			int minimum = INFINITY;
			Vertex wFinal = null;
			
			// Loop through the edges of the graph
			for(int i = 0; i < g.allEdges.size(); i++)
			{
				// Get the edge and retain its two ends in some Vertex variables.
				Edge edge = g.allEdges.get(i);
				Vertex v1 = edge.getFirstEnd();
				Vertex v2 = edge.getSecondEnd();
				
				// If one end in in X and the other is not
				if(X.contains(v1) && !(X.contains(v2)))
				{
					// Compute the greedy criterion = the shortest path to first end + distance to second end
					int greedyCriterion = shortestPath.get(v1) + edge.distance;
					
					// Keep only the minimum greedy criterion from X to V - X
					if(greedyCriterion < minimum)
					{
						minimum = greedyCriterion;
						wFinal = v2;						
					} // if
				} // if
			} // for
			
			// Add the current found vertex to the set of visited vertices.
			X.add(wFinal);
			// Replace the current shortest path to a vertex which is not in X with the minimum found.
			shortestPath.put(wFinal, minimum);
		} // while
		
		// Print the shortest Path found
		printShortest(g,shortestPath);
	} // dijkstra
	
	// Prints the Shortest Path found between source vertex and all other vertices in graph
	private static void printShortest(Graph g, HashMap<Vertex, Integer> shortestPath)
	{
		for(int i = 0; i < g.vertices.size(); i++)
		{
			Vertex v1 = g.getVertex(i + 1);
			System.out.println("Shorpath for vertex " + v1.label + " = " + shortestPath.get(v1));
		} // for
		System.out.println();
	} // printShortest
	
	// Main method, constructing the graph, calling the method that computes the shortest distances
	public static void main(String[] arg)throws IOException
	{
		
		File file = new File(System.getProperty("user.dir") + "/dijkstra.txt");
		BufferedReader input = new BufferedReader(new FileReader(file));
		
		Graph graph = new Graph();
		
		try 
		{
			// Read a line of text from the file
		    String line = input.readLine();
		    
		    while (line != null) 
		    {
		    	// Split the line of text into a vector of Strings
		    	// The regex is the tab between ints
		    	String[] vector = line.split("\t");
		    	
		    	// Extract the first vertex on this line and add it to the graph
		    	int labelVertex1 = Integer.parseInt(vector[0]);
		    	
		    	// Create a vertex holding the new vertex and add it to the graph
		    	Vertex v1 = graph.putVertex(labelVertex1);
		    	
		    	if(vector.length > 1)
		    	{
			    	for(int i=1; i < vector.length; i++)
			    	{
			    		// Split the i-th entry of vector into outgoing edge and its distance
			    		String[] splitDistance = vector[i].split(",");
			    		int labelVertex2 = Integer.parseInt(splitDistance[0]);
			    		int distance = Integer.parseInt(splitDistance[1]);

			    		Vertex v2 = graph.putVertex(labelVertex2);
			    		// Create an edge having the properties read.
			    		
			    		Edge edge = new Edge(v1, v2, distance);
			    		
			    		v1.addEdge(edge);
			    		graph.allEdges.add(edge);		    		
			    	} // for
		    	} // if
		    	
		    	// Read a new line of text
		    	line = input.readLine();		    	
		    } // while			
		} // try
		finally
		{
			input.close();
		} // finally
		
		System.out.println("The representation of the graph read is:\n");
		graph.printGraph();
		
		System.out.println("The edges of the graph are:\n");
		graph.printEdges();
		
		// Initialize the source vertex
		Vertex sourceVertex = graph.getVertex(1);
		
		
		// Compute the shortest path from source vertex to all other vertices
		computeShortestPath(graph, sourceVertex);
		
	} // main		
		 
} // DijkstraAlgo
