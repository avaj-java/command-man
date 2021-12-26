package jaemisseo.man.configuration.context;

class SelfAware {

    void init(CommanderConfig config){
        this.config = config;

    }

    CommanderConfig config;

}
