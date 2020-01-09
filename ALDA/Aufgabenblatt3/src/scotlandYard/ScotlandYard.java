package scotlandYard;

import SYSimulation.src.sim.SYSimulation;

import java.io.FileNotFoundException;
import java.awt.Color;
import java.io.IOException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Kürzeste Wege im Scotland-Yard Spielplan mit A* und Dijkstra.
 * @author Oliver Bittel
 * @since 27.02.2019
 */
public class ScotlandYard {

	/**
	 * Fabrikmethode zur Erzeugung eines gerichteten Graphens für den Scotland-Yard-Spielplan.
	 * <p>
	 * Liest die Verbindungsdaten von der Datei ScotlandYard_Kanten.txt.
	 * Für die Verbindungen werden folgende Gewichte angenommen:
	 * U-Bahn = 5, Taxi = 2 und Bus = 3.
	 * Falls Knotenverbindungen unterschiedliche Beförderungsmittel gestatten,
	 * wird das billigste Beförderungsmittel gewählt. 
	 * Bei einer Vebindung von u nach v wird in den gerichteten Graph sowohl 
	 * eine Kante von u nach v als auch von v nach u eingetragen.
	 * @return Gerichteter und Gewichteter Graph für Scotland-Yard.
	 * @throws FileNotFoundException
	 */
	public static DirectedGraph<Integer> getGraph() throws FileNotFoundException {

		DirectedGraph<Integer> sy_graph = new AdjacencyListDirectedGraph<>();
		Scanner in = new Scanner(new File("ScotlandYard_Kanten.txt"));

		String line;

		while (in.hasNextLine()) {
			line = in.nextLine();
			String[] w = line.split(" ");

			int u = Integer.parseInt(w[0]);
			int v = Integer.parseInt(w[1]);

			int dist = 0;
			int oldDist = Integer.MAX_VALUE;

			sy_graph.addVertex(u);
			sy_graph.addVertex(v);

			switch(w[2]) {
				case "Taxi":
					dist = 2;
					break;
				case "Bus":
					dist = 3;
					break;
				case "UBahn":
					dist = 5;
					break;
			}

			if (sy_graph.containsEdge(u, v)) {
				oldDist = (int) sy_graph.getWeight(u, v);
			}
			if (oldDist < dist)
				dist = oldDist;

			sy_graph.addEdge(u, v, dist);
			sy_graph.addEdge(v, u, dist);
		}
		
		// Test, ob alle Kanten eingelesen wurden: 
		System.out.println("Number of Vertices:       " + sy_graph.getNumberOfVertexes());	// 199
		System.out.println("Number of directed Edges: " + sy_graph.getNumberOfEdges());	  	// 862
		double wSum = 0.0;
		for (Integer v : sy_graph.getVertexSet())
			for (Integer w : sy_graph.getSuccessorVertexSet(v))
				wSum += sy_graph.getWeight(v,w);
		System.out.println("Sum of all Weights:       " + wSum);	// 1972.0
		
		return sy_graph;
	}


	/**
	 * Fabrikmethode zur Erzeugung einer Heuristik für die Schätzung
	 * der Distanz zweier Knoten im Scotland-Yard-Spielplan.
	 * Die Heuristik wird für A* benötigt.
	 * <p>
	 * Liest die (x,y)-Koordinaten (Pixelkoordinaten) aller Knoten von der Datei
	 * ScotlandYard_Knoten.txt in eine Map ein.
	 * Die zurückgelieferte Heuristik-Funktion estimatedCost
	 * berechnet einen skalierten Euklidischen Abstand.
	 * @return Heuristik für Scotland-Yard.
	 * @throws FileNotFoundException
	 */
	public static Heuristic<Integer> getHeuristic() throws FileNotFoundException {
		return new ScotlandYardHeuristic();
	}

	/**
	 * Scotland-Yard Anwendung.
	 * @param args wird nicht verewendet.
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		DirectedGraph<Integer> syGraph = getGraph();
		
		//Heuristic<Integer> syHeuristic = null; // Dijkstra
		Heuristic<Integer> syHeuristic = getHeuristic(); // A*

		ShortestPath<Integer> sySp = new ShortestPath<Integer>(syGraph,syHeuristic);

		sySp.searchShortestPath(65,157);
		System.out.println("Distance = " + sySp.getDistance()); // 9.0

		sySp.searchShortestPath(1,175);
		System.out.println("Distance = " + sySp.getDistance()); // 25.0

		sySp.searchShortestPath(1,173);
		System.out.println("Distance = " + sySp.getDistance()); // 22.0


		SYSimulation sim;
		try {
			sim = new SYSimulation();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		sySp.setSimulator(sim);
		sim.startSequence("Shortest path from 1 to 173");

		//sySp.searchShortestPath(65,157); // 9.0
		//sySp.searchShortestPath(1,175); //25.0
		
		sySp.searchShortestPath(1,173); //22.0
		// bei Heuristik-Faktor von 1/10 wird nicht der optimale Pfad produziert.
		// bei 1/30 funktioniert es.

		System.out.println("Distance = " + sySp.getDistance());
		List<Integer> sp = sySp.getShortestPath();

		for (int c : sySp.shortP) {
			sim.visitStation(c, Color.BLUE);
		}

		int a = -1;
		for (int b : sp) {
			if (a != -1)
				sim.drive(a, b, Color.RED.darker());
			sim.visitStation(b);
			a = b;
		}


        sim.stopSequence();
    }

}

class ScotlandYardHeuristic implements Heuristic<Integer> {

	public Map<Integer,Point> coord = new HashMap<>(); // Ordnet jedem Knoten seine Koordinaten zu

	private static class Point {
		int x;
		int y;
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public ScotlandYardHeuristic() throws FileNotFoundException {
		Scanner in = new Scanner(new File("ScotlandYard_Knoten.txt"));

		String line;

		while (in.hasNextLine()) {
			line = in.nextLine();
			String[] w = line.split("[\\t|\\s]+");

			Point p = new Point(Integer.parseInt(w[1]), Integer.parseInt(w[2]));

			coord.put(Integer.parseInt(w[0]), p);
		}
	}

	public double estimatedCost(Integer u, Integer v) {
		ScotlandYardHeuristic.Point vP = coord.get(u);
		ScotlandYardHeuristic.Point wP = coord.get(v);

		int x = (vP.x - wP.x);
		int y = (vP.y - wP.y);

		double betrag = Math.sqrt((x * x) + (y * y));

		return betrag / 30;
	}
}

