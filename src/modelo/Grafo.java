package modelo;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Grafo {
	
	private boolean mAdjacencia[][];
	
	private boolean temCiclo;
	private boolean eConexo;
	private boolean eBipartido;
	private List<List<Point>> blocos;
	private List<Point> pontes;
	private List<Integer> articulacoes;
	private List<List<Integer>> compConexas;
	
	public Grafo(int n, double p){
		mAdjacencia = new boolean [n][n];
		for (int i = 0; i < mAdjacencia.length; i++) {
			//Grafo não direcionado j começa de i+1
			for (int j = i+1; j < mAdjacencia.length; j++) {
				if(Math.random() <= p && i != j){
					mAdjacencia[i][j] = true;
					//Grafo não direcionado
					mAdjacencia[j][i] = true;
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
		f.close();
	}
	
	public boolean isTemCiclo() {
		return temCiclo;
	}

	public void setTemCiclo(boolean temCiclo) {
		this.temCiclo = temCiclo;
	}

	public boolean iseConexo() {
		return eConexo;
	}

	public void seteConexo(boolean eConexo) {
		this.eConexo = eConexo;
	}

	public boolean iseBipartido() {
		return eBipartido;
	}

	public void seteBipartido(boolean eBipartido) {
		this.eBipartido = eBipartido;
	}

	public List<List<Point>> getBlocos() {
		return blocos;
	}

	public void setBlocos(List<List<Point>> blocos) {
		this.blocos = blocos;
	}

	public List<Point> getPontes() {
		return pontes;
	}

	public void setPontes(List<Point> pontes) {
		this.pontes = pontes;
	}

	public List<Integer> getArticulacoes() {
		return articulacoes;
	}

	public void setArticulacoes(List<Integer> articulacoes) {
		this.articulacoes = articulacoes;
	}

	public List<List<Integer>> getCompConexas() {
		return compConexas;
	}

	public void setCompConexas(List<List<Integer>> compConexas) {
		this.compConexas = compConexas;
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
		f.close();
	}

	public boolean[][] getmAdjacencia() {
		return mAdjacencia;
	}

	@Deprecated
	public void setmAdjacencia(boolean[][] mAdjacencia) {
		this.mAdjacencia = mAdjacencia;
	}
	
}
