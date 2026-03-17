package catering.businesslogic.eventManagement;

import catering.businesslogic.staffManagement.*;
import catering.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.sql.*;


public class StaffEventInfo{

    private int eventId;
    private int numbersOfWaitStaff;
    private int numberOfKitchenStaff;
    private ArrayList<ContactedStaff> contactedStaff = new ArrayList<ContactedStaff>();
    private ArrayList<Role> roles = new ArrayList<Role>();

    public StaffEventInfo(){
        
    }

    public int getEventId(){
        return eventId;
    }

    public int getNumberOfWaitStaff(){
        return this.numbersOfWaitStaff;
    }
    public int getNumberOfKitchenStaff(){
        return this.numberOfKitchenStaff;
    }

    //roles
    public ArrayList<Role> getRoles(){
        return this.roles;
    }
    public Role getRole(int index){
       return this.roles.get(index);
    }
    
    public void setRole(int index, Role role){
        if(!(role.isCook() && roles.get(index).isCook() || role.isWaiter() && roles.get(index).isWaiter())){
            if(role.isCook()){
                numbersOfWaitStaff--;
                numberOfKitchenStaff++;
            }
            else{
                numbersOfWaitStaff++;
                numberOfKitchenStaff--;
            }
        }
        roles.set(index, role);
    }

    public void addRole(Role role){
        if(role.isCook()){
            numberOfKitchenStaff++;
        }
        else{
            numbersOfWaitStaff++;
        }

        roles.add(role);
    }
    
    public void deleteRole(int index){
        if(roles.get(index).isCook()){
            numberOfKitchenStaff--;
        }
        else{
            numbersOfWaitStaff--;
        }

        roles.remove(index);
    }
    //cont staff
    public ArrayList<ContactedStaff> getContactedStaffs(){
        return contactedStaff;
    }

    public ContactedStaff getContactedStaff(int index){
        return contactedStaff.get(index);
    }

    public void addContactedStaff(ContactedStaff cont){
        this.contactedStaff.add(cont); 
    }
    
    public void deleteContactedStaff(int index){
        this.contactedStaff.remove(index);
    }
    public void setEndDateContactedStaff(int index,LocalDate endDate){
        contactedStaff.get(index).setEndDate(endDate);
    }
    public void setPositionContactedStaff(int index,String position){
        contactedStaff.get(index).setPosition(position);
    }

    public void setNumberOfWaitStaff(int i){
        numbersOfWaitStaff = i;
    }
    public void setNumberOfKitchenStaff(int i){
        numberOfKitchenStaff = i;
    }

    public boolean containsStaffMember(StaffMember staffMember){
        for(Role r: roles){
            if(r.getContact().equals(staffMember))
                return true;
        }
        return false;
    }

    public List<Role> getRoleStaffMember(StaffMember staffMember){
        List<Role> toRet = new ArrayList<>();
        for(Role r: roles){
            if(r.getContact().equals(staffMember))
                toRet.add(r);
        }
        return toRet;
    }


    /** DATABASE OPERATIONS */

    public static StaffEventInfo load(int id){
        String query = "SELECT event_id, numbers_of_wait_staff, numbers_of_kitchen_staff FROM StaffEventInfos WHERE event_id = ?";
        StaffEventInfo ret = new StaffEventInfo();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                ret.eventId = rs.getInt("event_id");
                ret.numbersOfWaitStaff = rs.getInt("numbers_of_wait_staff");
                ret.numberOfKitchenStaff = rs.getInt("numbers_of_kitchen_staff");
                
                ret.contactedStaff = ContactedStaff.load(id);
                ret.roles = Role.load(id);
            }
        }, id);

        return ret;
    }


    public static void updateNumberOfWaitStaff(StaffEventInfo staffEventInfo,int i){
        String query = "UPDATE StaffEventInfos SET `numbers_of_wait_staff` = ? WHERE `event_id` = ?";
        PersistenceManager.executeUpdate(query,i,staffEventInfo.eventId);
    }
    public static void updateNumberOfKitchenStaff(StaffEventInfo staffEventInfo,int i){
        String query = "UPDATE StaffEventInfos SET `numbers_of_kitchen_staff` = ? WHERE `event_id` = ?";
        PersistenceManager.executeUpdate(query,i,staffEventInfo.eventId);
    }

    public static void updateModifiedRole(StaffEventInfo staffEventInfo, int index,Role role){
        String query = "UPDATE Roles SET `position` = ?, `kitchen` = ?, `staff_member_id` = ?, `event_id` = ? WHERE `event_id` = ? AND `staff_member_id` = ?";
        PersistenceManager.executeUpdate(query,role.getPosition(), role.getKitchen(), role.getContactId(), role.getEventId(), role.getEventId(), role.getContactId());
    }
    public static void updateAddedRole(StaffEventInfo staffEventInfo,Role role){
        String query = "INSERT INTO Roles(position, kitchen, staff_member_id, event_id ) VALUES (?, ?, ?, ?)";
        PersistenceManager.executeUpdate(query, role.getPosition(),role.getKitchen() ,role.getContactId(), role.getEventId());
    }
    public static void updateDeletedRole(StaffEventInfo staffEventInfo,int index){
        String query = "DELETE FROM Roles WHERE `event_id` = ? AND `staff_member_id` = ?";
        Role toDelete = staffEventInfo.getRole(index);
        PersistenceManager.executeUpdate(query,toDelete.getEventId(),toDelete.getContactId());
    }

    public static void updateAddedContactedStaff(StaffEventInfo staffEventInfo,ContactedStaff cont){
        String query = "INSERT INTO ContactedStaffs(date_requested, date_end, position, event_id , contact_id) VALUES (?, ?, ?, ?, ?)";
        PersistenceManager.executeUpdate(query, cont.getRequestDate(), cont.getEndDate(), cont.getPosition(), cont.getEventId(), cont.getContactId());
    }
    public static void updateDeleteContactedStaff(StaffEventInfo staffEventInfo,int index){
        String query = "DELETE FROM ContactedStaffs WHERE `event_id` = ? AND `contact_id` = ?";
        ContactedStaff toDelete = staffEventInfo.getContactedStaff(index);
        PersistenceManager.executeUpdate(query,toDelete.getEventId(),toDelete.getContactId());
    }
    public static void updateEndDateContactedStaff(StaffEventInfo staffEventInfo, int index, LocalDate endDate){
        String query = "UPDATE ContactedStaffs SET `date_end` = ? WHERE `event_id` = ? AND `contact_id` = ?";
        ContactedStaff toUpdate = staffEventInfo.getContactedStaff(index);
        PersistenceManager.executeUpdate(query, endDate, toUpdate.getEventId(), toUpdate.getContactId());
    }
    public static void updatePositionContactedStaff(StaffEventInfo staffEventInfo, int index, String position){
        String query = "UPDATE ContactedStaffs SET `position` = ? WHERE `event_id` = ? AND `contact_id` = ?";
        ContactedStaff toUpdate = staffEventInfo.getContactedStaff(index);
        PersistenceManager.executeUpdate(query, position, toUpdate.getEventId(), toUpdate.getContactId());
    }
}