package knn.loadingV2;

public class SplittedReuterWithTag {
    private String[] text;
    private String tag;

    public SplittedReuterWithTag(String[] text, String tag) {
        this.text = text;
        this.tag = tag;
    }

    public String[] getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }
}
