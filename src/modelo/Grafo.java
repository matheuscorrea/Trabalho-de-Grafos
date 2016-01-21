package modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Grafo {
	
	private boolean mAdjacencia[][];
	
	public Grafo(int n, double p){
		mAdjacencia = new boolean [n][n];
		for (int i = 0; i < mAdjacencia.length; i++) {
			//Grafo não direcionado j começa de i+1
			for (int j = 0; j < mAdjacencia.length; j++) {
				if(Math.random() <= p && i != j){
					mAdjacencia[i][j] = true;
					//Grafo não direcionado
					//mAdjacencia[j][i] = true;
				}
			}			
		}
	}
	
	public Grafo(String arquivo) throws IOException{
		carregar(arquivo);
	}
	
	public void salvar(String caminho) throws IOException{
		FileOutputStream f = new FileOutputStream(caminho);
		f.write(mAdjacencia.length);
		for (int i = 0; i < mAdjacencia.length; i++){
			for (int j = 0; j < mAdjacencia.length; j++){
				if(mAdjacencia[i][j]){
					f.write(1);
				}else{
					f.write(0);
				}
			}
		}
	}
	
	public void carregar(String nomeArquivo) throws IOException{
		FileInputStream f = new FileInputStream(nomeArquivo);
		int n = f.read();
		mAdjacencia = new boolean[n][n];
		for (int i = 0; i < mAdjacencia.length; i++){
			for (int j = 0; j < mAdjacencia.length; j++){
				int aresta = f.read();
				if(aresta==1){
					mAdjacencia[i][j] = true;
				}else{
					mAdjacencia[i][j] = false;
				}
			}
		}		
	}

	public boolean[][] getmAdjacencia() {
		return mAdjacencia;
	}

	public void setmAdjacencia(boolean[][] mAdjacencia) {
		this.mAdjacencia = mAdjacencia;
	}
	
}
