package catering.businesslogic.staffManagement;
import java.util.*;
import java.time.*;
import catering.businesslogic.UseCaseLogicException; 

import java.sql.*;
import catering.persistence.*;

public class StaffMember{
   
    private boolean kitchen;
    private boolean permanent;
    private int numberOfDaysOff;
    private ArrayList<DateSpan> onHoliday = new ArrayList<>();
    private Contact contact;

    public StaffMember(boolean permanent, int numberOfDaysOff, Contact contact,boolean kitchen)throws UseCaseLogicException{
        this.permanent = permanent;
        this.kitchen = kitchen;
        this.numberOfDaysOff = numberOfDaysOff;
        
        if(contact.containsAddressAndFiscalCode())
            this.contact = contact;
        else
            throw new UseCaseLogicException("Il contatto selezionato non ha un contratto (mancano codice fiscale e indirizzo)");
       
    }

    public StaffMember(){
    
    }
   
    public boolean equals(Object comp){
        return comp instanceof StaffMember && this.contact.equals(((StaffMember)comp).contact);
    }
    
    public String getName(){
        return this.contact.getName();
    }
    public void setName(String name){
        this.contact.setName(name);
    }
    
    public String getSurname(){
        return this.contact.getSurname();
    }
    public void setSurname(String surname){
        this.contact.setSurname(surname); 
    }

    public int getContactId(){
        return getContact().getContactId();
    }
    
    public String getEmail(){
        return this.contact.getEmail();
    }
    public void setEmail(String email){
        this.contact.setEmail(email);
    }
    
    public String getWhatsapp(){
        return this.contact.getWhatsapp();
    }
    public void setWhatsapp(String whatsapp){
        this.contact.setWhatsapp(whatsapp);
    }
    
    public String getAddress(){
        return this.contact.getAddress();
    }
    public void setAddress(String address){
        this.contact.setAddress(address);
    }
    
    public String getFiscalCode(){
        return this.contact.getFiscalCode();
    }
    public void setFiscalCode(String fiscalCode){
        this.contact.setFiscalCode(fiscalCode);
    } 
    
    public Contact getContact(){
        return this.contact;
    }
    public void setContact(Contact contact){
        this.contact = contact;
    } 
    
    public boolean getPermanent(){
        return this.permanent;
    }
    public void setPermanent(boolean permanent){
        this.permanent = permanent;
    }
    
    public boolean isOnHoliday(){
        LocalDate localDate = LocalDate.now();
        for(DateSpan ds: onHoliday)
            if(ds.contains(localDate))
                return true;
        return false;
    } 
    
    public void addOnHoliday(DateSpan ds){
        onHoliday.add(ds);
        Collections.sort(onHoliday);
    }
    public ArrayList<DateSpan> getHolidays(){
        return onHoliday;
    }
    public void removeOnHoliday(DateSpan ds){
        onHoliday.remove(ds);
    }
    
    public int getNumberOfDaysOff(){
        return this.numberOfDaysOff;
    }
    public void setNumberOfDaysOff(int numberOfDaysOff){
        this.numberOfDaysOff = numberOfDaysOff;
    }

    public String toString(){
        return contact + " | kitchen: " + kitchen +  " | permanent: " + permanent  + " | number of days off: " + numberOfDaysOff + " | Holiday: " + onHoliday;
    }


    /** DATABASE OPERATIONS */

