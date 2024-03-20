package learn.unexplained.data;

import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EncounterRepositoryDouble implements EncounterRepository {
    @Override
    public List<Encounter> findAll() throws DataAccessException {
        return List.of(new Encounter(2, EncounterType.CREATURE, "1/1/2015", "test description", 1));
    }

    @Override
    public Encounter add(Encounter encounter) throws DataAccessException {
        return encounter;
    }

    @Override
    public boolean deleteById(int encounterId) throws DataAccessException {
        return encounterId != 12;
    }
    @Override
    public List<Encounter> findByType(EncounterType type) throws DataAccessException{
            List<Encounter> encounters = List.of(new Encounter(2, EncounterType.CREATURE, "1/1/2015", "test description", 1));
            ArrayList<Encounter> result =new ArrayList<>();
            for (Encounter e : encounters){
                if(type==e.getType()){
                    result.add(e);
                }
            }
            return result;
    }
    @Override
    public boolean update(Encounter encounter) throws DataAccessException{
        return encounter.getEncounterId()>0;
    }

}
