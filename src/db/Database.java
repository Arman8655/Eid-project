package db;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static  final ArrayList<Entity> entities = new ArrayList<>();
    private static int idNumber = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();


    private Database(){}

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Validator for entity code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }
    private static void validateEntity(Entity entity) throws InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());
        if (validator != null) {
            validator.validate(entity);
        }
    }


    public static void add(Entity e) throws InvalidEntityException {
        validateEntity(e);
        Entity copy = e.copy();
        copy.id = idNumber++;
        entities.add(copy);
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities){
            if (entity.id == id){
                return entity.copy();
            }
        }
        throw new EntityNotFoundException(id);

    }
    public static void delete(int id) throws EntityNotFoundException{
        for (int i = 0 ; i < entities.size() ; i++){
            if (entities.get(i).id == id) {
                entities.remove(i);
                return;
            }
        }
        throw new EntityNotFoundException(id);

    }
    public static void update(Entity e) throws EntityNotFoundException, InvalidEntityException {
        validateEntity(e);
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.copy());
                return;
            }
        }
        throw new EntityNotFoundException(e.id);
    }
}



