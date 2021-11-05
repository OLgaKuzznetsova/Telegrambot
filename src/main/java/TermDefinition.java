import java.util.ArrayList;

public class TermDefinition {
    private String term;
    private String definition;

    public void setValues(String iTerm, String iDefinition) {
        term = iTerm;
        definition = iDefinition;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition(boolean capitalize) {
        if (capitalize){
            definition = definition.substring(0, 1).toUpperCase() + definition.substring(1);
        }
        return definition;
    }
}