     public static StaffMember load(int id){
        String query = "SELECT `contact_id`, `permanent_staff`, `numbers_of_holidays_days`, `kitchen_staff` FROM StaffMembers WHERE `contact_id` = ?";
        StaffMember ret = new StaffMember();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                ret.contact = Contact.load(rs.getInt("contact_id"));
                ret.permanent = rs.getBoolean("permanent_staff");
                ret.numberOfDaysOff = rs.getInt("numbers_of_holidays_days");
                ret.onHoliday = StaffMember.loadOnHoliday(rs.getInt("contact_id"));
                ret.kitchen = rs.getBoolean("kitchen_staff");
            }
        }, id);

        return ret;
    }

    public static ArrayList<DateSpan> loadOnHoliday(int id){
        String query = "SELECT `staff_member_id`, `from_date`, `to_date` FROM HolidaysOfStaffMember WHERE `staff_member_id` = ?";
        ArrayList<DateSpan> ret = new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(new DateSpan(LocalDate.parse(rs.getString("from_date")),LocalDate.parse(rs.getString("to_date"))));
                }while(rs.next());
            }
        }, id);
        Collections.sort(ret);

        return ret;
    }

    public void save() {
        String query = "INSERT INTO StaffMembers (contact_id,permanent_staff,numbers_of_holidays_days,kitchen_staff) VALUES(?,?,?,?)";

        PersistenceManager.executeUpdate(query, contact.getId(),permanent,numberOfDaysOff,kitchen);
    }



    public static void updateChangedName(StaffMember staffMember, String name){
        String query = "UPDATE Contacts SET `name` = ? WHERE `user_id` = ?";
        PersistenceManager.executeUpdate(query, name, staffMember.getContactId());
    }
    public static void updateChangedSurname(StaffMember staffMember, String surname){
        String query = "UPDATE Contacts SET `surname` = ? WHERE `user_id` = ?";
        PersistenceManager.executeUpdate(query, surname, staffMember.getContactId());
    }
    public static void updateChangedEmail(StaffMember staffMember, String email){
        String query = "UPDATE Contacts SET `email` = ? WHERE `user_id` = ?";
        PersistenceManager.executeUpdate(query, email, staffMember.getContactId());
    }
    public static void updateChangedWhatsapp(StaffMember staffMember, String whatsapp){
        String query = "UPDATE Contacts SET `whatsapp` = ? WHERE `user_id` = ?";
        PersistenceManager.executeUpdate(query, whatsapp, staffMember.getContactId());
    }
    public static void updateChangedAddress(StaffMember staffMember, String address){
        String query = "UPDATE Contacts SET `address` = ? WHERE `user_id` = ?";
        PersistenceManager.executeUpdate(query, address, staffMember.getContactId());
    }
    public static void updateChangedFiscalCode(StaffMember staffMember, String fiscalCode){
        String query = "UPDATE Contacts SET `fiscal_code` = ? WHERE `user_id` = ?";
        PersistenceManager.executeUpdate(query, fiscalCode, staffMember.getContactId());
    }
    public static void updateChangedContact(StaffMember staffMember, Contact contact){
        StaffMember.updateChangedName(staffMember, contact.getName());
        StaffMember.updateChangedSurname(staffMember,contact.getSurname());
        StaffMember.updateChangedEmail(staffMember, contact.getEmail());
        StaffMember.updateChangedWhatsapp(staffMember, contact.getWhatsapp());
        StaffMember.updateChangedAddress(staffMember, contact.getAddress());
        StaffMember.updateChangedFiscalCode(staffMember, contact.getFiscalCode());
    }
    public static void updateChangedPermanent(StaffMember staffMember, boolean permanent){
        String query = "UPDATE StaffMembers SET `permanent_staff` = ? WHERE `contact_id` = ?";
        PersistenceManager.executeUpdate(query, permanent, staffMember.getContactId());
    }
    public static void updateAddOnHoliday(StaffMember staffMember, DateSpan s){
        String query = "INSERT INTO HolidaysOfStaffMember(from_date, to_date, staff_member_id) VALUES (?, ?, ?)";
        PersistenceManager.executeUpdate(query, s.getFrom().toString(), s.getTo().toString(), staffMember.getContactId());
    }
    public static void updateRemoveOldOnHoliday(StaffMember staffMember, DateSpan s){
        String query = "DELETE FROM HolidaysOfStaffMember WHERE from_date = ? AND to_date = ? AND staff_member_id = ?";
        PersistenceManager.executeUpdate(query, s.getFrom(), s.getTo() , staffMember.getContactId());

    }
    public static void updateChangedNumberOfDaysOff(StaffMember staffMember, int daysOff){
        String query = "UPDATE StaffMembers SET `numbers_of_holidays_days` = ? WHERE `contact_id` = ?";
        PersistenceManager.executeUpdate(query, daysOff, staffMember.getContactId());
    }
}