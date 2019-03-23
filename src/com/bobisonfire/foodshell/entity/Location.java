package com.bobisonfire.foodshell.entity;

import com.bobisonfire.foodshell.FileIOHelper;
import com.bobisonfire.foodshell.exc.LocationNotFoundException;
import com.bobisonfire.foodshell.transformer.ObjectTransformer;

import java.util.TreeMap;

/**
 * Класс, реализующий локации - места расположения персонажей в <i>FoodShell</i>.<br>
 * Все локации хранятся в CSV-файле (путь к которому указывает сам пользователь), равно как
 * и в структуре TreeMap внутри локации, доступ к которой осуществляется методами
 * getLocationByName, getMap, setMap и update.
 * Локация World - основная локация, в нее помещаются все персонажи при отстутствии других локаций
 * и ее невозможно удалить.
 */
public class Location implements CSVSerializable {
    public static final String CSV_HEAD = "name,x,y,z";
    public static String PATH = "location.csv";

    private static TreeMap<String, Location> LocationMap = new TreeMap<>();

    /**
     * Возвращает локацию по ее уникальному идентификатору (ищет ее в коллекции)
     * или оповещает, что такой локации не существует.
     * @param name Название локации (уникальный ключ).
     */
    public static Location getLocationByName(String name) {
        if (!LocationMap.containsKey(name.intern()) && !name.intern().equals(""))
            throw new LocationNotFoundException(name);
        return LocationMap.get(name);
    }

    public static void setMap(TreeMap<String, Location> map) {
        LocationMap = map;
    }

    public static TreeMap<String, Location> getMap() {
        return LocationMap;
    }

    /**
     * Перезаписывает CSV-файл с локациями текущей коллекцией.<br>
     * Должен вызываться при каждом изменении локаций для синхронизации файла.<br>
     * При удалении существующей локации также следует вызвать Human.update(),
     * чтобы персонажи из удаленных локаций переместились в основную локацию.
     */
    public static void update() {
        mFileIOHelper.writeCSVMapIntoFile(LocationMap, false);
    }

    public String toCSV() {
        return String.format("%s,%.3f,%.3f,%.3f",
                name, coords.getX(), coords.getY(), coords.getZ());
    }

    public String getPath() {
        return PATH;
    }

    public String getCSVHead() {
        return CSV_HEAD;
    }



    private String name;
    private Coordinate coords;
    private static FileIOHelper mFileIOHelper = new FileIOHelper();

    public Location(ObjectTransformer objectTransformer, boolean serialize) {
        this.name = objectTransformer.getString("name");
        this.coords = new Coordinate(
                objectTransformer.getDouble("x"),
                objectTransformer.getDouble("y"),
                objectTransformer.getDouble("z")
        );

        LocationMap.put(name, this);
        if (serialize)
            update();

    }

    public Location() {
        name = "World";
        coords = new Coordinate(0, 0, 0);

        LocationMap.put(name, this);
        update();
    }



    public String getName() {
        return name;
    }

    public Coordinate getCoords() {
        return coords;
    }

}
