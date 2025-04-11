package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static  final ArrayList<Entity> entities = new ArrayList<>();
    private static int idNumber = 1;
    private static final Map<Integer, Validator> validators = new HashMap<>();


    private Database(){}
    public static void registerValidator(int entityCode, Validator validator) {
        if (validator == null) {
            throw new IllegalArgumentException("Validator cannot be null");
        }
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Validator for entity code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }


    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        }
        if (e instanceof Trackable) {
            Trackable trackable = (Trackable) e;
            Date now = new Date();
            trackable.setCreationDate(now);
            trackable.setLastModificationDate(now);
        }
        Entity copy = e.copy();
        copy.id = idNumber++;
        e.id = copy.id;
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
    public static void update(Entity e) throws EntityNotFoundException , InvalidEntityException{
        validateEntity(e);
        for (int i = 0 ; i < entities.size() ; i++){
            if (entities.get(i).id == e.id){

                if (e instanceof Trackable) {
                    ((Trackable) e).setLastModificationDate(new Date());
                }
                entities.set(i, e.copy());
                return;

            }
        }
        throw new EntityNotFoundException(e.id);
    }
    private static void validateEntity(Entity entity) throws InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());
        if (validator != null) {
            validator.validate(entity);
        }
    }


}
