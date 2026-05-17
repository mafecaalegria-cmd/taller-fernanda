package com.universidad.estructuras;

/**
 * Grafo de edificios universitarios representado con matriz de adyacencia.
 * Implementa el algoritmo de Dijkstra para ruta más corta.
 */
public class GrafoEdificios {

    private static final int INF = Integer.MAX_VALUE / 2;

    private int numEdificios;
    private String[] nombres;

    // Matriz de adyacencia nativa [N][N] con distancias en metros
    private int[][] distancias;

    public GrafoEdificios(String[] nombresEdificios) {
        this.numEdificios = nombresEdificios.length;
        this.nombres = nombresEdificios;
        this.distancias = new int[numEdificios][numEdificios];

        // Inicializar: 0 en diagonal, INF para sin conexión directa
        for (int i = 0; i < numEdificios; i++)
            for (int j = 0; j < numEdificios; j++)
                distancias[i][j] = (i == j) ? 0 : INF;
    }

    /** Agrega conexión bidireccional entre dos edificios. */
    public void agregarConexion(int origen, int destino, int metros) {
        if (origen < 0 || destino < 0 || origen >= numEdificios || destino >= numEdificios) {
            System.out.println("  Índice de edificio inválido.");
            return;
        }
        distancias[origen][destino] = metros;
        distancias[destino][origen] = metros;
        System.out.printf("  Conexión agregada: %s ↔ %s (%dm)%n",
                nombres[origen], nombres[destino], metros);
    }

    /** Algoritmo de Dijkstra. Retorna [distancia_total, ruta_como_string]. */
    public String calcularRutaMasCorta(int origen, int destino) {
        int[] dist    = new int[numEdificios];
        int[] anterior = new int[numEdificios];
        boolean[] visitado = new boolean[numEdificios];

        for (int i = 0; i < numEdificios; i++) {
            dist[i] = INF;
            anterior[i] = -1;
        }
        dist[origen] = 0;

        for (int iter = 0; iter < numEdificios; iter++) {
            // Nodo no visitado con menor distancia
            int u = -1;
            for (int i = 0; i < numEdificios; i++)
                if (!visitado[i] && (u == -1 || dist[i] < dist[u]))
                    u = i;

            if (dist[u] == INF) break;
            visitado[u] = true;

            for (int v = 0; v < numEdificios; v++) {
                if (!visitado[v] && distancias[u][v] < INF) {
                    int nuevaDist = dist[u] + distancias[u][v];
                    if (nuevaDist < dist[v]) {
                        dist[v] = nuevaDist;
                        anterior[v] = u;
                    }
                }
            }
        }

        if (dist[destino] == INF)
            return "No existe ruta entre " + nombres[origen] + " y " + nombres[destino] + ".";

        // Reconstruir ruta
        StringBuilder ruta = new StringBuilder();
        int actual = destino;
        String[] pasos = new String[numEdificios];
        int n = 0;
        while (actual != -1) {
            pasos[n++] = nombres[actual];
            actual = anterior[actual];
        }

        // Invertir
        ruta.append("\n  Ruta más corta:\n  ");
        for (int i = n - 1; i >= 0; i--) {
            ruta.append(pasos[i]);
            if (i > 0) ruta.append(" → ");
        }
        ruta.append("\n  Distancia TOTAL: ").append(dist[destino]).append(" metros");
        return ruta.toString();
    }

    public void mostrarEdificios() {
        System.out.println("  Edificios registrados:");
        for (int i = 0; i < numEdificios; i++)
            System.out.printf("    %d: %s%n", i, nombres[i]);
    }

    public int getNumEdificios() { return numEdificios; }
}
