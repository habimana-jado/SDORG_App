
package domain;

public enum EGender {
    MALE("Male"), 
    FEMALE("Female");
    
    private String label;

    private EGender(String label) {
        this.label = label;
    }
    
    
}
