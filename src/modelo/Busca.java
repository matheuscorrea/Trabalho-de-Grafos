package modelo;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	
	public Busca(Grafo g, String caminho) throws FileNotFoundException, UnsupportedEncodingException{
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
				buscaEmProfundidade(i);
				g.adicionaComponenteConexa(new ArrayList<>(componenteConexo));
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
			
		}
		PrintWriter writer = new PrintWriter(caminho);
		g.imprimirDados(writer);
		imprimeTiposArestas(writer);
		
		
		if(g.eEuleriano()){
			boolean aux[][] = mAdjacencia.clone();
			//Começa do 0
			System.out.println("Caminho euleriano: ");
			int verticeAtual=0;
			int verticeAnterior = 0;
			int verticeCandidato = 0;
			boolean acabou = false;
			while(!acabou){
				int i=0;
				g.getPontes();
				verticeCandidato = -1;
				//Enquanto i<n, ou (nao tem adjacencia ou é ponte)
				while(i<mAdjacencia.length&&
						(mAdjacencia[verticeAtual][i]==false || 
							(g.getPontes().contains(new Point(verticeAtual,i)) || g.getPontes().contains(new Point(i,verticeAtual)) )  
							)
					 ){
					if(g.getPontes().contains(new Point(verticeAtual,i)) || g.getPontes().contains(new Point(i,verticeAtual))){
						verticeCandidato = i;
					}					
					i++;
				}
				//Verifica se acabou
				if(verticeCandidato==-1 && i == mAdjacencia.length){
					acabou = true;
				}else{
					//Verifica se vai para o vertice ponte (candidato) ou outro (i)
					verticeAnterior = verticeAtual;
					if(i == mAdjacencia.length ){
						verticeAtual = verticeCandidato;
					}else{
						verticeAtual = i;
					}
					//Remove e escreve aresta
					mAdjacencia[verticeAnterior][verticeAtual] = false;
					mAdjacencia[verticeAtual][verticeAnterior] = false;
					System.out.println(" ("+verticeAnterior+","+verticeAtual+") ");
					writer.println(" ("+verticeAnterior+","+verticeAtual+") ");
					
					//Reinicia o grafo
					g = new Grafo(this.mAdjacencia);
					this.g = g;
					this.n = g.getmAdjacencia().length;
					profundidadeEntrada = new int[n];
					profundidadeSaida = new int[n];
					back = new int[n];
					tiposArestas = new TipoAresta[n][n];
					inicializaTipoArestas();
					t = 0;
					buscaEmProfundidade(verticeAtual);
				}
			}
		}
		
		
		writer.close();
		
		
		
	}
	
	public static boolean eConexo (Grafo g){
		return false;
	}
	
	public void imprimeTiposArestas(PrintWriter writer) throws FileNotFoundException{				
		for (int i = 0; i < tiposArestas.length; i++) {
			for (int j = 0; j < tiposArestas[0].length; j++) {
				System.out.print(tiposArestas[i][j].toString() + " ");
				writer.print(tiposArestas[i][j].toString() + " ");
			}
			System.out.println();
			writer.println();
		}
	}
	
	private void buscaEmProfundidade(int v){
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
					buscaEmProfundidade(i);
					if(back[i]>=profundidadeEntrada[v]){
						List<Point> bl = new ArrayList();
						Set<Integer> vBloco = new HashSet<>();
						while(!bloco.empty() && !bloco.peek().equals(new Point(i,v)) && !bloco.peek().equals(new Point(v,i))){
							vBloco.add(bloco.peek().x);
							vBloco.add(bloco.peek().y);
							bl.add(bloco.pop());
						}		
						vBloco.add(bloco.peek().x);
						vBloco.add(bloco.peek().y);
						bl.add(bloco.pop());
						if(bl.size()==1){
							g.adicionaPonte(bl.get(0));
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
