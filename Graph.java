// COMP282 Section 16304 Project 4
// Group members:
// Nicholas Warfield
// Javier Aguayo
// John Wiesenfeld
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.*;

public class Graph 
{
    private int vertices;
    private ArrayList<LinkedList<Edge>> adjacencyList;
    private DijkstraRow[] dijkstra;

    public Graph(BufferedReader reader) throws IOException, Exception
	{
		String line;
		int neighbors, src, dest, weight;
		line = reader.readLine();
		this.vertices = Integer.parseInt(line);
		adjacencyList = new ArrayList<LinkedList<Edge>>(vertices);

		for (int v = 0; v < vertices; v++) 
		{
			LinkedList<Edge> edgeList = new LinkedList<Edge>();
			adjacencyList.add(v, edgeList);
			line = reader.readLine();
			String[] token = line.split(" ");
			neighbors = Integer.parseInt(token[0]);
			
			for(int i = 1; i <= neighbors*2; i=i+2)
			{
				dest = Integer.parseInt(token[i]);
				weight = Integer.parseInt(token[i+1]);
				addEdge(v, dest, weight);
			}
		}
	}

    private void addEdge(int source, int destination, int weight) 
	{
        Edge edge = new Edge(source, destination, weight);
        adjacencyList.get(source).add(edge);
    }

    public void printGraph()
	{
        System.out.println(vertices);
		for (int i = 0; i <vertices ; i++) 
		{
			LinkedList<Edge> list = adjacencyList.get(i);
            System.out.print(list.size());
			
			for (int j = 0; j <list.size() ; j++) 
			{
                System.out.print(" " + list.get(j).destination + " " + list.get(j).weight);
            }
			System.out.println();
        }
    }

	public boolean isConnected()
	{
		return Shortest(0);
	}
	
