package proyecto1;

public class ArreglosMatrices {
    dias = ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"]
 
def crear_horario():
    """Crea una matriz 7x24 de False (disponible)."""
    return [[False] * 24 for _ in range(7)]
 
def reservar(horario, dia, hora, duracion):
    """Reserva el aula en el día/hora indicados."""
    for h in range(hora, hora + duracion):
        if horario[dia][h]:
            print(f"Error: {dias[dia]} {h}:00 ya está reservado")
            return False
    for h in range(hora, hora + duracion):
        horario[dia][h] = True
    print(f"Reserva exitosa: {dias[dia]} {hora}:00 - {hora + duracion}:00")
    return True
 
def liberar(horario, dia, hora, duracion):
    """Libera el horario indicado."""
    for h in range(hora, hora + duracion):
        horario[dia][h] = False
    print(f"Liberado: {dias[dia]} {hora}:00 - {hora + duracion}:00")
 
def consultar_disponibilidad(horario, dia, hora):
    """Consulta si una hora está disponible."""
    estado = "OCUPADO" if horario[dia][hora] else "LIBRE"
    print(f"{dias[dia]} {hora}:00 -> {estado}")
    return not horario[dia][hora]
 
def mostrar_horario(horario):
    """Imprime la matriz de horario."""
    print(f"\n{'Hora':<6}", end="")
    for d in dias:
        print(f"{d:<11}", end="")
    print()
    for h in range(6, 22):
        print(f"{h}:00  ", end="")
        for d in range(7):
            print(f"{'[X]':<11}" if horario[d][h] else f"{'[ ]':<11}", end="")
        print()
 
 
# ── 2. Notas por semestre Double[10][20] ─────────────────────
 
def crear_notas():
    """Crea un arreglo 10x20 de None (sin nota)."""
    return [[None] * 20 for _ in range(10)]
 
def registrar_nota(notas, semestre, materia, nota):
    """Registra una nota (semestre 0-9, materia 0-19)."""
    if 0 <= semestre < 10 and 0 <= materia < 20:
        notas[semestre][materia] = nota
        print(f"Nota {nota} registrada en semestre {semestre+1}, materia {materia+1}")
    else:
        print("Error: índice fuera de rango")
 
def promedio_semestre(notas, semestre):
    """Calcula el promedio de un semestre específico."""
    valores = [n for n in notas[semestre] if n is not None]
    return sum(valores) / len(valores) if valores else 0.0
 
def promedio_acumulado(notas):
    """Calcula el promedio acumulado de todos los semestres."""
    todos = [n for sem in notas for n in sem if n is not None]
    return sum(todos) / len(todos) if todos else 0.0
 
def materias_reprobadas(notas):
    """Retorna lista de (semestre, materia, nota) con nota < 3.0."""
    reprobadas = []
    for i in range(10):
        for j in range(20):
            if notas[i][j] is not None and notas[i][j] < 3.0:
                reprobadas.append((i + 1, j + 1, notas[i][j]))
    return reprobadas
 
def reporte_academico(notas, nombre):
    """Imprime el reporte académico completo."""
    print(f"\n--- REPORTE ACADEMICO ---")
    print(f"Estudiante: {nombre}")
    aprobadas = reprobadas_count = 0
    for i in range(10):
        sem = [n for n in notas[i] if n is not None]
        if not sem:
            continue
        print(f"\nSemestre {i+1}:")
        for j, nota in enumerate(notas[i]):
            if nota is not None:
                flag = " *** REPROBADA" if nota < 3.0 else ""
                print(f"  Materia {j+1:02d}: {nota:.1f}{flag}")
                if nota < 3.0:
                    reprobadas_count += 1
                else:
                    aprobadas += 1
        print(f"  Promedio semestre: {promedio_semestre(notas, i):.2f}")
    print("\n=== RESUMEN ===")
    print(f"Promedio acumulado: {promedio_acumulado(notas):.2f}")
    print(f"Materias aprobadas: {aprobadas}")
    print(f"Materias reprobadas: {reprobadas_count}")
 
 
# ── 3. Matriz de adyacencia entre edificios int[N][N] ────────
 
EDIFICIOS = ["Ingenieria", "Biblioteca", "Cafeteria", "Rectoria", "Laboratorios"]
N = len(EDIFICIOS)
INF = float('inf')
 
def crear_grafo():
    """Crea la matriz de adyacencia N x N."""
    return [[0 if i == j else INF for j in range(N)] for i in range(N)]
 
def agregar_conexion(grafo, origen, destino, distancia):
    """Agrega conexión bidireccional."""
    grafo[origen][destino] = distancia
    grafo[destino][origen] = distancia
    print(f"Conexion: {EDIFICIOS[origen]} <-> {EDIFICIOS[destino]} ({distancia}m)")
 
def dijkstra(grafo, origen, destino):
    """Algoritmo de Dijkstra con arreglos nativos de Python."""
    dist = [INF] * N
    visitado = [False] * N
    anterior = [-1] * N
    dist[origen] = 0
 
