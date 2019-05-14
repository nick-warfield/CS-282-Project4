// COMP282 Section 16304 Project 4
// Group members:
// Nicholas Warfield
// Javier Aguayo
// John Wiesenfeld

import java.io.*;
import java.util.Scanner;

public class Driver
{
	public static void main(String[] args) 
	{
		Graph graph = getUserGraph();
		boolean isConnected = graph.isConnected(), exit = false;
		while (!exit)
		{
			graph.printGraph();
			switch (menu())
			{
				case 1:
					System.out.println("Graph is " +
							(isConnected ? "" : "not ") +
							"connected");
					break;

				case 2:
					printMinSpanTree(graph, isConnected);
					break;

				case 3:
					printPath(graph);
					break;

				case 4:
					System.out.println("Quitting");
					exit = true;
					break;

				default:
					System.out.println("Invalid Input");
					break;
			}
		}
	}

	private static void prompt() { System.out.print("> "); }
	private static int menu()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Select number for input\n" +
				"1) Is Connected\n" +
				"2) Minimum Spanning Tree\n" +
				"3) Shortest Path\n" +
				"4) Quit\n");
		prompt();
		int input = sc.nextInt();
		if (input < 1 || input > 4) { input = -1; }
		return input;
	}

	private static void printMinSpanTree(Graph g, boolean isConnected)
	{
		if (isConnected)
		{
			//graph.MST();
		}
		else
		{
			System.out.println("Graph is not connected.");
		}
	}
	private static void printPath(Graph g)
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the number of the starting node (0 - " + (g.getVertices()-1) + ")");
		prompt();
		int start = reader.nextInt();
		if(start >= 0 && start < g.getVertices())
		{
			g.Shortest(start);
		} else {
			System.out.println("Error: node does not exist");
		}

	}

	private static Graph getUserGraph()
	{
		Scanner userInput = new Scanner(System.in);
		Graph graph = null;
		boolean validInput;
		do
		{
			validInput = true;
			System.out.println("Enter Name of Input File");
			prompt();
			String fileName = userInput.next();
			try
			{
				FileReader reader = new FileReader(fileName);
				graph = new Graph(new BufferedReader(reader));
				reader.close();
			}
			catch (FileNotFoundException fne)
			{
				System.out.println(fne.toString());
				validInput = false;
			}
			catch (IOException ioe)
			{
				System.out.println(ioe.toString());
				System.out.println("Exiting Program");
				System.exit(0);
			}
		} while(!validInput);
		return graph;
	}
}
