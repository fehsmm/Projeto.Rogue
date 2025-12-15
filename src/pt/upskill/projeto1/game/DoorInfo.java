package pt.upskill.projeto1.game;

public class DoorInfo {
    private int id;
    private char type;
    private String nextRoom;
    private int nextDoorId;
    private String key;

    public DoorInfo(int id, char type, String nextRoom, int nextDoorId, String key) {
        this.id = id;
        this.type = type;
        this.nextRoom = nextRoom;
        this.nextDoorId = nextDoorId;
        this.key = key;
    }

    public int getId() {
        return id;
    }
    public char getType(){
        return type;
    }
    public String getNextRoom() {
        return nextRoom;
    }
    public int getNextDoorId() {
        return nextDoorId;
    }
    public String getKey() {
        return key;
    }
}
