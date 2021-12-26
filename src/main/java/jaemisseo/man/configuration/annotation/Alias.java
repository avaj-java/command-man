package jaemisseo.man.configuration.annotation;

import java.lang.annotation.*;

/**
 * Created by sujkim on 2017-06-11.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {

    String value() default "";

}