package jaemisseo.man.configuration.annotation.type

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface AfterCommandMessenger {

    String value() default "hoya"

}