	private int findMinCost(int keyWeight[], Boolean mstSet[]) 
    { 
        // Initialize min value 
        int min = Integer.MAX_VALUE, min_index=-1; 
  
        for (int v = 0; v < vertices; v++) 
            if (mstSet[v] == false && keyWeight[v] < min) 
            { 
                min = keyWeight[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    }

    private void printMST(int parent[], int neighbors[]) 
    { 
		System.out.println(vertices);
		for (int i = 0; i <vertices ; i++) 
		{
			System.out.print(neighbors[i]);
			
			for (int j = 0; j <adjacencyList.get(i).size() ; j++) 
			{
                int count = 0;
				while(count < vertices)
				{
					if(parent[count] == i && adjacencyList.get(i).get(j).destination == count )
					{
						System.out.print(" " + adjacencyList.get(i).get(j).destination + " " + adjacencyList.get(i).get(j).weight);
					}
					count++;
				}
				if(adjacencyList.get(i).get(j).destination == parent[i])
				{
					System.out.print(" " + adjacencyList.get(i).get(j).destination + " " + adjacencyList.get(i).get(j).weight);

				}
			}
			System.out.println();
        }
        System.out.println();
	} 
  
    public void primMST() 
    { 
        int parent[] = new int[vertices]; 
        int neighbors[] = new int[vertices]; 
  
        // Key values used to pick minimum weight edge in cut 
        int keyWeight[] = new int [vertices]; 
  
        // To represent set of vertices not yet included in MST 
        Boolean mstSet[] = new Boolean[vertices]; 
  
        // Initialize all keys as INFINITE 
        for (int i = 0; i < vertices; i++) 
        { 
            keyWeight[i] = Integer.MAX_VALUE; 
            mstSet[i] = false; 
        } 
  
        // Always include first 1st vertex in MST. 
        keyWeight[0] = 0;  
        parent[0] = -1; 
  
        for (int count = 0; count < vertices-1; count++) 
        { 
            // Pick thd minimum key vertex from the set of vertices 
            // not yet included in MST 
            int u = findMinCost(keyWeight, mstSet); 

            // Add the picked vertex to the MST Set 
            mstSet[u] = true; 
            LinkedList<Edge> list = adjacencyList.get(u);

            for (int v = 0; v < list.size(); v++) 
			{
				int dest = list.get(v).destination;
                // Update the key only if dest weight is smaller than key[dest] 
               	if(mstSet[dest] == false && list.get(v).weight < keyWeight[dest])
				{
					parent[dest] = u; 
                    keyWeight[dest] = list.get(v).weight; 
				}
			}
        } 
		neighbors = checkEdges(parent);
        printMST(parent, neighbors); 
    }
	private int[] checkEdges(int[] parents)
	{
		int temp[] = new int[vertices];
		for (int i = 0; i < parents.length; i++)
		{
			if (parents[i] != -1)
			{
				temp[parents[i]]++;
				temp[i]++;
			}				
		}
		return temp;
	}

	private class Edge
	{
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) 
		{
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    //This method essentially updates the class variable 'dijkstra', also returns false if any
	//node is unreachable from the node specified (i.e. the graph is not connected)
	private boolean Shortest(int Node)
	{
		dijkstra = new DijkstraRow[vertices];
		boolean connected = true;

		for(int i = 0; i < dijkstra.length; i++)
		{
			dijkstra[i] = new DijkstraRow();
		}

		//set starting node cost to 0
		dijkstra[Node].cost = 0;

		//loop through all vertices
		for(int i = 0; i < dijkstra.length; i++)
		{
			//find lowest cost node so far
			int current = findMinCost(dijkstra);
			if(current == Integer.MAX_VALUE) return false;
			//mark this node visited
			dijkstra[current].markVisited();
			//loop through neighboring nodes
			for(int j = 0; j < adjacencyList.get(current).size(); j++)
			{
				int neighbor = adjacencyList.get(current).get(j).destination;
				if(!dijkstra[neighbor].visited)
				{
					int CurrentCostToNeighbor = dijkstra[neighbor].cost;
					int CostToCurrent = dijkstra[current].cost;
					int PossibleNewCost = CostToCurrent + adjacencyList.get(current).get(j).weight;

					if(PossibleNewCost < CurrentCostToNeighbor)
					{
						dijkstra[neighbor].cost = PossibleNewCost;
						dijkstra[neighbor].previous = current;
					}
				}
			}
		}
		for(int i = 0; i < dijkstra.length; i++)
		{
			if(dijkstra[i].cost == Integer.MAX_VALUE) connected = false;
		}
		return connected;
	}

	public void printShortest(int Node)
	{
		Shortest(Node);
		//printing
		System.out.println();
		for(int i = 0; i < dijkstra.length; i++)
		{
			if(dijkstra[i].cost < Integer.MAX_VALUE)
			{
				System.out.print(i + ": (" + dijkstra[i].cost + ") ");
				print(i, dijkstra);
				System.out.println();
			} else {
				System.out.print(i + ": (Infinity) ");
				System.out.println();
			}
		}
		System.out.println();
	}
	//recursive print function
	private void print(int i, DijkstraRow[] d)
	{
		if(d[i].previous == Integer.MAX_VALUE)
		{
			System.out.print(i);
			return;
		}
		print(d[i].previous, d);
		System.out.print(" -> " + i);
	}

	private int findMinCost(DijkstraRow[] d)
	{
		int min = Integer.MAX_VALUE;
		int current = Integer.MAX_VALUE;
		for(int i = 0; i < d.length; i++)
		{
			if(d[i].cost < min && !d[i].visited)
			{
				current = i;
				min = d[i].cost;
			}
		}
		return current;
	}
	
	private class DijkstraRow
	{
		boolean visited;
		int cost;
		int previous;

		public DijkstraRow()
		{
			visited = false;
			cost = Integer.MAX_VALUE;
			previous = Integer.MAX_VALUE;
		}
		public void markVisited()
		{
			visited = true;
		}
	}

	public int getVertices()
	{
		return vertices;
	}
}
