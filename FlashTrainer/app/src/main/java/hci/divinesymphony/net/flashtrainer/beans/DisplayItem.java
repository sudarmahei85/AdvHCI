package hci.divinesymphony.net.flashtrainer.beans;

/**
 * Created by rick on 3/8/15.
 */
public class DisplayItem {

    private final String text;
    private final String imageFile;
    private final String videoFile;
    private final String id;

    public DisplayItem(String text, String id) {
        this.text = text;
        this.imageFile = null;
        this.videoFile = null;
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public String getId() {
        return this.id;
    }

    //TODO add constructor and getters for multimedia, after we figure out how to represent them

}
