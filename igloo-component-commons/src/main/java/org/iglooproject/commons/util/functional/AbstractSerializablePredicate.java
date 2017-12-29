package org.iglooproject.commons.util.functional;

import java.io.Serializable;

import com.google.common.base.Predicate;

/**
 * A simple class enabling declaration of anonymous serializable predicates.
 * @deprecated use {@link SerializablePredicate} instead
 */
@Deprecated
public abstract class AbstractSerializablePredicate<T> implements Predicate<T>, Serializable {
	
	private static final long serialVersionUID = -1795574395617921000L;

}