    for _ in range(N - 1):
        # Nodo no visitado con menor distancia
        u = min((i for i in range(N) if not visitado[i]), key=lambda i: dist[i], default=-1)
        if u == -1 or dist[u] == INF:
            break
        visitado[u] = True
        for v in range(N):
            if not visitado[v] and grafo[u][v] != INF:
                nueva = dist[u] + grafo[u][v]
                if nueva < dist[v]:
                    dist[v] = nueva
                    anterior[v] = u
 
    if dist[destino] == INF:
        print(f"No hay ruta entre {EDIFICIOS[origen]} y {EDIFICIOS[destino]}")
        return
 
    # Reconstruir camino
    camino = []
    actual = destino
    while actual != -1:
        camino.append(actual)
        actual = anterior[actual]
    camino.reverse()
 
    print("\n--- RESULTADO ---")
    ruta_str = " -> ".join(EDIFICIOS[e] for e in camino)
    print(f"Ruta mas corta: {ruta_str}")
    print(f"Distancia TOTAL: {dist[destino]} metros")
 
 
# ── 4. Arreglo fijo de facultades Facultad[5] ────────────────
 
facultades = [None] * 5
 
def inicializar_facultades():
    facultades[0] = {"nombre": "Ingenieria de Sistemas", "codigo": "IS"}
    facultades[1] = {"nombre": "Ingenieria Civil",       "codigo": "IC"}
    facultades[2] = {"nombre": "Administracion",         "codigo": "AD"}
    facultades[3] = {"nombre": "Medicina",               "codigo": "ME"}
    facultades[4] = {"nombre": "Derecho",                "codigo": "DE"}
 
def mostrar_facultades():
    print("Facultades:")
    for i, f in enumerate(facultades):
        if f:
            print(f"  {i}. [{f['codigo']}] {f['nombre']}")
 
 
# ── Demo ──────────────────────────────────────────────────────
 
if __name__ == "__main__":
    print("=" * 50)
    print("  DEMO - Arreglos y Matrices en Python")
    print("=" * 50)
 
    # Horario
    print("\n[1] Horario de Aula")
    h = crear_horario()
    reservar(h, 1, 8, 2)   # Lunes 8-10
    consultar_disponibilidad(h, 1, 8)
    consultar_disponibilidad(h, 1, 10)
 
    # Notas
    print("\n[2] Notas del Estudiante")
    notas = crear_notas()
    registrar_nota(notas, 0, 0, 4.5)
    registrar_nota(notas, 0, 1, 2.8)
    registrar_nota(notas, 0, 2, 5.0)
    registrar_nota(notas, 1, 0, 3.5)
    reporte_academico(notas, "Ana Maria Gomez")
 
    # Dijkstra
    print("\n[3] Rutas entre Edificios")
    grafo = crear_grafo()
    agregar_conexion(grafo, 0, 2, 150)   # Ingenieria - Cafeteria
    agregar_conexion(grafo, 2, 3, 180)   # Cafeteria - Rectoria
    agregar_conexion(grafo, 0, 1, 400)   # Ingenieria - Biblioteca
    dijkstra(grafo, 0, 3)                # Ingenieria -> Rectoria
 
    # Facultades
    print("\n[4] Facultades")
    inicializar_facultades()
    mostrar_facultades()
}
