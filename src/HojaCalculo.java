/** 
 * 
 * 
 */
public class HojaCalculo extends Algoritmo
 {

	private Lista<Vertice> Valores;
	private DiGraph digrafo;
	private DiGraphHash solucion; 
	public HojaCalculo(Lista<Vertice> valores)
	{
		this.Valores = valores;
	}
	
	
	public DiGraph ejecutar(DiGraph d)
	{
		this.digrafo = d;
		EvaluarFormulas();  
	
		return this.digrafo;
	}
	
	// Metodo para evaluar las formulas del grafo
	// Para cada nodo valor aplica la funcion SumarCostos que a cada sucesor
	// suma el costo de su valor predecesor
	public void EvaluarFormulas()
	{
			
		while (Valores.size() != 0)
		{
			Vertice valorpivote = Valores.get(0);
			Valores.remove(valorpivote);
			Lista<Vertice> suc = (Lista <Vertice>)this.digrafo.getSucesors(valorpivote);
			if (suc != null)
			{				
				SumarCostos(valorpivote, suc );
			}
		}
		
	}
	
	// Metodo que recibe un nodo y sus sucesores
	// a cada nodo sucesor le  suma el costo del nodo mas el costo del predecesor
	public void SumarCostos( Vertice nodo, Lista<Vertice> Sucesores)
	{	
	
		for (int i=0; i<Sucesores.size(); i++)
		{
			Vertice ini = Sucesores.get(i);
			long j = ini.getCosto()+nodo.getCosto();
			ini.asigCosto(j);
		    ((DiGraphHash)this.digrafo).delArc(nodo, ini);
			if(( this.digrafo.getInDegree(ini) == 0) )
			{	
				if (Valores.size() == 0){
					Valores.add(ini);}
					else{
				Valores.add(Valores.size(),ini);}
			}
		}
	}
}