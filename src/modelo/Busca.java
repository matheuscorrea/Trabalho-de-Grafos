package modelo;

public class Busca {
	
	private enum TipoAresta{PROFUNDIDADE, RETORNO, CRUZAMENTO, AVANCO, NULO}
	
	private Grafo g;
	private boolean mAdjacencia[][];
	private int n;
	private int profundidadeEntrada[];
	private int profundidadeSaida[];
	private TipoAresta tiposArestas[][];
	private int v0;
	
	private int t;
	
	public Busca(Grafo g){
		this.g = g;
		this.mAdjacencia = g.getmAdjacencia();
		this.n = g.getmAdjacencia().length;
		profundidadeEntrada = new int[n];
		profundidadeSaida = new int[n];
		tiposArestas = new TipoAresta[n][n];
		inicializaTipoArestas();
		t = 0;
		v0 = (int)(Math.random()*n);
		this.buscaEmProfundidade(v0);
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
	
	private void buscaEmProfundidade(int v){
		profundidadeEntrada[v] = t++;
		//Passa por todos vertices e verifca quais s√£o os vizinhos
		for (int i = 0; i < mAdjacencia.length; i++) {
			if(mAdjacencia[v][i]){
				//√â vizinho de sa√≠da, existe aresta v -> i
				if(profundidadeEntrada[i] == 0){
					tiposArestas[v][i] = TipoAresta.PROFUNDIDADE;
					buscaEmProfundidade(i);
				} else{
					tiposArestas[v][i] = TipoAresta.RETORNO;
					//Tem ciclo e n„o È ·rvore
					g.setTemCiclo(true);
					
				}
				
				
				/*else if(profundidadeSaida[i] != 0){
					tiposArestas[v][i] = TipoAresta.CRUZAMENTO;
				}else if(profundidadeEntrada[v] > profundidadeEntrada[i]){
					//Ent√£o tem ciclo
					tiposArestas[v][i] = TipoAresta.RETORNO;
					System.out.println("Tem ciclo");
				}else{
					tiposArestas[v][i] = TipoAresta.AVANCO;
				}
			}
			else{
				//TODO Inicializar como TipoAresta.NULO para o caso de nenhuma aresta
				tiposArestas[v][i] = TipoAresta.NULO;
			}*/
			}
			profundidadeSaida[v] = t++;
		}
	}
	
	private void inicializaTipoArestas(){
		for (int i = 0; i < tiposArestas.length; i++) {
			for (int j = 0; j < tiposArestas[0].length; j++) {
				tiposArestas[i][j] = TipoAresta.NULO;
				tiposArestas[j][i] = TipoAresta.NULO;
			}
		}
	}
}
