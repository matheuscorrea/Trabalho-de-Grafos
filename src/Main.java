import java.io.IOException;

import modelo.Busca;
import modelo.Grafo;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stubSystem.out.println("G1");
		
		Grafo g1 = new Grafo(3, 0.9);
		g1.salvar("grafo.dat");
		Grafo g2 = new Grafo("grafo.dat");
		
		System.out.println("G1");
		for (int i = 0; i < g1.getmAdjacencia().length; i++) {
			for	(int j = 0; j < g1.getmAdjacencia().length; j++){
				System.out.print(g1.getmAdjacencia()[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("G2");
		for (int i = 0; i < g2.getmAdjacencia().length; i++) {
			for	(int j = 0; j < g2.getmAdjacencia().length; j++){
				System.out.print(g2.getmAdjacencia()[i][j] + " ");
			}
			System.out.println();
		}
		
		Busca b = new Busca(g2);
		b.imprimeTiposArestas();
		
	}

}
