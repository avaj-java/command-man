package jaemisseo.man.configuration.context


import jaemisseo.man.PropMan
import jaemisseo.man.TimeMan
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Environment extends CommanderHelper {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    String applicationName;
    PropMan propmanExternal;
    PropMan propmanDefault;
    List<String> dashDashOptionList;

    boolean modeNeedToDownloadForRemaking = false;


    /*************************
     *
     * INIT
     *
     *************************/
    public void init(CommanderConfig config){
        this.config = config;
        propmanExternal = config.getPropGen().getExternalProperties();
        propmanDefault = config.getPropGen().getDefaultProperties();
        dashDashOptionList = (List<String>) propmanExternal.get("--");

        /** [Setup] --OPTION **/
        setupLogOption();
        setupOtherOption();

        setupApplicationName();
        setupTime();
    }




    /*************************
     *
     *  Setup from Dash Dash Option
     *
     *************************/
    private void setupLogOption(){
        //- Set Log
        if (dashDashOptionList.contains("error")){
            propmanExternal.set("log.level.console", "error");
            if (dashDashOptionList.contains("log.file"))
                propmanExternal.set("log.level.file", "error");
        }else if (dashDashOptionList.contains("debug")){
            propmanExternal.set("log.level.console", "debug");
            if (dashDashOptionList.contains("log.file"))
                propmanExternal.set("log.level.file", "debug");
        }else if (dashDashOptionList.contains("trace")){
            propmanExternal.set("log.level.console", "trace");
            if (dashDashOptionList.contains("log.file"))
                propmanExternal.set("log.level.file", "trace");
        }
    }

    private void setupOtherOption(){
        //- Set Help Option
        if (dashDashOptionList.contains("help"))
            propmanExternal.set("help", true);

        //- Set Help Option
        if (dashDashOptionList.contains("gen"))
            propmanExternal.set("mode.generate", true);
    }

    private void setupApplicationName(){
        //- Set Application Identity
//        String oldApplicationNameFromCommand = CommanderHelper.getApplicationName(propmanExternal)
//        this.applicationName = oldApplicationNameFromCommand
//        propmanDefault.set("application.name", oldApplicationNameFromCommand)

        propmanDefault.set("application.name", this.applicationName);
    }

    private void setupTime(){
        this.timeman = new TimeMan().init().start();
    }



}
