package name.mymiller.lang.singleton;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author jmiller Indicates classes with this annotation type are singletons
 * and can be access via the SingletonInterface.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface Singleton {

}
