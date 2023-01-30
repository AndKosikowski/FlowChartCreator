package FlowChartCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class AdjacencyMatrixGraph<T> extends Graph<T> {
	Map<T,Integer> keyToIndex;
	List<T> indexToKey;
	int[][] matrix;
	
	AdjacencyMatrixGraph(Set<T> keys) {
		int size = keys.size();
		this.keyToIndex = new HashMap<T,Integer>();
		this.indexToKey = new ArrayList<T>();
		this.matrix = new int[size][size];
		
		int count = 0;
		for(T item: keys) {
			keyToIndex.put(item,count);
			indexToKey.add(count, item);
			count++;
		}
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				matrix[i][j] = 0;
			}
		}
		
		
		// need to populate keyToIndex and indexToKey with info from keys
	}
	
	public int validKeyCheck(T key) {//checks if valid key and returns index
		if(!hasVertex(key)) {
			throw new NoSuchElementException();
		}
		return keyToIndex.get(key);
	}
	
	
	@Override
	public int size() {
		return matrix.length;
	}

	@Override
	public int numEdges() {
		int size = matrix.length;
		int numEdges = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(matrix[i][j] == 1) {
					numEdges++;
				}
			}
		}
		return numEdges;
	}

	@Override
	public boolean addEdge(T from, T to) {
		int fromIndex = validKeyCheck(from);
		int toIndex = validKeyCheck(to);
		
		if(matrix[fromIndex][toIndex] == 1) {
			return false;
		}
		matrix[fromIndex][toIndex] = 1;
		return true;
	}

	@Override
	public boolean hasVertex(T key) {
		return keyToIndex.containsKey(key);
	}

	@Override
	public boolean hasEdge(T from, T to) throws NoSuchElementException {
		int fromIndex = validKeyCheck(from);
		int toIndex = validKeyCheck(to);
		if (matrix[fromIndex][toIndex] == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEdge(T from, T to) throws NoSuchElementException {
		int fromIndex = validKeyCheck(from);
		int toIndex = validKeyCheck(to);
		if (matrix[fromIndex][toIndex] == 0) {
			return false;
		}
		matrix[fromIndex][toIndex] = 0;
		return true;
	}

	@Override
	public int outDegree(T key) {
		int index = validKeyCheck(key);
		int count = 0;
		for(int i = 0; i < matrix.length; i++) {
			if (matrix[index][i] == 1) {
				count++;
			}
		}
		
		return count;
	}

	@Override
	public int inDegree(T key) {
		int index = validKeyCheck(key);
		int count = 0;
		for(int i = 0; i < matrix.length; i++) {
			if (matrix[i][index] == 1) {
				count++;
			}
		}
		return count;
	}

	@Override
	public Set<T> keySet() {
		return keyToIndex.keySet();
	}

	@Override
	public Set<T> successorSet(T key) {
		Integer index = keyToIndex.get(key);
		Set<T> set = new HashSet<T>();
		if(index == null) {
			throw new NoSuchElementException();
		}
		for(int i = 0; i < matrix.length; i++) {
			if(matrix[index][i] == 1) {
				set.add(indexToKey.get(i));
			}
		}
		return set;
	}

	@Override
	public Set<T> predecessorSet(T key) {
		Integer index = keyToIndex.get(key);
		Set<T> set = new HashSet<T>();
		if(index == null) {
			throw new NoSuchElementException();
		}
		for(int i = 0; i < matrix.length; i++) {
			if(matrix[i][index] == 1) {
				set.add(indexToKey.get(i));
			}
		}
		return set;
	}

	@Override
	public Iterator<T> successorIterator(T key) {		
		return new successorItr(validKeyCheck(key));
	}
	
	public class successorItr implements Iterator<T> {

		int position;
		int index;
		
		public successorItr(int index) {
			this.index = index;
			position = 0;
		}
		
		@Override
		public boolean hasNext() {
			for(int i = 0; position+i < matrix.length; i++) {
				if(matrix[index][position+i] == 1) {
					return true;
				}
			}
			
			return false;
		}

		@Override
		public T next() {
			while(hasNext()) {
				position++;
				if(matrix[index][position-1] == 1) {
					return indexToKey.get(position-1);
				}
			}
			return null;
		}
		
	}

	@Override
	public Iterator<T> predecessorIterator(T key) {
		return new predecessorItr(validKeyCheck(key));
	}
	
	public class predecessorItr implements Iterator<T> {

		int position;
		int index;
		
		public predecessorItr(int index) {
			this.index = index;
			position = 0;
		}
		
		@Override
		public boolean hasNext() {
			for(int i = 0; position+i < matrix.length; i++) {
				if(matrix[position+i][index] == 1) {
					return true;
				}
			}
			return false;
		}

		@Override
		public T next() {
			while(hasNext()) {
				position++;
				if(matrix[position-1][index] == 1) {
					return indexToKey.get(position-1);
				}
			}
			return null;
		}
	}
	
	

}
