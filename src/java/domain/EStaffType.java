
package domain;

public enum EStaffType {
    SUPERVISOR("Supervisor"), 
    SECURITY("Security"), 
    TECHNICIAN("Technician"), 
    RECEPTIONIST("Receptionist"), 
    CLEANER("Cleaner"), 
    OTHER("Other");
    
    private String label;

    private EStaffType(String label) {
        this.label = label;
    }
    
    

}
