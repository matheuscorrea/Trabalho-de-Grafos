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
		//Passa por todos vertices e verifca quais são os vizinhos
		for (int i = 0; i < mAdjacencia.length; i++) {
			if(mAdjacencia[v][i]){
				//É vizinho de saída, existe aresta v -> i
				if(profundidadeEntrada[i] == 0){
					tiposArestas[v][i] = TipoAresta.PROFUNDIDADE;
					buscaEmProfundidade(i);
				}else if(profundidadeSaida[i] != 0){
					tiposArestas[v][i] = TipoAresta.CRUZAMENTO;
				}else if(profundidadeEntrada[v] > profundidadeEntrada[i]){
					//Então tem ciclo
					tiposArestas[v][i] = TipoAresta.RETORNO;
					System.out.println("Tem ciclo");
				}else{
					tiposArestas[v][i] = TipoAresta.AVANCO;
				}
			}
			else{
				//TODO Inicializar como TipoAresta.NULO para o caso de nenhuma aresta
				tiposArestas[v][i] = TipoAresta.NULO;
			}
		}
		profundidadeSaida[v] = t++;
	}
}
