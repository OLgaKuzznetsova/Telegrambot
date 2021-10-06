public class Data {
    private String term;
    private String definition;

    public void setValues(String iTerm, String iDefinition) {
        term = iTerm;
        definition = iDefinition;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }
}
