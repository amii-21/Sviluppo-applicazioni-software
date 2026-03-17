package catering.businesslogic.holidayManagement;

import catering.businesslogic.staffManagement.*;
import java.time.*;

public class HolidayRequest implements Comparable<HolidayRequest>{
    
    private DateSpan date;
    private StaffMember staffMember;
    
    public HolidayRequest(DateSpan date, StaffMember staffMember){
        this.date = date;
        this.staffMember = staffMember;
    }

    public int compareTo(HolidayRequest comp){
        return this.date.compareTo(comp.date);
    }
    
    public DateSpan getSpan(){
        return date;
    }
    
    public LocalDate getStartDate(){
        return date.getFrom();
    }
    public LocalDate getEndDate(){
        return date.getTo();
    }
    
    public StaffMember getStaffMember(){
        return staffMember;
    }
    	
    public void setStartDate(LocalDate newStartDate){
        date.setFrom(newStartDate);
    }
    public void setEndDate(LocalDate newEndDate){
        date.setTo(newEndDate);
    } 

    public void setStaffMember(StaffMember newStaffMember){
        staffMember = newStaffMember;
    }

    public String toString(){
        return date.toString() + " ID Dipendente: " + staffMember.getContactId();
    }

}