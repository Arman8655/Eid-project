package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public class Database {
    private static  final ArrayList<Entity> entities = new ArrayList<>();
    private static int idNumber = 1;


    private Database(){}


    public static void add(Entity e){
        e.id = idNumber ++;
        entities.add(e);
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities){
            if (entity.id == id){
                return entity;
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
    public static void update(Entity e) throws EntityNotFoundException{
        for (int i = 0 ; i < entities.size() ; i++){
            if (entities.get(i).id == e.id){
                entities.set(i, e);
                return;

            }
        }
        throw new EntityNotFoundException(e.id);
    }


}
