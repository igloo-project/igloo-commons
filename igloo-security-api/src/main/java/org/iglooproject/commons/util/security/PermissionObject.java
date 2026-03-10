package org.iglooproject.commons.util.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PermissionObject {

  String DEFAULT_PERMISSION_OBJECT_NAME = "permissionObject";

  String value() default DEFAULT_PERMISSION_OBJECT_NAME;
}
