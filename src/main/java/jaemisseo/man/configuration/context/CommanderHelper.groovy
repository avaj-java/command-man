package jaemisseo.man.configuration.context

import jaemisseo.man.PropMan
import jaemisseo.man.TimeMan
import jaemisseo.man.configuration.exception.WantToRestartException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class CommanderHelper {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String APPLICATION_INSTALLER_MAKER = 'installer-maker'
    public static final String APPLICATION_INSTALLER = 'installer'
    public static final String APPLICATION_HOYA = 'hoya'

    protected CommanderConfig config
    protected TimeMan timeman

    public CommanderConfig getConfig(){
        return this.config;
    }

    public void setConfig(CommanderConfig config){
        this.config = config;
    }

    public TimeMan getTimeman(){
        return this.timeman;
    }


//    static String getApplicationName(PropMan propmanExternal){
//        String applicationName = null;
//
//        List<String> specialValueList = propmanExternal.get('--')
//
//        if (specialValueList.contains(APPLICATION_INSTALLER)){
//            applicationName = APPLICATION_INSTALLER
//        }else if (specialValueList.contains(APPLICATION_HOYA)){
//            applicationName = APPLICATION_HOYA
//        }else{
//            applicationName = APPLICATION_INSTALLER_MAKER
//        }
//
//        return applicationName
//    }



    /*************************
     * RUN COMMAND
     *   - Your command from Command Line
     * @param config
     *************************/
    void runCommand(String command){
        runCommand([command])
    }

    void runCommand(List<String> commandList){
        runCommand(commandList, null)
    }

    void runCommand(List<String> commandList, Class jobClass){
        def jobInstance = (jobClass) ? config.findInstance(jobClass) : null
        try{
            commandList.each{
                if (config.hasCommand(it)){
                    config.command(it)
                }else{
                    jobInstance.commandName = it
                    config.command(jobClass)
                }
            }

        }catch(Exception e){
            if (e instanceof WantToRestartException
            || (e.cause && e.cause instanceof WantToRestartException)
            ){
                runCommand(commandList, jobClass)
            }else{
                throw e
            }
        }
    }



    /*************************
     * FINISH START
     * @param e
     *************************/
    void finishCommand(List<String> commandCalledByUserList, double elapseTime){
        //Show ElapseTime
        logger.info """
            - Command    : ${commandCalledByUserList.join(', ')} 
            - ElapseTime : ${elapseTime}s"""
    }



    /*************************
     * LOG arguments from terminal
     * @param e
     *************************/
    void logExternalProperty(PropMan propmanExternal){
        //TODO:INFO => DEBUG
        logger.debug(" [ CHECK External Option ] ")

        //Command Properties
        List commandProperties = propmanExternal['']
        commandProperties.each{ String commandOption ->
            logger.debug("${commandOption}")
        }

        //Properties
        propmanExternal.properties.each{
            if (!['--', ''].contains(it.key))
                logger.debug("${it.key}=${it.value}")
        }

        //Special Properties
        List specialProperties = propmanExternal['--']
        specialProperties.each{ String specialOption ->
            logger.debug("--${specialOption}")
        }
        logger.debug("")
    }

}
