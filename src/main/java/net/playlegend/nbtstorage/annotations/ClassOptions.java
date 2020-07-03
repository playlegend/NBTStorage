package net.playlegend.nbtstorage.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassOptions {

  FilterType filterType() default FilterType.NONE;

  enum FilterType {
    NONE,
    INCLUDE,
    EXCLUDE
  }

}
