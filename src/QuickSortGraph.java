 public class QuickSortGraph extends Algoritmo
 {
	private DiGraph digrafo;
	private Object [] nodos;
	
	
	public DiGraph ejecutar(DiGraph d)
	{
		this.nodos =((DiGraphHash)d).getAllNodes();
		ordenarQuicksort(this.nodos, 0, nodos.length-1);
		return d;
	}
	
	public Object[] solucion()
	{
		return this.nodos;
	}

	// Metodo para ordenar lexicograficamente un conjunto de celdas dispuestas en un arreglo
	public void ordenarQuicksort(Object[] nodos, int primero, int ultimo)
	{
    	int i=primero, j=ultimo;
    	Object pivote= nodos[(primero + ultimo) / 2];
    	Object auxiliar;
 
    	do{
    		while(funcion(((Vertice)nodos[i]), ((Vertice)pivote))) 
			{i++;}    		
    		while(funcion(((Vertice)pivote), ((Vertice)nodos[j])))
			{j--;}
 
    		if (i<=j){
    			auxiliar=nodos[j];
    			nodos[j]=nodos[i];
    			nodos[i]=auxiliar;
    			i++;
    			j--;
    		}
 
    	} while (i<=j);
 
    	if(primero<j) ordenarQuicksort(nodos,primero, j);
    	if(ultimo>i) ordenarQuicksort(nodos,i, ultimo);
    }
	
	// Metodo que devuelve un booleano si el vertice V es menor que el vertice
	// W de acuerdo al relacion lexigrafica
	public boolean funcion (Vertice V, Vertice W)
	{
		String x = V.getId();
		String y = W.getId();
		String [] comparex = particion(x);
		String [] comparey = particion(y);
		boolean v = false;
		int tam = 0;

		if (comparex[0].hashCode() < comparey[0].hashCode()) {
			v = true;
		}
		else if(comparex[0].hashCode() == comparey[0].hashCode()) {
			
			if (comparex[1].hashCode() < comparey[1].hashCode()) {
				v =true;
			}
		}
		
		return v;
	}
	
	// Metodo que recibe un string X y devuelve un arreglo de string "compare"
	// de dos posiciones la primera con sus letras y la segunda con sus numeros
	public String [] particion (String x) {
	
		String [] compare = new String [2];
		
		for(int i=0; i<x.length(); i++) {
		
			Character a = x.charAt(i);
			
			if (!(Character.isLetter(a))) {
				compare[0] = x.substring(0,i);
				compare[1] = x.substring(i,x.length());
				i = x.length();
			}
		}
		
		return compare;
	
	}
	

}