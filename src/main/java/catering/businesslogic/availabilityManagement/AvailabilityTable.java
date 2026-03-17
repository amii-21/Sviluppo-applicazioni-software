package catering.businesslogic.availabilityManagement;
import java.util.ArrayList;
import java.util.List;
import catering.businesslogic.staffManagement.*;
import java.util.Collections;
import java.sql.*;
import java.time.*;

import catering.persistence.*;



public class AvailabilityTable{
    
    private static AvailabilityTable singleInstance;
    private List<Availability> table = new ArrayList<>();

    private AvailabilityTable(){
    }

    public static AvailabilityTable getInstance() {
        if (singleInstance == null) {
            singleInstance = new AvailabilityTable();
        }
        return singleInstance;
    }

    
    public List<Availability> getAvailabilities(){
        return this.table;
    }
    public Availability getAvailability(int index){
        return this.table.get(index);
    }

    public void revokeAvailability(Availability a){
        this.table.remove(a);
    }
    public void addAvailability(Availability a){
        this.table.add(a);
        Collections.sort(this.table);
    }
    public void revokeAvailabilityForHoliday(StaffMember staffMember){
        List<DateSpan> holidays = staffMember.getHolidays();
        List<Availability> staffAvailability = getAvailabilityOfStaffMember(staffMember);
        for(DateSpan ds: holidays)
            for(Availability av: staffAvailability){
                if(ds.contains(av.getDate()))
                    table.remove(av);
            }
    }
    public List<Availability> getAvailabilityOfStaffMember(StaffMember staffMember){
        List<Availability> ret = new ArrayList<>();
        for(Availability av: table){
            if(av.contains(staffMember)){
                ret.add(av);
            }
        }
        return ret;
    }

    /** DATABASE OPERATIONS */

    public static ArrayList<Availability> load(int id){
        String query = "SELECT staff_member_id, date FROM AvailabilityTable";
        ArrayList<Availability> ret = new ArrayList<Availability>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(new Availability( LocalDate.parse(rs.getString("date")),StaffMember.load(rs.getInt("staff_member_id"))));
                }while(rs.next());
            }
        }, id);

        return ret;
    }


    public static void updateRevokeAvailability(AvailabilityTable availabilityTable, Availability a){
        String query = "DELETE FROM AvailabilityTable WHERE date = ? AND staff_member_id = ? ";
        PersistenceManager.executeUpdate(query, a.getDate().toString(), a.getStaffMember().getContactId());
    }
    public static void updateAddAvailability(AvailabilityTable availabilityTable, Availability a){
        String query = "INSERT INTO AvailabilityTable(date, staff_member_id) VALUES (?, ?)";
        PersistenceManager.executeUpdate(query, a.getDate(), a.getStaffMember().getContactId());
    }
    public static void updateRevokeAvailabilityForHoliday(AvailabilityTable availabilityTable, StaffMember sm){
        String query = "DELETE FROM AvailabilityTable WHERE date BETWEEN ? AND ? AND staff_member_id = ?";
        for(DateSpan ds : sm.getHolidays()){
           PersistenceManager.executeUpdate(query, ds.getFrom(), ds.getTo(), sm.getContactId());
        }
         
    }

}