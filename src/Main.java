import java.io.IOException;

import Modelo.Grafo;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stubSystem.out.println("G1");
		
		Grafo g1 = new Grafo(4, 0.5);
		g1.salvar("grafo.dat");
		Grafo g2 = new Grafo("grafo.dat");
		
		System.out.println("G1");
		for (int i = 0; i < g1.mAdjacencia.length; i++) {
			for	(int j = 0; j < g1.mAdjacencia.length; j++){
				System.out.print(g1.mAdjacencia[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("G2");
		for (int i = 0; i < g2.mAdjacencia.length; i++) {
			for	(int j = 0; j < g2.mAdjacencia.length; j++){
				System.out.print(g2.mAdjacencia[i][j] + " ");
			}
			System.out.println();
		}
	}

}
