package catering.businesslogic.staffManagement;

public interface StaffMemberReceiver{
   public void updateChangedName(StaffMember staffMember, String name);
   public void updateChangedSurname(StaffMember staffMember, String surname);
   public void updateChangedEmail(StaffMember staffMember, String email);
   public void updateChangedWhatsapp(StaffMember staffMember, String whatsapp);
   public void updateChangedAddress(StaffMember staffMember, String address);
   public void updateChangedFiscalCode(StaffMember staffMember, String FiscalCode);
   public void updateChangedContact(StaffMember staffMember, Contact contact);
   public void updateChangedPermanent(StaffMember staffMember, boolean b);
   public void updateAddOnHoliday(StaffMember staffMember, DateSpan s);
   public void updateRemoveOldOnHoliday(StaffMember staffMember, DateSpan s);
   public void updateChangedNumberOfDaysOff(StaffMember staffMember, int daysOff);
}