/*
    Copyright (C) 2006-2011  le.jhe@freesbee.fr

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package org.jhe.csviterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author le.jhe@freesbee.fr
 * @license GPL v3
 */
public class CSVArrayList implements CSVList {
	private List<Map<String,String>> list;
	CSVIteratorImpl iter;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public CSVArrayList(String filePath) throws IOException {
		this.list = new ArrayList<Map<String,String>>();
		this.iter = new CSVIteratorImpl(filePath);
		while (iter.hasNext()) {
			try {
				list.add(iter.nextRecord());
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<Map<String, String>> iterator() {
		return list.iterator();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean add(Map<String, String> o) {
		return list.add(o);
	}

	public boolean remove(Object o) {
		return list.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return list.contains(c);
	}

	public boolean addAll(Collection<? extends Map<String, String>> c) {
		return list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Map<String, String>> c) {
		return list.addAll(index,c);
	}

	public boolean removeAll(Collection<?> c) {
		return removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return retainAll(c);
	}

	public void clear() {
		list.clear();
	}

	public Map<String, String> get(int index) {
		return list.get(index);
	}

	public Map<String, String> set(int index, Map<String, String> element) {
		return list.set(index, element);
	}

	public void add(int index, Map<String, String> element) {
		list.add(index, element);
	}

	public Map<String, String> remove(int index) {
		return remove(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<Map<String, String>> listIterator() {
		return list.listIterator();
	}

	public ListIterator<Map<String, String>> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<Map<String, String>> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
	
	public List<String> getHeaders() throws IOException {
		List<String> ret = null;
		
		ret = iter.getHeaders();
		CSVIteratorImpl.debug("iter.getHeaders()=>"+ret);
		return ret;
	}
}
