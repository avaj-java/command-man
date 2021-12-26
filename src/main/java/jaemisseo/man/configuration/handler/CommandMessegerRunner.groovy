package jaemisseo.man.configuration.handler;

import jaemisseo.man.configuration.context.Environment;
import jaemisseo.man.configuration.context.SelfAware;

import java.util.List;

public class CommandMessegerRunner {

    public CommandMessegerRunner(
            Environment environment,
            List<AbstractBeforeCommandMessenger> beforeHandlers,
            List<AbstractCommandMessenger> handlers,
            List<AbstractAfterCommandMessenger> afterHandlers
    ){
        this.environment = environment;
        this.beforeHandlers = beforeHandlers;
        this.handlers = handlers;
        this.afterHandlers = afterHandlers;
    }

    Environment environment = null;
    List<AbstractBeforeCommandMessenger> beforeHandlers = null;
    List<AbstractCommandMessenger> handlers = null;
    List<AbstractAfterCommandMessenger> afterHandlers = null;


    public boolean run(Environment environment, SelfAware selfAware){
        //- Before
        beforeHandlers.each { handler ->
            if (handler.check(environment))
                handler.run(environment, selfAware);
        }

        //- Run
        handlers.each { handler ->
            try{
                if (handler.check(environment))
                    handler.run(environment, selfAware);
            }catch(Exception e){
                e.printStackTrace();
            }
        };

        //- After
        afterHandlers.each{ handler ->
            if (handler.check(environment))
                handler.run(environment, selfAware);
        };

        return true;
    }


}
