package org.iglooproject.commons.util.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.common.base.Equivalence;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public final class CollectionUtils {
	
	private CollectionUtils() { }
	
	/**
	 * Replaces all elements in `dst` by those in `src`.
	 * 
	 * This method is view-proof : it will work as expected even if `src` is a view of `dst`,
	 * i.e. if `src`'s content depends on `dst`'s (see for example
	 * {@link Collections#unmodifiableCollection(Collection)}, {@link Sets#union(java.util.Set, java.util.Set)}, ...).
	 * 
	 * In the process, this method may copy the elements of `src` in a new collection,
	 * hence it is not advisable to use it on large collections or in frequently called methods.
	 *  
	 * @param dst The collection whose content is to be replaced. Not null.
	 * @param src The collection whose content is to be copied to `dst`, or null to simply clear `dst`.
	 */
	public static <T> void replaceAll(Collection<T> dst, Collection<? extends T> src) {
		if (src == null) {
			dst.clear();
		} else {
			Collection<T> elements = Lists.newArrayList(src);
			dst.clear();
			dst.addAll(elements);
		}
	}
	
	public static <T> void replaceAll(
		Collection<T> dst,
		Collection<? extends T> src,
		Consumer<T> addConsumer,
		Consumer<T> removeConsumer
	) {
		src.stream()
			.filter(((Predicate<T>) dst::contains).negate())
			.forEach(addConsumer);
		
		dst.stream()
			.filter(((Predicate<T>) src::contains).negate())
			.forEach(removeConsumer);
		
		replaceAll(dst, src);
	}
	
	/**
	 * Same as {@link CollectionUtils#replaceAll(Collection, Collection)}, but for {@link Map Maps}
	 */
	public static <K, V> void replaceAll(Map<K, V> dst, Map<? extends K, ? extends V> src) {
		if (src == null) {
			dst.clear();
		} else {
			Map<K, V> elements = Maps.newLinkedHashMap(src);
			dst.clear();
			dst.putAll(elements);
		}
	}
	
	/**
	 * **WARNING:** very low efficiency `(O(N*N))`
	 * @return A list containing the elements of source that are not in filter, using `equivalence` to distinguish different elements.
	 */
	public static <T> List<T> difference(Iterable<? extends T> source, Iterable<? extends T> filter, Equivalence<? super T> equivalence) {
		final List<T> result = new LinkedList<>();
		if (source != null && filter != null) {
			sourceLoop: for (T sourceElement : source) {
				for (T filterElement : filter) {
					if (equivalence.equivalent(sourceElement, filterElement)) {
						continue sourceLoop;
					}
				}
				result.add(sourceElement);
			}
		} else if (source != null && filter == null) {
			Iterables.addAll(result, source);
		}
		return result;
	}

	/**
	 * **WARNING:** very low efficiency `(O(N*N))`
	 * 
	 * @return A list containing the elements of source that are also in filter, using `equivalence` to distinguish different elements.
	 */
	public static <T> List<T> intersection(Iterable<? extends T> source, Iterable<? extends T> filter, Equivalence<? super T> equivalence) {
		final List<T> result = new LinkedList<>();
		if (source != null && filter != null) {
			sourceLoop: for (T sourceElement : source) {
				for (T filterElement : filter) {
					if (equivalence.equivalent(sourceElement, filterElement)) {
						result.add(sourceElement);
						continue sourceLoop;
					}
				}
			}
		}
		return result;
	}

}
