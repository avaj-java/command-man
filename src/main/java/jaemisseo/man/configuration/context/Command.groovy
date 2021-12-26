package jaemisseo.man.configuration.context;

public class Command {

    public Command(String[] args){
        this.args = args;
    }

    String[] args = null;

    String applicationName = null;

    boolean modeCloningWithDownload = false;

}
