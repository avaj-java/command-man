package jaemisseo.man.configuration.handler;

import jaemisseo.man.configuration.config.Environment;
import jaemisseo.man.configuration.config.SelfAware;

abstract public class AbstractCommandMessenger {

    abstract public boolean check(Environment environment);

    abstract public void run(Environment environment, SelfAware selfAware);



}
