import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

class Test
{
	static boolean visite[];
	public static void dfs(Graph g, int u)
	{
		visite[u] = true;
		System.out.println("Je visite " + u);
		for (Edge e: g.adj(u))
			/* Si on prend l'arête dans le bon sens */
			if (e.from == u)
				if (!visite[e.to])
					dfs(g,e.to);
	}


	public static void bfs(Graph g, int u){
		HashMap<Integer, Integer>liens= new HashMap<Integer,Integer>();
		
		LinkedList<Integer> fileSommets = new LinkedList<Integer>();
		fileSommets.addLast(u);
		visite[u] = true;
		while(!fileSommets.isEmpty()){
			
			u = (int) fileSommets.removeFirst();
			System.out.println("je visite "+u);
			// Pour chaque arrete adjacente à u
			for(Edge e : g.adj(u)){
				if(e.from == u && !visite[e.to]){
					fileSommets.addLast(e.to);
					liens.put(e.to, u);
					visite[e.to] = true;
				}
			}
		}
		int i = liens.get(u);
		while(liens.containsKey(i)){
			System.out.println(" i :"+i);
			i = liens.get(i);
		}
	}
	
	// retourne un chemin de s à t
	public LinkedList<Edge>chemin(Graph g, int s, int t){	
		
		// Si on n'est pas arrivés à la destination
		if(s != t){
			// Pour chaque fils de s
			for(Edge e : g.adj(s)){
				if(e.from == s){
					
				}
				
			}
		}
		return null;
		
	}


	public static void testGraph()
	{
		int n = 5;
		int i,j;
		Graph g = new Graph(n*n+2);

		for (i = 0; i < n-1; i++)
			for (j = 0; j < n ; j++)
				g.addEdge(new Edge(n*i+j, n*(i+1)+j, 1664 - (i+j),10*j));

		for (j = 0; j < n ; j++)		  
			g.addEdge(new Edge(n*(n-1)+j, n*n, 666,10*j));

		for (j = 0; j < n ; j++)					
			g.addEdge(new Edge(n*n+1, j, 10*j,10*j));

		g.addEdge(new Edge(13,17,1337,0));
		g.writeFile("test.dot");
		// dfs à partir du sommet 3
		visite = new boolean[n*n+2];
		//dfs(g, 3);
		bfs(g,26);
	}

	public static void main(String[] args)
	{
		testGraph();
	}
}
