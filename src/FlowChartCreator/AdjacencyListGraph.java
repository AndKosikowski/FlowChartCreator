package FlowChartCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


public class AdjacencyListGraph<T> extends Graph<T> {
	Map<T,Vertex> keyToVertex;
	
	private class Vertex {
		T key;
		List<Vertex> successors;
		List<Vertex> predecessors;
		
		Vertex(T key) {
			this.key = key;
			this.successors = new ArrayList<Vertex>();
			this.predecessors = new ArrayList<Vertex>();
		}
	}
	
	AdjacencyListGraph(Set<T> keys) {
		this.keyToVertex = new HashMap<T,Vertex>();
		for (T key : keys) {
			Vertex v = new Vertex(key);
			this.keyToVertex.put(key, v);
		}
	}
	
	public Vertex validKeyCheck(T key) {//checks if valid key and returns vertex
		if(!hasVertex(key)) {
			throw new NoSuchElementException();
		}
		return keyToVertex.get(key);
	}
	

	@Override
	public int size() {
		return keyToVertex.size();
	}

	@Override
	public int numEdges() {
		// TODO Auto-generated method stub
		Set<T> set = keyToVertex.keySet();
		Set<Vertex> vertices = new HashSet<Vertex>();
		for(T key: set) {
			vertices.add(keyToVertex.get(key));
		}
		int edges = 0;
		for(Vertex vtx: vertices) {
			edges += vtx.successors.size();
		}
		
		return edges;
	}

	@Override
	public boolean addEdge(T from, T to) {
		Vertex vtx1 = validKeyCheck(from);
		Vertex vtx2 = validKeyCheck(to);
		if(vtx1.successors.contains(vtx2)) {
			return false;
		}
		vtx1.successors.add(vtx2);
		vtx2.predecessors.add(vtx1);
		return true;
	}

	@Override
	public boolean hasVertex(T key) {
		return keyToVertex.containsKey(key);
	}

	@Override
	public boolean hasEdge(T from, T to) throws NoSuchElementException {
		Vertex vtx1 = validKeyCheck(from);
		Vertex vtx2 = validKeyCheck(to);
		if(vtx1.successors.contains(vtx2)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEdge(T from, T to) throws NoSuchElementException {
		Vertex vtx1 = validKeyCheck(from);
		Vertex vtx2 = validKeyCheck(to);
		if(vtx1.successors.contains(vtx2)) {
			vtx1.successors.remove(vtx2);
			vtx2.predecessors.remove(vtx1);
			return true;
		}
		return false;
	}

	@Override
	public int outDegree(T key) {
		return validKeyCheck(key).successors.size();
	}

	@Override
	public int inDegree(T key) {
		return validKeyCheck(key).predecessors.size();
	}

	@Override
	public Set<T> keySet() {
		return keyToVertex.keySet();
	}

	@Override
	public Set<T> successorSet(T key) {
		Vertex vtx = validKeyCheck(key);
		Set<T> set = new HashSet<T>();
		for(Vertex successor: vtx.successors) {
			set.add(successor.key);
		}
		return set;
	}

	@Override
	public Set<T> predecessorSet(T key) {
		Vertex vtx = validKeyCheck(key);
		Set<T> set = new HashSet<T>();
		for(Vertex predecessor: vtx.predecessors) {
			set.add(predecessor.key);
		}
		return set;
	}

	@Override
	public Iterator<T> successorIterator(T key) {
		// TODO Auto-generated method stub
		return new successorItr(validKeyCheck(key));
	}
	
	public class successorItr implements Iterator<T> {

		Vertex vertex;
		int position;
		
		public successorItr(Vertex vertex) {
			this.vertex = vertex;
			position = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(position >= vertex.successors.size()) {
				return false;
			}
			return true;
		}

		@Override
		public T next() {
			while(hasNext()) {
				position++;
				return vertex.successors.get(position-1).key;
			}
			return null;
		}
		
	}

	@Override
	public Iterator<T> predecessorIterator(T key) {
		return new predecessorItr(validKeyCheck(key));
	}
	
	public class predecessorItr implements Iterator<T> {

		Vertex vertex;
		int position;
		
		public predecessorItr(Vertex vertex) {
			this.vertex = vertex;
			position = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(position >= vertex.predecessors.size()) {
				return false;
			}
			return true;
		}

		@Override
		public T next() {
			while(hasNext()) {
				position++;
				return vertex.predecessors.get(position-1).key;
			}
			return null;
		}
	}


	


}
