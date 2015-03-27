import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{

	public static int[][] readpgm(String fn)
	{		
		try {
			InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
			BufferedReader d = new BufferedReader(new InputStreamReader(f));
			String magic = d.readLine();
			String line = d.readLine();
			while (line.startsWith("#")) {
				line = d.readLine();
			}
			Scanner s = new Scanner(line);
			int width = s.nextInt();
			int height = s.nextInt();		   
			line = d.readLine();
			s = new Scanner(line);
			int maxVal = s.nextInt();
			int[][] im = new int[height][width];
			s = new Scanner(d);
			int count = 0;
			while (count < height*width) {
				im[count / width][count % width] = s.nextInt();
				count++;

			}
			return im;
		}

		catch(Throwable t) {
			t.printStackTrace(System.err) ;
			return null;
		}
	}

	public static void writepgm(int[][]image, String Filename){
		// ecrire P2
		// largeur hauteur
		// 255
		int hauteur = image.length;
		int largeur = image[0].length;
		try
		{			 
			PrintWriter writer = new PrintWriter(Filename, "UTF-8");
			writer.println("P2");
			writer.println(largeur+" "+hauteur);
			writer.println("255");
			for(int i = 0; i<image.length; i++){
				for(int j = 0; j<image[i].length; j++){
					writer.print(image[i][j]+" ");
				}
				writer.println();
			}
			writer.close();
		}
		catch (IOException e)
		{
		}	

	}

	public static int[][]interest(int[][]image){
		int[][]interest = new int[image.length][image[0].length];
		int valPixel, voisinGauche = 0, voisinDroite = 0;
		boolean existeVoisinGauche, existeVoisinDroite;
		for(int i = 0; i<image.length; i++){
			for(int j = 0; j<image[i].length; j++){
				existeVoisinDroite = true;
				existeVoisinGauche = true;
				valPixel = image[i][j];
				try{
					voisinGauche = image[i][j-1];
				}catch(IndexOutOfBoundsException e){ // Pas de voisin gauche
					interest[i][j] = Math.abs(valPixel - image[i][j+1]); // Différence entre le pixel et le pixel suivant en valeur absolue
					existeVoisinGauche = false;
				}
				try{
					voisinDroite = image[i][j+1];
				}
				catch(IndexOutOfBoundsException e){ // Pas de voisin droite
					interest[i][j] = Math.abs(valPixel - image[i][j-1]); // Différence entre le pixel et le pixel suivant en valeur absolue
					existeVoisinDroite = false;
				}
				if(existeVoisinDroite && existeVoisinGauche){ // Si on a trouvé les voisins gauche et droite
					interest[i][j] = Math.abs(valPixel - ((voisinDroite+voisinGauche)/2));
				}
			}
		}
		return interest;
	}

	public static int[][] indices(int[][]tab){
		int[][]indices = new int[tab.length][tab[0].length];
		for(int i = 0; i<tab.length; i++){
			for (int j = 0; j<tab[0].length; j++){
				indices[i][j] = tab.length*j+i+1;
			}
		}
		return indices;
	}


	public static Graph toGraph(int[][]itr){
		int[][]indices = indices(itr);
		int[][]interet = interest(itr);
		int size = itr.length * itr[0].length;
		Graph g = new Graph(size+2); // Taille du tableau + source + destination
		int indiceSommet, indiceSommetDroite;
		int indiceGauche1, indiceGauche2, indiceGauche3;
		for(int i = 0; i<itr.length; i++){
			g.addEdge(new Edge(0, i+1, 9999, 0)); // arrêtes reliant la source aux pixels gauche
			for(int j = 0; j<itr[0].length;j++){
				indiceSommet = indices[i][j];
				try{
					indiceSommetDroite = indices[i][j+1];
					g.addEdge(new Edge(indiceSommet, indiceSommetDroite,interet[i][j], 0)); // arrêtes entre le sommet et le sommet droit
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try{
					indiceGauche1 = indices[i-1][j-1];
					g.addEdge(new Edge(indiceSommet, indiceGauche1,9999, 0));
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try{
					indiceGauche2 = indices[i][j-1];
					g.addEdge(new Edge(indiceSommet, indiceGauche2,9999, 0));
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try{
					indiceGauche3 = indices[i+1][j-1];
					g.addEdge(new Edge(indiceSommet, indiceGauche3,9999, 0));
				}
				catch(ArrayIndexOutOfBoundsException e){}
			}
			g.addEdge(new Edge(itr.length*(itr[0].length-1)+i+1,size+1,interet[i][itr[0].length-1], 0)); // arrêtes vers la destination
		}
		return g;
	}

	public static void main(String []args){		
		/* 					Test readpgm					*/
		int[][] tab = readpgm("ex1.pgm");
		for(int i = 0; i<tab.length; i++){
			for(int j = 0; j<tab[i].length; j++){
				System.out.print(tab[i][j]+" ");
			}
			System.out.println();
		}
		
		/*				Test writepgm						*/
		writepgm(tab, "C:/Users/Florine/Desktop/test.txt");

		/*					Test toGraph					*/

		/*int[][]tab = new int[][]{{3,11,24,39},
								{8,21,29,39},
								{74,80,100,200}};*/
		Graph g = toGraph(tab);
		g.writeFile("C:/Users/Florine/Desktop/Test2.txt");
	}
}
