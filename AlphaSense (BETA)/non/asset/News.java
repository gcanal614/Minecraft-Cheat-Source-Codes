package non.asset;


public class News {
    private String label;
    private String id;

    public News(String label, String id) {
        this.label = label;
    }
    public void onRun(final String[] s) {
    }

    public String getLabel() {
        return this.label;
    }
    
    public String getId() {
    	return this.id;
    }
}
