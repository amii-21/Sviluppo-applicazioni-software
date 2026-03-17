package catering.businesslogic.kitchen;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.availabilityManagement.Availability;
import catering.businesslogic.availabilityManagement.AvailabilityTableManager;
import catering.businesslogic.event.Event;
import catering.businesslogic.eventManagement.EventInfo;
import catering.businesslogic.eventManagement.EventInfoManager;
import catering.businesslogic.eventManagement.Notes;
import catering.businesslogic.eventManagement.Role;
import catering.businesslogic.eventManagement.StaffEventInfoManager;
import catering.businesslogic.holidayManagement.HolidayRequest;
import catering.businesslogic.holidayManagement.HolidayTable;
import catering.businesslogic.holidayManagement.HolidayTableManager;
import catering.businesslogic.staffManagement.Contact;
import catering.businesslogic.staffManagement.DateSpan;
import catering.businesslogic.staffManagement.StaffMember;
import catering.businesslogic.staffManagement.StaffMemberManager;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultPrinter;
import catering.util.Pair;

class SetupSummarySheet {
    private static boolean initialized = false;
    private static CatERing cr = CatERing.getInstance();

    public static void eseguiSetupSummarySheet() {
        if (!initialized) {

            // id 1
            User luciano = new User("Luciano", new Contact("Luciano", "Spalletti",  "luciano.spalletti@email.it", "3511111111", "Via Spalletti 1, Torino", "SPLLCN80A01H501U"));
            luciano.addRole(User.enumRole.PROPRIETARIO);
            luciano.save();

            // id 2
            User mattia = new User("Mattia", new Contact("Mattia", "Perin",  "mattia.perin@email.it", "3511111112", "Via Perin 3, Torino", "PRNMAT90B45F205K"));
            mattia.addRole(User.enumRole.ORGANIZZATORE);
            mattia.save();

            // id 3
            User daniele = new User("Daniele", new Contact("daniele", "Rugani", "daniele.rugani@email.it", "3511111113", "Via Rugani 2, Torino", "RGNLNL85C12D612T"));
            daniele.addRole(User.enumRole.ORGANIZZATORE);
            daniele.save();

            // id 4
            User andrea = new User("Andrea", new Contact("andrea", "Cambiaso", "andrea.cambiaso@email.it", "351111114", "Corso Cambiaso 27, Torino", "CMBNDR92D61F839Y"));
            andrea.addRole(User.enumRole.CHEF);
            andrea.save();

            // id 5
            User carlo = new User("Carlo", new Contact("Carlo", "Pinsoglio", "carlo.pinsoglio@email.it", "3511111115", "Via Pinsoglio 90, Torino", "PNSCLL87A23C351W"));
            carlo.addRole(User.enumRole.CUOCO);
            carlo.save();

            // id 6
            User kenan = new User("Kenan", new Contact("Kenan", "Yildiz",  "kenan.yildiz@email.it", "3511111116", "Via Yildiz 10, Torino", "YLDKNN91T22L219X"));
            kenan.addRole(User.enumRole.CUOCO);
            kenan.save();

            // id 7
            User michele = new User("Michele", new Contact("Michele", "DiGregorio", "michele.digregorio@email.it", "351111117", "Via DiGregorio 15, Torino", "DIGMCL88G12L219Z"));
            michele.addRole(User.enumRole.CUOCO);
            michele.save();

            // id 8
            User manuel = new User("Manuel", new Contact("Manuel", "Locatelli", "manuel.locatelli@email.it", "3511111118", "Via Locatelli 5, Torino", "LOCMLN94H21M610E"));
            manuel.addRole(User.enumRole.SERVIZIO);
            manuel.save();

            // id 9
            User federico = new User("Federico", new Contact("Federico", "Gatti", "federico.gatti@email.it", "3511111119", "Via Gatti 87, Torino", "GTTFRD91I14E512B"));
            federico.addRole(User.enumRole.SERVIZIO);
            federico.save();

            // id 10
            User fabio = new User("Fabio", new Contact("Fabio", "Miretti", "fabio.miretti@email.it", "3511111120", "Via Miretti 12, Torino", "MRIFBA95L30H123G"));
            fabio.addRole(User.enumRole.SERVIZIO);
            fabio.save();
           
            StaffMember lucianoStaff, mattiaStaff, danieleStaff, andreaStaff, carloStaff,
            kenanStaff, micheleStaff, manuelStaff, federicoStaff, fabioStaff;

            try{
                lucianoStaff = new StaffMember(true, 12, luciano.getContact(), false);
                lucianoStaff.save();

                mattiaStaff = new StaffMember(true, 25, mattia.getContact(), false);
                mattiaStaff.save();

                danieleStaff = new StaffMember(true, 7, daniele.getContact(), false);
                danieleStaff.save();

                andreaStaff = new StaffMember(true, 18, andrea.getContact(), true);
                andreaStaff.save();

                carloStaff = new StaffMember(true, 30, carlo.getContact(), true);
                carloStaff.save();

                kenanStaff = new StaffMember(false, 9, kenan.getContact(), true);
                kenanStaff.save();

                micheleStaff = new StaffMember(true, 15, michele.getContact(), true);
                micheleStaff.save();

                manuelStaff = new StaffMember(false, 22, manuel.getContact(), false);
                manuelStaff.save();

                federicoStaff = new StaffMember(true, 14, federico.getContact(), false);
                federicoStaff.save();

                fabioStaff = new StaffMember(false, 10, fabio.getContact(), false);
                fabioStaff.save();

                HolidayTableManager htm = cr.getHolidayTableManager();
                htm.addHolidayRequest(new HolidayRequest(new DateSpan(LocalDate.now().plusMonths(4).plusDays(3), LocalDate.now().plusMonths(4).plusDays(7)), carloStaff));
                htm.addHolidayRequest(new HolidayRequest(new DateSpan(LocalDate.now().plusMonths(4).plusDays(1), LocalDate.now().plusMonths(4).plusDays(9)), federicoStaff));
                htm.addHolidayRequest(new HolidayRequest(new DateSpan(LocalDate.now().plusMonths(4).plusDays(5), LocalDate.now().plusMonths(4).plusDays(15)), micheleStaff));

                AvailabilityTableManager atm = cr.getAvailabilityTableManager();
                for(int i=0;i<10;i++){
                    atm.addAvailability(new Availability(LocalDate.now().plusMonths(4).plusDays(i),federicoStaff));
                }
                for(int i=0;i<15;i++){
                    atm.addAvailability(new Availability(LocalDate.now().plusMonths(4).plusDays(i),carloStaff));
                }
                for(int i=0;i<12;i++){
                    atm.addAvailability(new Availability(LocalDate.now().plusMonths(4).plusDays(i),micheleStaff));
                }

                for(int i=0;i<10;i++){
                    atm.addAvailability(new Availability(LocalDate.now().plusMonths(4).plusDays(i),fabioStaff));
                }
                for(int i=0;i<10;i++){
                    atm.addAvailability(new Availability(LocalDate.of(2025, 3, 10).plusDays(i),fabioStaff));
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            cr.getUserManager().setCurrentUser(luciano);
            EventInfoManager eim = cr.getEventInfoManager();

            //Evento 1
            LocalDate ld1 = LocalDate.of(2025, 3, 10);
            Date start1 = Date.valueOf(ld1);
            Date end1 = Date.valueOf(ld1);
            Event e1 = cr.getEventManager().createEvent("Cena Aziendale Juventus", start1, end1, andrea);
            eim.setCurrentEventInfo(EventInfo.load(e1.getId()));
            eim.addLocations("Corso Gaetano Scirea 50, Torino (Allianz Stadium)");
            eim.addLocations("Via Druento 175, Torino (JTC Continassa)");
            eim.addTypeOfService("Gala Dinner Juventus");
            eim.addTypeOfService("Hospitality VIP");
            eim.addTypeOfService("Open Bar & Finger Food");
            eim.addInfo("L'evento istituzionale Juventus FC è un'occasione esclusiva per partner e stakeholder.");
            eim.addInfo("Il menu stellato celebra l'eccellenza italiana con materie prime del territorio.");
            eim.addInfo("Servizio Hospitality d'élite con focus sulla rapidità nei momenti pre-partita.");
            eim.addNotes(new Notes(
                "Standard eccellenti, fluidità servizio VIP", 
                "Evento di alto profilo gestito con successo. Il prestigio del brand è stato rispettato. Da monitorare la sincronia del servizio durante i discorsi ufficiali."
            ));
            eim.addNotes(new Notes(
                "Fabio: leadership e gestione hospitality impeccabile", 
                "Cameriere senior estremamente professionale, ha gestito i tavoli della dirigenza con discrezione e precisione. Unico punto di attenzione: la velocità nel coordinamento del beverage durante il picco di metà serata."
            ));

            // Evento 2 
            LocalDate ld2Start = LocalDate.of(2024, 9, 16);
            LocalDate ld2End = LocalDate.of(2024, 9, 17);
            Date start2 = Date.valueOf(ld2Start);
            Date end2 = Date.valueOf(ld2End);
            Event e2 = cr.getEventManager().createEvent("Premiazione Trofeo Juventus", start2, end2, andrea);
            eim.setCurrentEventInfo(EventInfo.load(e2.getId()));
            eim.addLocations("Piazza San Carlo, Torino (Fan Zone)");
            eim.addTypeOfService("Premiazione Ufficiale");
            eim.addTypeOfService("Catering Hospitality Stadium");
            eim.addTypeOfService("Servizio Executive");
            eim.addInfo("La celebrazione del trofeo Juventus coinvolgerà squadra, staff e partner istituzionali.");
            eim.addInfo("Piazza San Carlo si conferma il cuore pulsante del tifo bianconero per gli eventi di piazza.");
            eim.addInfo("Catering d'élite progettato per grandi numeri, mantenendo lo standard qualitativo della Club House.");
            
            // Evento 3 
            LocalDate ld3 = LocalDate.of(2025, 4, 22);
            Date start3 = Date.valueOf(ld3);
            Date end3 = Date.valueOf(ld3);
            Event e3 = cr.getEventManager().createEvent("Coffee Break Media Day", start3, end3, andrea);
            eim.setCurrentEventInfo(EventInfo.load(e3.getId()));
            eim.addLocations("Via Druento 153, Torino (Juventus Museum)");
            eim.addTypeOfService("Coffee Break Media Day");
            eim.addTypeOfService("Backstage Catering");
            eim.addTypeOfService("Quick Refreshment");

            eim.addInfo("Coffee break post-conferenza stampa per facilitare il networking tra testate giornalistiche e staff.");
            eim.addInfo("Rinfresco dinamico con snack energetici e servizio caffetteria espresso di alta qualità.");

            eim.addNotes(new Notes(
                "Team cucina: ottima gestione snack gourmet, coordinamento da velocizzare", 
                "Qualità delle proposte eccellente, molto apprezzate dalla stampa. Bisogna però snellire il flusso durante la pausa flash tra le sessioni."
            ));

            eim.addNotes(new Notes(
                "Criticità gestione area Hospitality: richiesto intervento immediato", 
                "Problemi di temperatura nelle bevande calde e ritardi nel rifornimento buffet. È necessario un briefing urgente per gestire i picchi di affluenza dei giornalisti."
            ));

            eim.addNotes(new Notes(
                "Fabio: pilastro del servizio, gestione impeccabile dei flussi VIP", 
                "Grinta, competenza e sangue freddo: Fabio ha gestito l'area stampa con estrema professionalità, ricevendo i complimenti diretti dal responsabile comunicazione per la precisione nel servizio."
            ));

            // Evento 4
            LocalDate ld4 = LocalDate.now().plusMonths(4);
            Date start4 = Date.valueOf(ld4);
            Date end4 = start4;
            Event e4 = cr.getEventManager().createEvent("Black Tie Charity Gala", start4, end4, andrea);
            eim.setCurrentEventInfo(EventInfo.load(e4.getId()));
            eim.addLocations("Piazza Castello, Torino (Palazzo Reale)");
            eim.addLocations("Via Accademia delle Scienze 6, Torino (Museo Egizio)");

            eim.addTypeOfService("Black Tie Charity Gala");
            eim.addTypeOfService("Elite Catering");
            eim.addTypeOfService("Silver Service al tavolo");

            eim.addInfo("Gala di beneficenza 'Juventus for Special' dedicato al supporto di progetti di inclusione sociale.");
            eim.addInfo("Cornice storica nel cuore di Torino, allestita con i colori istituzionali del club.");
            eim.addInfo("Esperienza culinaria d'eccellenza: menù stellato servito al tavolo per la prima squadra e gli ospiti d'onore.");
            
            // Evento 5 
            LocalDate ld5 = LocalDate.now().plusMonths(4).plusDays(10);
            Date start5 = Date.valueOf(ld5);
            Date end5 = start5;
            Event e5 = cr.getEventManager().createEvent("Jersey Reveal Event", start5, end5, andrea);
            eim.setCurrentEventInfo(EventInfo.load(e5.getId()));
            eim.addLocations("Corso Castelfidardo 22, Torino (OGR - Sala Fucine)");
            eim.addLocations("Via Druento 175, Torino (Juventus Store Flagship)");

            eim.addTypeOfService("Jersey Reveal Event");
            eim.addTypeOfService("Street Food Gourmet");
            eim.addTypeOfService("Mixology Bar");

            eim.addInfo("Lancio esclusivo della 'Special Edition Jersey': un ponte tra performance sportiva e lifestyle.");
            eim.addInfo("Location: OGR Torino, hub dell'innovazione, ideale per un reveal ad alto impatto tecnologico.");
            eim.addInfo("Esperienza dinamica: Urban cocktail party con dj-set e servizio finger food a tema 'Black & White'.");
                       
            StaffEventInfoManager seim = cr.getStaffEventInfoManager();

            fabioStaff = StaffMember.load(User.load("Fabio").getId());            
            seim.setCurrentStaffEventInfo(EventInfo.load(1).getStaffEventInfo());
            seim.addRole(new Role(1, "Cameriera", fabioStaff, false));
            seim.setCurrentStaffEventInfo(EventInfo.load(3).getStaffEventInfo());
            seim.addRole(new Role(3, "Barista", fabioStaff, false));
            seim.setCurrentStaffEventInfo(EventInfo.load(4).getStaffEventInfo());
            seim.addRole(new Role(4, "Maitre", fabioStaff, false));
            
            System.out.println();

            initialized = true;
        }
    }
}

@TestMethodOrder(OrderAnnotation.class)
public class SummarySheetTest {

    private CatERing cr = CatERing.getInstance();

    @BeforeAll
    static void init() {
        PersistenceManager.initializeDatabase("/Users/ami/Desktop/catering AmirMarciano/database/catering_init_sqlite.sql");
        SetupSummarySheet.eseguiSetupSummarySheet();
    }

    @Test
    @Order(1)
    public void testGetInfoDipendente(){
        System.out.println();
        System.out.println("---------------- Test Visualizzazione Richieste Ferie ------------------");

        System.out.println("Test 1: Il proprietario vuole visualizzare le richieste di ferie fatte dai dipendenti");
        System.out.println();

        HolidayTableManager htm = cr.getHolidayTableManager();
        HolidayTable ht = htm.getHolidayRequests();
        assertNotNull(ht);
        List<HolidayRequest> hrList = ht.getTable();
        assertNotNull(hrList);

        System.out.println("Elenco delle richieste di ferie:");
        for(int i=0;i<hrList.size();i++)
            System.out.println("\t"+(i+1)+". "+hrList.get(i));
        System.out.println();

        System.out.println("Dettagli delle richieste di Ferie 1 e 2:");
        System.out.println();

        Pair<HolidayRequest,List<Availability>> checkPair = htm.getHolidayRequestInfo(0);
        assertNotNull(checkPair);
        
        System.out.println("Giorni di ferie richieste:");
        System.out.println("\t"+checkPair.getKey().getSpan());
        System.out.println("Dipendente");
        System.out.println("\t"+checkPair.getKey().getStaffMember().getContact());
        System.out.println("Giorni di ferie rimanendi: "+checkPair.getKey().getStaffMember().getNumberOfDaysOff());
        System.out.println("Date Disponibili:");
        for(Availability av : checkPair.getValue()){
            System.out.println("\t"+av);
        }
        System.out.println();
        
        checkPair = htm.getHolidayRequestInfo(1);
        assertNotNull(checkPair);

        System.out.println("Giorni di ferie richieste:");
        System.out.println("\t"+checkPair.getKey().getSpan());
        System.out.println("Dipendente");
        System.out.println("\t"+checkPair.getKey().getStaffMember().getContact());
        System.out.println("Giorni di ferie rimanenti: "+checkPair.getKey().getStaffMember().getNumberOfDaysOff());
        System.out.println("Date Disponibili:");
        for(Availability av : checkPair.getValue()){
            System.out.println("\t"+av);
        }
        System.out.println();


    }
    
    @Test
    @Order(2)
    public void testAcceptHolidayRequest(){
        System.out.println();
        System.out.println("---------------- Test Accettazione Richieste Ferie ------------------");

        System.out.println("Test 2: Il proprietario vuole accettare una richiesta di ferie fatte dai dipendenti");
        System.out.println();

        User proprietario = User.load("Luciano");
        UserManager um = cr.getUserManager();
        um.setCurrentUser(proprietario);
        assertTrue(um.getCurrentUser().isOwner());

        HolidayTableManager htm = cr.getHolidayTableManager();
        StaffMemberManager smm = cr.getStaffMemberManager();

        System.out.println("Richiesta di ferie da Accettare: "+htm.getHolidayRequest(0));
        StaffMember federicoGatti = StaffMember.load(User.load("Federico").getId());
        assertNotNull(federicoGatti);
        smm.setCurrentStaffMember(federicoGatti);
        System.out.println("Dipendente:");
        System.out.println("\t"+smm.getCurrentStaffMember()+"\n");
        System.out.println("\n Quary delle disponibilità date dal dipendente,accettando la richiesta cambieranno le disponibilità  ");
        PersistenceManager.executeQuery("SELECT * FROM AvailabilityTable WHERE `staff_member_id`=9", new ResultPrinter("Disponibilità - DOPO"));
        System.out.println("Ferie attualmente accettate: "+smm.getCurrentStaffMember().getHolidays());
        System.out.println("Giorni di ferie rimanenti: "+smm.getCurrentStaffMember().getNumberOfDaysOff());
        System.out.println("\nAccettando la richiesta di ferie\n");
        try{
            htm.handleRequest(0, true);
        }
        catch(UseCaseLogicException e){
            e.printStackTrace();
        }
        System.out.println("Visuallizzando le disponibilità dopo aver accettato la richiesta di ferie");
        PersistenceManager.executeQuery("SELECT * FROM AvailabilityTable WHERE `staff_member_id`=9", new ResultPrinter("Disponibilità - DOPO"));
        System.out.println("Ferie attualmente accettate: "+smm.getCurrentStaffMember().getHolidays());
        System.out.println("Giorni di ferie rimanenti: "+smm.getCurrentStaffMember().getNumberOfDaysOff());

        System.out.println("\n\n");

        System.out.println("Richiesta di ferie da Accettare: "+htm.getHolidayRequest(0));
        StaffMember carloPinsoglio = StaffMember.load(User.load("Carlo").getId());
        assertNotNull(carloPinsoglio);
        smm.setCurrentStaffMember(carloPinsoglio);
        System.out.println("Dipendente che l'ha richiesta:");
        System.out.println("\t"+smm.getCurrentStaffMember());
        System.out.println("\nOQuary delle disponibilità date dal dipendente,accettando la richiesta cambieranno le disponibilità ");
        PersistenceManager.executeQuery("SELECT * FROM AvailabilityTable WHERE `staff_member_id`=5", new ResultPrinter("Disponibilità - PRIMA"));
        System.out.println("Ferie attualmente accettate: "+smm.getCurrentStaffMember().getHolidays());
        System.out.println("Giorni di ferie rimanenti: "+smm.getCurrentStaffMember().getNumberOfDaysOff());
        System.out.println("\nAccettando la richiesta di ferie\n");
        try{
            htm.handleRequest(0, true);
        }
        catch(UseCaseLogicException e){
            e.printStackTrace();
        }
        System.out.println("Visuallizzando le disponibilità dopo aver accettato la richiesta di ferie");
        PersistenceManager.executeQuery("SELECT * FROM AvailabilityTable WHERE `staff_member_id`=5", new ResultPrinter("Disponibilità - DOPO"));
        System.out.println("Ferie attualmente accettate: "+smm.getCurrentStaffMember().getHolidays());
        System.out.println("Giorni di ferie rimanenti: "+smm.getCurrentStaffMember().getNumberOfDaysOff());

        System.out.println();
    }
    
    @Test
    @Order(3)
    public void testGetStatistics(){
        System.out.println();
        System.out.println("---------------- Test Visualizzazione Statistiche Dipendente Occasionale------------------");

        System.out.println("Test 3: Il proprietario vuole visualizzare le statistiche di un dipendente occasionale per valutare di assumerlo permanentemente");
        System.out.println();
        
        User proprietario = User.load("Luciano");
        UserManager um = cr.getUserManager();
        um.setCurrentUser(proprietario);
        assertTrue(um.getCurrentUser().isOwner());

        StaffMemberManager smm = cr.getStaffMemberManager();

        StaffMember fabioMiretti = StaffMember.load(User.load("Fabio").getId());
        assertNotNull(fabioMiretti);
        Pair<List<Role>, List<Notes>> toCheck = smm.getStatistics(fabioMiretti);
        assertNotNull(toCheck);
        System.out.println("\nSTATISTICHE DI FABIO MIRETTI\n");
        System.out.println("Ruoli ricoperti:");
        for(Role r:toCheck.getKey()){
            System.out.println("\t"+r);
        }
        System.out.println();

        System.out.println("Note riguardo gli eventi a cui ha partecipato:\n");
        List<Notes> notesAndMore = toCheck.getValue();
        for(int i=0;i<toCheck.getValue().size()-1;i++){
            System.out.println(notesAndMore.get(i)+"\n");
        }
        
        System.out.println("Disponibilità totali: "+notesAndMore.get(notesAndMore.size()-1).getText()+"\n");

        
        
    }

}
