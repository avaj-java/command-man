package jaemisseo.man.configuration.handler;

import jaemisseo.man.configuration.context.Environment;
import jaemisseo.man.configuration.context.SelfAware;

abstract public class AbstractCommandMessenger {

    abstract public boolean check(Environment environment);

    abstract public void run(Environment environment, SelfAware selfAware);



}
