import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.*;

public class Graph
{
	private ArrayList<Edge>[] adj; //Les arrêtes : un tableau d'ArrayList d'arrêtes
	private final int V; // Nombre de sommets
	int E;
	@SuppressWarnings("unchecked")
	public Graph(int N)
	{
		this.V = N;
		this.E = 0;
		adj = (ArrayList<Edge>[]) new ArrayList[N];
		for (int v= 0; v < N; v++)
			adj[v] = new ArrayList<Edge>(8); // On crée une ArrayList de 8 arrêtes vides par sommet

	}

	public int vertices()
	{
		return V;
	}

	public void addEdge(Edge e)
	{
		int v = e.from;
		int w = e.to;
		adj[v].add(e);
		adj[w].add(e);
	}

	public final Iterable<Edge> adj(int v)
	{
		return adj[v];
	}      

	public final Iterable<Edge> edges()
	{
		ArrayList<Edge> list = new ArrayList<Edge>();
		for (int v = 0; v < V; v++)
			for (Edge e : adj(v)) {
				if (e.to != v)
					list.add(e);
			}
		return list;
	}


	public void writeFile(String s)
	{
		try
		{			 
			PrintWriter writer = new PrintWriter(s, "UTF-8");
			writer.println("digraph G{");
			for (Edge e: edges())
				writer.println(e.from + "->" + e.to + "[label=\"" + e.used  +"/" + e.capacity + "\"];");
			writer.println("}");
			writer.close();
		}
		catch (IOException e)
		{
		}						
	}

	public ArrayList chemin(int u){
		ArrayList chemin = new ArrayList<Integer>();
		boolean[] visite = null;
		HashMap<Integer, Integer>liens= new HashMap<Integer,Integer>();
		LinkedList<Integer> fileSommets = new LinkedList<Integer>();
		// On enfile le premier sommet
		fileSommets.addLast(u);
		// On le marque
		visite[u] = true;
		// Tant qu'on a des sommets dans la file
		while(!fileSommets.isEmpty()){
			// On défile
			u = (int) fileSommets.removeFirst();
			System.out.println("je visite "+u);
			// Pour chaque arrete adjacente à u
			for(Edge e : adj(u)){
				// Si elle est dans le bon sens
				if(e.from == u && !visite[e.to]){
					// On enfile le sommet
					fileSommets.addLast(e.to);
					liens.put(e.to, u);
					// On le marque
					visite[e.to] = true;
				}
			}
		}
		int i = liens.get(u);
		chemin.add(i);
		while(liens.containsKey(i)){
			System.out.println(" i :"+i);
			i = liens.get(i);
			chemin.add(i);
		}
		return chemin;
	}
}
