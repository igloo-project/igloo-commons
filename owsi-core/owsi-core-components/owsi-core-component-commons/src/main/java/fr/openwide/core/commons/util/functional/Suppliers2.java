package fr.openwide.core.commons.util.functional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public final class Suppliers2 {

	private Suppliers2() { }
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) // LinkedListSupplier works for any T
	public static <T> Supplier<LinkedList<T>> linkedList() {
		return (Supplier) LinkedListSupplier.INSTANCE;
	}
	
	private static enum LinkedListSupplier implements Supplier<LinkedList<?>> {
		INSTANCE;
		
		@Override
		public LinkedList<?> get() {
			return Lists.newLinkedList();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) // ArrayListSupplier works for any T
	public static <T> Supplier<ArrayList<T>> arrayList() {
		return (Supplier) ArrayListSupplier.INSTANCE;
	}
	
	private static enum ArrayListSupplier implements Supplier<ArrayList<?>> {
		INSTANCE;
		
		@Override
		public ArrayList<?> get() {
			return Lists.newArrayList();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) // HashSetSupplier works for any T
	public static <T> Supplier<HashSet<T>> hashSet() {
		return (Supplier) HashSetSupplier.INSTANCE;
	}
	
	private static enum HashSetSupplier implements Supplier<HashSet<?>> {
		INSTANCE;
		
		@Override
		public HashSet<?> get() {
			return Sets.newHashSet();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) // HashSetSupplier works for any T
	public static <T> Supplier<LinkedHashSet<T>> linkedHashSet() {
		return (Supplier) LinkedHashSetSupplier.INSTANCE;
	}
	
	private static enum LinkedHashSetSupplier implements Supplier<HashSet<?>> {
		INSTANCE;
		
		@Override
		public HashSet<?> get() {
			return Sets.newLinkedHashSet();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) // NaturalOrderTreeSetSupplier works for any T
	public static <T extends Comparable> Supplier<TreeSet<T>> treeSet() {
		return (Supplier) NaturalOrderTreeSetSupplier.INSTANCE;
	}

	private static enum NaturalOrderTreeSetSupplier implements Supplier<TreeSet<?>> {
		INSTANCE;
		
		@Override
		@SuppressWarnings("rawtypes")
		public TreeSet<?> get() {
			return (TreeSet) Sets.newTreeSet();
		}
	}
	
	public static <T> Supplier<TreeSet<T>> treeSet(Comparator<? super T> comparator) {
		return new ComparatorTreeSetSupplier<T>(comparator);
	}
	
	private static class ComparatorTreeSetSupplier<T> implements Supplier<TreeSet<T>>, Serializable {
		private static final long serialVersionUID = 6476238745119640079L;
		
		private final Comparator<? super T> comparator;
		
		public ComparatorTreeSetSupplier(Comparator<? super T> comparator) {
			super();
			this.comparator = comparator;
		}

		@Override
		public TreeSet<T> get() {
			return Sets.newTreeSet(comparator);
		}
	}

}
