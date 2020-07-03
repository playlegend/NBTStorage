package net.playlegend.nbtstorage.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Options {

  String nbtKey() default "";

  String writerMethod() default "";

  String readerMethod() default "";
  
}
