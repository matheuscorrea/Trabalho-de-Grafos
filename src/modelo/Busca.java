package modelo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Busca {
	
	private enum TipoAresta{PROFUNDIDADE, __RETORNO___, CRUZAMENTO, AVANCO, ____NULO____}
	
	private Grafo g;
	private boolean mAdjacencia[][];
	private int n;
	private int profundidadeEntrada[];
	private int profundidadeSaida[];
	private int back[];
	private boolean cor[];
	private TipoAresta tiposArestas[][];
	private int v0;
	private int componentes = 0;
	private Stack<Point> bloco = new Stack<>();
	private Set<Integer> componenteConexo = new HashSet<Integer>();
	private int qtdBlocos[];
	private int grau[];
	
	
	private int t;
	
	public Busca(Grafo g){
		this.g = g;
		this.mAdjacencia = g.getmAdjacencia();
		this.n = g.getmAdjacencia().length;
		profundidadeEntrada = new int[n];
		profundidadeSaida = new int[n];
		back = new int[n];
		cor = new boolean[n];
		tiposArestas = new TipoAresta[n][n];
		inicializaTipoArestas();
		t = 0;
		
		//v0 = (int)(Math.random()*n);
		//this.buscaEmProfundidade(v0);
		g.seteBipartido(true);
		this.qtdBlocos = new int[n];
		for (int i = 0; i < n; i++) {
			if(profundidadeEntrada[i]==0){
				componentes++;			
				buscaEmProfundidade(i,-1);
				//Imprime componente
				componenteConexo.clear();
			}
		}
		if(componentes!=1){
			g.seteConexo(false);
		}
		if(g.iseConexo() && !g.isTemCiclo()){
			g.seteArvore(true);
		}
		
		for (int i = 0; i < qtdBlocos.length; i++) {
			if(qtdBlocos[i]>1){
				g.adicionaArticulacao(i);
			}
		}
		if(g.iseConexo()){
			grau = new int[n];
			for (int i = 0; i < mAdjacencia.length; i++) {
				for (int j = i + 1; j < mAdjacencia[0].length; j++) {
					if(mAdjacencia[i][j]){
						grau[i]++;
						grau[j]++;
					}
				}
			}
			g.seteEuleriano(true);
			for (int i = 0; i < grau.length; i++) {
				if(grau[i]%2==1){
					g.seteEuleriano(false);
				}
			}
			boolean aux[][] = mAdjacencia;
			
			
		}
		g.imprimirDados();
		System.out.println(t);
	}
	
	public static boolean eConexo (Grafo g){
		return false;
	}
	
	public void imprimeTiposArestas(){
		System.out.println(v0);
		for (int i = 0; i < tiposArestas.length; i++) {
			for (int j = 0; j < tiposArestas[0].length; j++) {
				System.out.print(tiposArestas[i][j].toString() + " ");
			}
			System.out.println();
		}
	}
	
	private void buscaEmProfundidade(int v, int pai){
		profundidadeEntrada[v] = ++t;
		componenteConexo.add(v);
		back[v] = profundidadeEntrada[v];
		//Passa por todos vertices e verifca quais sÃ£o os vizinhos
		for (int i = 0; i < mAdjacencia.length; i++) {
			if(mAdjacencia[v][i] && tiposArestas[v][i] == TipoAresta.____NULO____){
				//Ã‰ vizinho de saÃ­da, existe aresta v -> i
				if(profundidadeEntrada[i] == 0){
					tiposArestas[v][i] = TipoAresta.PROFUNDIDADE;
					tiposArestas[i][v] = TipoAresta.PROFUNDIDADE;
					System.out.println(v +"->"+i+" Profundidade");
					cor[i] = !cor[v];
					//Adicionar vértice ao componente conexo
					bloco.push(new Point(v,i));					 
					buscaEmProfundidade(i, v);
					if(back[i]>=profundidadeEntrada[v]){
						if(bloco.size()==1){
							g.adicionaPonte(new Point(v,i));
						}						
						List<Point> bl = new ArrayList();
						Set<Integer> vBloco = new HashSet<>();
						while(!bloco.empty() && !bloco.peek().equals(new Point(i,v)) && !bloco.peek().equals(new Point(v,i))){
							vBloco.add(i);
							vBloco.add(v);
							bl.add(bloco.pop());
						}								
						for (Integer integer : vBloco) {
							qtdBlocos[integer]++;
						}
						g.adicionaBloco(bl);
					}
					back[v] = Math.min(back[v], back[i]);
				} else{
					tiposArestas[v][i] = TipoAresta.__RETORNO___;
					tiposArestas[i][v] = TipoAresta.__RETORNO___;
					System.out.println(v +"->"+i+" Retorno");
					back[v] = Math.min(back[v], profundidadeEntrada[i]);
					bloco.push(new Point(v,i));
					//Tem ciclo e não é árvore
					g.setTemCiclo(true);
					if(cor[i]== cor[v]){
						g.seteBipartido(false);
					}					
				}
			}
			
		}
		profundidadeSaida[v] = ++t;
	}
	
	private void inicializaTipoArestas(){
		for (int i = 0; i < tiposArestas.length; i++) {
			for (int j = 0; j < tiposArestas[0].length; j++) {
				tiposArestas[i][j] = TipoAresta.____NULO____;
				tiposArestas[j][i] = TipoAresta.____NULO____;
			}
		}
	}
}
