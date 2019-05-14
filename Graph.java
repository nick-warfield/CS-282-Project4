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

    public Graph(BufferedReader reader) throws IOException
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
		return false;
	}
	//?? Stuck ??
/*	public void MST()
	{
		int minWeight = Integer.MAX_VALUE;
		int[][] marked = new int[vertices][vertices];
		for(int i = 0; i < vertices; i++)
		{
			LinkedList<Edge> list = adjacencyList.get(i);

			for (int j = 0; j <list.size() ; j++)
			{
                if(marked[j][i]==1)
				{
				System.out.print(" " + list.get(j).destination + " " + list.get(j).weight);
				}
				else if(list.get(j).weight < minWeight)
				{
					minWeight = list.get(j).weight;
				}
            }
			if(minWeight != Integer.MAX_VALUE)
			{
				System.out.print(" " + list.get(minWeight).destination + " " + list.get(minWeight).weight);
				marked[list.get(minWeight).destination] = true;
			}
		}
	}
	*/



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

	public void Shortest(int Node)
	{
		DijkstraRow[] dijkstra = new DijkstraRow[vertices];

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
