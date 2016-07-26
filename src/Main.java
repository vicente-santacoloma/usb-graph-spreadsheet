import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.File;
import java.util.*;
import java.io.IOException;

public class Main
 {

	private String inFile;
	private String outFile;
	private	DiGraphHash digrafo;
	private Lista<Vertice> Valores   = new Lista<Vertice> ();
	private Lista<Vertice> Funciones = new Lista<Vertice> ();
	
	
	public Main (String inF, String outF)
	{
		inFile = inF;
		outFile = outF;
	}
	
	// Metodo para procesar y ejecutar el algoritmo Hoja de Calculo
	private int procesar ()
	{
		String linea = "";
		BufferedReader in = null;
		PrintStream out = null;
		
		try {
		
			in = new BufferedReader(new FileReader(inFile));
			out = new PrintStream(outFile);
			linea = in.readLine();
			int numhojas = Integer.parseInt(linea);
			int contador = 0;
			
			while (linea != null && contador < numhojas ) 
			{
			
				linea = in.readLine();
				String [] tokens = linea.split(" ");
				int Columnas = Integer.parseInt(tokens[0]);
				int Filas = Integer.parseInt(tokens[1]);
				int numnodes = Columnas*Filas;
				String [] Caracter  = cargarCaracteres(Columnas);
				digrafo = new DiGraphHash(numnodes);
				
				for (int i=1; i<Filas+1; i++) 
				{
					linea = in.readLine();
					cargarNodes(i,linea,Caracter);
				}
				cargarArcos();
				Algoritmo alg = new HojaCalculo(Valores);
				digrafo = (DiGraphHash)alg.ejecutar((DiGraph)digrafo);
				alg = new QuickSortGraph();
				digrafo = (DiGraphHash)alg.ejecutar((DiGraph)digrafo);
				Object [] celdas = ((QuickSortGraph)alg).solucion();
				
				for (int k = 0; k<Filas; k++) 
				{
					int v = k;
					for(int j = 0; j<Columnas; j++) 
					{
						out.print(((Vertice)celdas[v]).getCosto()+" ");
						out.flush();
						v = v + Filas; 
					
					}
					out.println("");
					out.flush();
				}
				contador++;
				Valores.clear();
				Funciones.clear();
			}
		
		} catch (Exception ioe) 
		{
			return -1;
		}
		
		return 0;
	}
	
	// Metodo para cargar los nodos Valor y Funcion en el grafo
	private void cargarNodes (int fila, String linea, String [] Caracter) 
	{
		String [] tokens = linea.split(" ");
		
		for (int i = 0; i<tokens.length; i++) {
		
			if (tokens[i].substring(0,1).equals("=")) {
			
				String id = Caracter[i] + Integer.toString(fila);
				String funcion = tokens[i].replace("=","");
				Vertice nodo = new Vertice (id,0, funcion);
				digrafo.addnode(nodo);
				Funciones.addi(nodo);
		
			}
			else {
			
				String id = Caracter[i] + Integer.toString(fila);
				int costo = Integer.parseInt(tokens[i]);
				Vertice nodo = new Vertice (id,costo);
				digrafo.addnode(nodo);
				Valores.addi(nodo);

			}
			
		}
		
	}
	
	// Metodo para cargar los arcos de acuerdo a la relacion Valor-Funcion y Funcion-Funcion
	private void cargarArcos () {
	
		int tam = Funciones.size();
	
		for (int i = 0; i<tam; i++) {
		
			Vertice nodoFuncion = Funciones.get(i);
			
			String [] tokens = nodoFuncion.getFuncion().split("\\+");
			
			for (int j = 0; j<tokens.length; j++) {
			
				Vertice nodo = new Vertice (tokens[j],0);
				nodo = digrafo.getVertice(nodo);
				Arc arco = new Arc(nodo, nodoFuncion);
				digrafo.addArc(arco);
			
			}
		}
	}
	
	// Metodo que devuelve el orden lexicografico de las celdas de acuerdo a las columnas
	private String [] cargarCaracteres (int Columnas) 
	{
	
		String [] Caracter = new String [Columnas];
		String [] Alfabeto = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		String carac = "";
		int resto = 0;
		int cociente = 0;
		
		for (int i = 0; i<Columnas; i++) {
		
			if (0<=i && i<26) {
			
				Caracter[i] = Alfabeto[i];
			
			}
			else if (26<=i && i<702) {
			
				resto = i % 26;
				cociente = i/26;
				cociente--;
				Caracter [i] = Alfabeto[cociente] + Alfabeto[resto];
				
			}
			else if (702<=i && i<18278) {
			
				resto = i % 26;
				cociente = i/26;
				cociente--;
				Caracter [i] = Alfabeto[resto];
				resto = cociente % 26;
				cociente = cociente/26;
				cociente--;
				Caracter [i] = Alfabeto[cociente] + Alfabeto[resto] + Caracter[i];
				
			}
		
		}
		
		return Caracter;
		
	}
	
	public static void main(String[] args) 
	 {
        if (args.length != 2) 
		{
            System.exit(-1);
        }

        String fileIn = args[0];
        String fileOut = args[1];

        Main m = new Main(fileIn, fileOut);

        System.exit(m.procesar());
    }
}