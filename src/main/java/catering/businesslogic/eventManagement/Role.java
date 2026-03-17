package catering.businesslogic.eventManagement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import catering.businesslogic.staffManagement.StaffMember;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

public class Role{
    
    private int eventId;
    private String position;
    private StaffMember staffMember;
    private boolean kitchen;

    public Role(int eventId, String position, StaffMember staffMember, boolean kitchen){
        this.position = position;
        this.staffMember = staffMember;
        this.kitchen = kitchen;
        this.eventId = eventId;
    }

    public Role(String position){
        this.position = position;
    }

    public String getPosition(){
        return this.position;
    }

    public void setPosition(String position){
        this.position = position;
    }
    
    public StaffMember getContact(){
        return this.staffMember;
    }

    public int getContactId(){
       return staffMember.getContactId();
    }

    public void setContact(StaffMember staffMember){
        this.staffMember = staffMember;
    }

    public boolean getKitchen(){
        return this.kitchen;
    }

    public boolean isCook(){
        return kitchen;
    }

    public boolean isWaiter(){
        return !kitchen;
    }

    public int getEventId(){
        return eventId;
    }

    /**DATABASE OPERATIONS */

    public static ArrayList<Role> load(int id){
        String query = "SELECT `position`, `kitchen`, `staff_member_id` ,`event_id` FROM Roles WHERE `event_id` = ?";
        ArrayList<Role> ret = new ArrayList<Role>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(new Role(rs.getInt("event_id"), rs.getString("position"), StaffMember.load(rs.getInt("staff_member_id")), rs.getBoolean("kitchen")));
                }while(rs.next());
            }
        }, id);
        
        return ret;
    }

    public String toString(){
        return position + " | " + staffMember.getName() + " " + staffMember.getSurname();
    }
}