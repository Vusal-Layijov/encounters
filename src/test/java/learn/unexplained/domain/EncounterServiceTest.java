package learn.unexplained.domain;

import learn.unexplained.data.DataAccessException;
import learn.unexplained.data.EncounterRepositoryDouble;
import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EncounterServiceTest {

    EncounterService service = new EncounterService(new EncounterRepositoryDouble());

    @Test
    void shouldNotAddNull() throws DataAccessException {
        EncounterResult expected = makeResult("encounter cannot be null");
        EncounterResult actual = service.add(null);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyWhen() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, " ", "test desc", 1);
        EncounterResult expected = makeResult("when is required");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }


    @Test
    void shouldNotAddEmptyDescription() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", "  ", 1);
        EncounterResult expected = makeResult("description is required");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullDescription() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", null, 1);
        EncounterResult expected = makeResult("description is required");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddZeroOccurrences() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", "test description", 0);
        EncounterResult expected = makeResult("occurrences must be greater than 0");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddDuplicate() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "1/1/2015", "test description", 1);
        EncounterResult expected = makeResult("duplicate encounter is not allowed");
        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "2/2/2019", "test description", 1);
        EncounterResult expected = new EncounterResult();
        expected.setPayload(encounter);

        EncounterResult actual = service.add(encounter);
        assertEquals(expected, actual);
    }
    @Test
    void shouldUpdate() throws DataAccessException {
        List<Encounter> all= service.findAll();
        Encounter toUpdate= all.get(0);
        toUpdate.setDescription("This is for testing");
        EncounterResult actual= service.update(toUpdate);
        assertTrue(actual.isSuccess());
        assertEquals("This is for testing",all.get(0).getDescription());
    }
    @Test
    void shouldNotUpdateEmptyEncounter() throws DataAccessException {
        EncounterResult expected = makeResult("encounter cannot be null");
        EncounterResult actual = service.update(null);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateEmptyWhen() throws DataAccessException {
        List<Encounter> all= service.findAll();
        Encounter toUpdate= all.get(0);
        toUpdate.setWhen(" ");
        EncounterResult expected = makeResult("when is required");
        EncounterResult actual = service.update(toUpdate);
        assertEquals(expected.getMessages().get(0), actual.getMessages().get(0));
        assertEquals("when is required", actual.getMessages().get(0));
    }
    @Test
    void shouldNotUpdateEmptyDescription() throws DataAccessException {
        List<Encounter> all= service.findAll();
        Encounter toUpdate= all.get(0);
        toUpdate.setDescription(" ");
        EncounterResult actual = service.update(toUpdate);
        assertEquals("description is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateEmptyOccurrences() throws DataAccessException {
        List<Encounter> all= service.findAll();
        Encounter toUpdate= all.get(0);
        toUpdate.setOccurrences(0);
        EncounterResult actual = service.update(toUpdate);
        assertEquals("occurrences must be greater than 0", actual.getMessages().get(0));
    }


    @Test
    void shouldNotUpdateDuplicate() throws DataAccessException {
        Encounter encounter = new Encounter(0, EncounterType.CREATURE, "1/1/2015", "test description", 1);
        EncounterResult expected = makeResult("duplicate encounter is not allowed");
        EncounterResult actual = service.update(encounter);
        assertEquals(expected, actual);
    }
    @Test
    void shouldDeleteValidId() throws DataAccessException {
        EncounterResult actual = service.deleteById(1);
        assertTrue(actual.isSuccess());
    }
    @Test
    void shouldNotDeleteNonValidId() throws DataAccessException {
        EncounterResult actual = service.deleteById(12);
        assertFalse(actual.isSuccess());
    }
    @Test
    void shouldFindAll() throws DataAccessException {
        List<Encounter> all= service.findAll();
        assertEquals(1,all.size());
    }
    @Test
    void shouldFindByType() throws DataAccessException {
        List<Encounter> all= service.findByType(EncounterType.CREATURE);
        assertEquals(1,all.size());
    }



    private EncounterResult makeResult(String message) {
        EncounterResult result = new EncounterResult();
        result.addErrorMessage(message);
        return result;
    }
}