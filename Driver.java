// COMP282 Section 16304 Project 3
// Group members:
// Nicholas Warfield
// Javier Aguayo
// John Wiesenfeld
import java.io.*;

public class Driver
{
	public static void main(String[] args) 
	{
        try{
			FileReader fr = new FileReader("input.txt");
			try{
			BufferedReader br = new BufferedReader(fr);
			Graph h = new Graph(br);
			h.printGraph();
			}
			catch(Exception IOException){
				System.out.println("Error opening input file");
				System.exit(0);
				//br.close();
			}
		}
		catch(Exception FileNotFoundException){
			System.out.println("Error no input file");
			System.exit(0);
			//fr.close();
		}
		
		
    }
}