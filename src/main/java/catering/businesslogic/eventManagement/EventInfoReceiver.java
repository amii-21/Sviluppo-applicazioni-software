package catering.businesslogic.eventManagement;

public interface EventInfoReceiver{
    public void updateNoteAdded(EventInfo eventInfo, Notes n);
    public void updateNoteModified(EventInfo eventInfo, int index ,Notes n);
    public void updateNoteDeleted(EventInfo eventInfo, int index);
    public void updateNumGuestsModified(EventInfo eventInfo, int i);
    public void updateLocationAdded(EventInfo eventInfo, String s);
    public void updateLocationModified(EventInfo eventInfo, int index, String s);
    public void updateLocationDeleted(EventInfo eventInfo, int index);
    public void updateInfoAdded(EventInfo eventInfo, String s);
    public void updateInfoModified(EventInfo eventInfo, int index,String s);
    public void updateInfoDeleted(EventInfo eventInfo, int index);
    public void updateTypeOfServiceAdded(EventInfo eventInfo, String s);
    public void updateTypeOfServiceModified(EventInfo eventInfo, int index, String s);
    public void updateTypeOfServiceDeleted(EventInfo eventInfo, int index);
}