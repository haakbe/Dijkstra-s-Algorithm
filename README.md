# Dijkstra-s-Algorithm

Dijkstra's Algorithm is an efficient way of computing the shortest path between two vertices in a graph. 

Conceived by the Dutch computer scientist Dijkstra in 1956, my implementation of this algorithm takes a O(m x n) running time (where m = no. of edges and n = no. of vertices) but it could be improved to O(m + n) using a min priority queue.

# Input

The input should consist of a file containing an adjacency list representation of an undirected weighted graph with n vertices labeled 1 to n. Each row consists of the node tuples that are adjacent to that particular vertex along with the length of that edge.

e.g on 8th row we have 8  4,100 => this means that the 8th node is adjacent with node 4 and the edge between them has a distance of 100

# Output

The output cosists of the n-th distances from node 1 (could be any other, just modify it on the main method) to every other node in the graph. 

# Clarifications

- If there is no path between a vertex v and source vertex, we'll define the shortest-path distance between 1 and v to be 1000000.

- Distance between v and itself is 0.

- If there are n nodes in the graph, they should have labels from 1 to n.
