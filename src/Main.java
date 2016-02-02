import java.io.IOException;

import modelo.Busca;
import modelo.Grafo;

public class Main {

	public static void main(String[] args) throws IOException {

		int n;
		float p;
		n = 6; 
		p= 0.6f;
		if(args.length == 2){
			n = Integer.valueOf(args[0]);
			p = Float.valueOf(args[1]);
			Grafo g1 = new Grafo(n, p);
			Busca b = new Busca(g1,"dados.dat");
		}else if(args.length == 1){
			String path = args[0];
			Grafo g1 = new Grafo(path);
			Busca b = new Busca(g1,"dados.dat");
		}else{
			Grafo g1 = new Grafo(n, p);
			g1.salvar("grafo.dat");
			
			Busca b = new Busca(g1,"dados.dat");
		}
		//b.imprimeTiposArestas("dados.dat");
				
	}
}
