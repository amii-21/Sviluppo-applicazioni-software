package catering.persistence;

import catering.businesslogic.staffManagement.*;

public class StaffMemberPersistence implements StaffMemberReceiver{
    public void updateChangedName(StaffMember staffMember, String name){
        StaffMember.updateChangedName(staffMember, name);
    }
    public void updateChangedSurname(StaffMember staffMember, String surname){
        StaffMember.updateChangedSurname(staffMember, surname);
    }
    public void updateChangedEmail(StaffMember staffMember, String email){
        StaffMember.updateChangedEmail(staffMember, email);
    }
    public void updateChangedWhatsapp(StaffMember staffMember, String whatsapp){
        StaffMember.updateChangedWhatsapp(staffMember, whatsapp);
    }
    public void updateChangedAddress(StaffMember staffMember, String address){
        StaffMember.updateChangedAddress(staffMember, address);
    }
    public void updateChangedFiscalCode(StaffMember staffMember, String fiscalCode){
        StaffMember.updateChangedFiscalCode(staffMember, fiscalCode);
    }
    public void updateChangedContact(StaffMember staffMember, Contact contact){
        StaffMember.updateChangedContact(staffMember, contact);
    }
    public void updateChangedPermanent(StaffMember staffMember, boolean b){
        StaffMember.updateChangedPermanent(staffMember, b);
    }
    public void updateAddOnHoliday(StaffMember staffMember, DateSpan s){
        StaffMember.updateAddOnHoliday(staffMember, s);
    }
    public void updateRemoveOldOnHoliday(StaffMember staffMember, DateSpan s){
        StaffMember.updateRemoveOldOnHoliday(staffMember, s);
    }
    public void updateChangedNumberOfDaysOff(StaffMember staffMember, int daysOff){
        StaffMember.updateChangedNumberOfDaysOff(staffMember, daysOff);
    }
}