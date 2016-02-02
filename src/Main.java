import java.io.IOException;

import modelo.Busca;
import modelo.Grafo;

public class Main {

	public static void main(String[] args) throws IOException {

		int n;
		if(args.length == 1){
			n = Integer.valueOf(args[0]);
		}else{
			n = 6;
		}
		Grafo g1 = new Grafo(n, 0.6);
		g1.salvar("grafo.dat");
		
		System.out.println("G1");
		for (int i = 0; i < g1.getmAdjacencia().length; i++) {
			for	(int j = 0; j < g1.getmAdjacencia().length; j++){
				System.out.print(g1.getmAdjacencia()[i][j] + " ");
			}
			System.out.println();
		}

		Busca b = new Busca(g1,"dados.dat");
		//b.imprimeTiposArestas("dados.dat");
				
	}
}
