package model;
import java.io.Serializable;
public class Tag implements Serializable{
    private String tagType;
    private String value;

    public Tag(String tagType, String value) {
        this.tagType = tagType;
        this.value = value;
    }
    /**
     * getter method for tagtype
     * @return
     */
    public String getTagtype() {
        return tagType;

    }
    /**
     * getter method for value
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * setter method for tag type
     * @param tag
     */
    public void setTagtype(String tag) {
        tagType = tag;
    }
    /**
     * setter method for value
     * @param value
     */
    public void setValue(String newValue) {
        value = newValue;
    }
    /**
     * to string for tag
     */
    public String toString() {
        return getTagtype() + ":" + getValue();
    }
    /**
     * compares if two tags are the same/tag already exists
     * @param tag tagtype
     * @param val value
     * @return 0 if not the same, 1 if they are the same
     */
    public int isTagEqual(String tag, String val) {
        int t = tagType.compareTo(tag);
        int v = value.compareToIgnoreCase(val);
        if (t == 0 && v==0) {
            return 1;
        }
        else {
            return 0;
        }

    }
    /**
     * compares tag key
     * @param t
     * @return
     */
    public int  compareTagtype(String t) {
        return tagType.compareTo(t);
    }
    /**
     * compares tag value
     * @param val
     * @return
     */
    public int compareValue(String val) {
        return value.compareTo(val);
    }

}