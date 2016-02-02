package modelo;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
	
	private boolean mAdjacencia[][];
	
	private boolean temCiclo;
	private boolean eConexo = true;
	private boolean eArvore;
	private boolean eBipartido;
	private List<List<Point>> blocos = new ArrayList<>();
	private List<Point> pontes = new ArrayList<>();
	private List<Integer> articulacoes = new ArrayList<>();
	private List<List<Integer>> compConexas = new ArrayList<>();
	private boolean eEuleriano;
	
	public Grafo(int n, double p){
		mAdjacencia = new boolean [n][n];
		for (int i = 0; i < mAdjacencia.length; i++) {
			//Grafo nÃ£o direcionado j comeÃ§a de i+1
			for (int j = i+1; j < mAdjacencia.length; j++) {
				if(Math.random() <= p && i != j){
					mAdjacencia[i][j] = true;
					//Grafo nÃ£o direcionado
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
	
	public void imprimirDados(){
		System.out.println("Tem ciclos: " + temCiclo);
		System.out.println("É conexo: " + eConexo);
		System.out.println("É árvore: "+ eArvore);
		System.out.println("É Bipartido " + eBipartido);
		for (List<Point> l : blocos) {
			System.out.println("Bloco: " + l.toString());
		}
		for (Point p : pontes) {
			System.out.println("Ponte" + p.toString());
		}
		for (Integer integer : articulacoes) {
			System.out.println("Articulação: " + integer);
		}
		for (List<Integer> l : compConexas) {
			System.out.println("Componente conexa: " + l.toString());
		}
		System.out.println("É euleriano: " + eEuleriano);
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
	
	public void adicionaBloco(List<Point> l){
		blocos.add(l);
	}
	
	public void adicionaPonte(Point p){
		pontes.add(p);
	}
	
	public void adicionaArticulacao(int i){
		articulacoes.add(i);
	}


	public boolean[][] getmAdjacencia() {
		return mAdjacencia;
	}

	@Deprecated
	public void setmAdjacencia(boolean[][] mAdjacencia) {
		this.mAdjacencia = mAdjacencia;
	}

	public boolean geteArvore() {
		return eArvore;
	}

	public void seteArvore(boolean eArvore) {
		this.eArvore = eArvore;
	}

	public boolean eEuleriano() {
		return eEuleriano;
	}

	public void seteEuleriano(boolean eEuleriano) {
		this.eEuleriano = eEuleriano;
	}
	
}
