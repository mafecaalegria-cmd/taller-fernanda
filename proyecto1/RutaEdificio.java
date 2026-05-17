package proyecto1;
 
/**
 * Gestiona las rutas entre edificios usando una matriz de adyacencia.
 * Implementa el algoritmo de Dijkstra para encontrar la ruta más corta.
 */
public class RutaEdificio {
 
    private static final int NUM_EDIFICIOS = 5;
    private static final int INF = Integer.MAX_VALUE;
 
    // Matriz de adyacencia int[N][N] - implementada manualmente con arreglo nativo
    private int[][] distancias;
 
    // Nombres de los edificios
    private String[] edificios;
 
    public RutaEdificio() {
        distancias = new int[NUM_EDIFICIOS][NUM_EDIFICIOS];
        edificios = new String[NUM_EDIFICIOS];
 
        // Inicializar matriz con INF (sin conexión)
        for (int i = 0; i < NUM_EDIFICIOS; i++) {
            for (int j = 0; j < NUM_EDIFICIOS; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else {
                    distancias[i][j] = INF;
                }
            }
        }
 
        // Nombres predeterminados de edificios
        edificios[0] = "Ingenieria";
        edificios[1] = "Biblioteca";
        edificios[2] = "Cafeteria";
        edificios[3] = "Rectoria";
        edificios[4] = "Laboratorios";
    }
 
    /**
     * Agrega una conexión bidireccional entre dos edificios.
     */
    public void agregarConexion(int origen, int destino, int distancia) {
        if (origen < 0 || origen >= NUM_EDIFICIOS || destino < 0 || destino >= NUM_EDIFICIOS) {
            System.out.println("Error: Indice de edificio invalido.");
            return;
        }
        distancias[origen][destino] = distancia;
        distancias[destino][origen] = distancia; // grafo no dirigido
        System.out.println("Conexion agregada: " + edificios[origen] + " <-> " + edificios[destino] + " (" + distancia + "m)");
    }
 
    /**
     * Algoritmo de Dijkstra para encontrar la ruta más corta.
     */
    public void calcularRutaMasCorta(int origen, int destino) {
        if (origen < 0 || origen >= NUM_EDIFICIOS || destino < 0 || destino >= NUM_EDIFICIOS) {
            System.out.println("Error: Indice de edificio invalido.");
            return;
        }
 
        int[] dist = new int[NUM_EDIFICIOS];
        boolean[] visitado = new boolean[NUM_EDIFICIOS];
        int[] anterior = new int[NUM_EDIFICIOS];
 
        // Inicializar
        for (int i = 0; i < NUM_EDIFICIOS; i++) {
            dist[i] = INF;
            visitado[i] = false;
            anterior[i] = -1;
        }
        dist[origen] = 0;
 
        // Dijkstra
        for (int count = 0; count < NUM_EDIFICIOS - 1; count++) {
            int u = minimoNoVisitado(dist, visitado);
            if (u == -1) break;
            visitado[u] = true;
 
            for (int v = 0; v < NUM_EDIFICIOS; v++) {
                if (!visitado[v]
                        && distancias[u][v] != INF
                        && dist[u] != INF
                        && dist[u] + distancias[u][v] < dist[v]) {
                    dist[v] = dist[u] + distancias[u][v];
                    anterior[v] = u;
                }
            }
        }
 
        // Mostrar resultado
        if (dist[destino] == INF) {
            System.out.println("No existe ruta entre " + edificios[origen] + " y " + edificios[destino]);
            return;
        }
 
        System.out.println("\n--- RESULTADO ---");
        System.out.print("Ruta mas corta: ");
        imprimirCamino(anterior, destino, origen);
        System.out.println("\nDistancia TOTAL: " + dist[destino] + " metros");
    }
 
    /**
     * Encuentra el nodo no visitado con menor distancia.
     */
    private int minimoNoVisitado(int[] dist, boolean[] visitado) {
        int min = INF;
        int indice = -1;
        for (int i = 0; i < NUM_EDIFICIOS; i++) {
            if (!visitado[i] && dist[i] <= min) {
                min = dist[i];
                indice = i;
            }
        }
        return indice;
    }
 
    /**
     * Imprime el camino reconstruyendo desde el arreglo de anteriores.
     */
    private void imprimirCamino(int[] anterior, int destino, int origen) {
        // Reconstruir camino usando arreglo estático
        int[] camino = new int[NUM_EDIFICIOS];
        int longitud = 0;
        int actual = destino;
 
        while (actual != -1) {
            camino[longitud++] = actual;
            actual = anterior[actual];
        }
 
        // Imprimir en orden correcto (de origen a destino)
        for (int i = longitud - 1; i >= 0; i--) {
            System.out.print(edificios[camino[i]]);
            if (i > 0) System.out.print(" -> ");
        }
    }
 
    /**
     * Muestra todos los edificios registrados.
     */
    public void mostrarEdificios() {
        System.out.println("\nEdificios registrados:");
        for (int i = 0; i < NUM_EDIFICIOS; i++) {
            System.out.println(i + ": " + edificios[i]);
        }
    }
 
    /**
     * Muestra la matriz de adyacencia completa.
     */
    public void mostrarMatriz() {
        System.out.println("\nMatriz de distancias:");
        System.out.print("       ");
        for (int i = 0; i < NUM_EDIFICIOS; i++) {
            System.out.printf("%-12s", edificios[i]);
        }
        System.out.println();
        for (int i = 0; i < NUM_EDIFICIOS; i++) {
            System.out.printf("%-7s", edificios[i]);
            for (int j = 0; j < NUM_EDIFICIOS; j++) {
                if (distancias[i][j] == INF) {
                    System.out.printf("%-12s", "INF");
                } else {
                    System.out.printf("%-12d", distancias[i][j]);
                }
            }
            System.out.println();
        }
    }
 
    // Alias para el Main existente
    public void mostrarRuta() {
        mostrarEdificios();
        System.out.println("Use la opcion 'Calcular ruta mas corta' para ver Dijkstra.");
    }
}
 
