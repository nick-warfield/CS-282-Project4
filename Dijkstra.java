public class Dijkstra{

	ChartRow[] chart;

	private class ChartRow{
		int vertex;
		boolean visited;
		int weight;
		int previous;

		public ChartRow(int ver, int wt, int prev)
		{
			vertex = ver;
			weight = wt;
			previous = prev;
		}
	}
}
