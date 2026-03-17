package catering.persistence;
import catering.businesslogic.availabilityManagement.*;
import catering.businesslogic.staffManagement.*;

public class AvailabilityTablePersistence implements AvailabilityTableReceiver{
    public void updateRevokeAvailability(AvailabilityTable availabilityTable, Availability a){
        AvailabilityTable.updateRevokeAvailability(availabilityTable, a);
    }
    public void updateAddAvailability(AvailabilityTable availabilityTable, Availability a){
        AvailabilityTable.updateAddAvailability(availabilityTable, a);
    }
    public void updateRevokeAvailabilityForHoliday(AvailabilityTable availabilityTable, StaffMember sm){
        AvailabilityTable.updateRevokeAvailabilityForHoliday(availabilityTable, sm);
    }

    @Override
    public boolean equals(Object o){
        return o instanceof AvailabilityTablePersistence;
    }
}