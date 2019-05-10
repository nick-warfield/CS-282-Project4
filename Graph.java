// COMP282 Section 16304 Project 4
// Group members:
// Nicholas Warfield
// Javier Aguayo
// John Wiesenfeld
import java.util.LinkedList;
import java.io.*;

public class Graph 
{
    private int vertices;
    private LinkedList<Edge>[] adjacencyList;

    public Graph(BufferedReader reader) throws IOException
	{
		String line;
		int neighbors, src, dest, weight;
		line = reader.readLine();
		this.vertices = Integer.parseInt(line);
		adjacencyList = new LinkedList[vertices];

		for (int v = 0; v < vertices; v++) 
		{
			adjacencyList[v] = new LinkedList<>();
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
        adjacencyList[source].add(edge); 
    }

    public void printGraph()
	{
        System.out.println(vertices);
		for (int i = 0; i <vertices ; i++) 
		{
			LinkedList<Edge> list = adjacencyList[i];
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
	public void MST()
	{
		int minWeight = Integer.MAX_VALUE; 
		int[][] marked = new int[vertices][vertices];
		for(i = 0; i < vertices; i++)
		{
			LinkedList<Edge> list = adjacencyList[i];
			
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
			if(minWeight != MAX_VALUE)
			{
				System.out.print(" " + list.get(minWeight).destination + " " + list.get(minWeight).weight);
				marked[list.get(minWeight).destination] = true;
			}
		}
	}
	
	public void Shortest()
	{
		
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
}
