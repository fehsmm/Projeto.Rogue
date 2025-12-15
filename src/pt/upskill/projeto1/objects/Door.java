package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.DoorInfo;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Door implements ImageTile {
    private Position position;
    private final int id;
    private final char type;
    private final String nextRoom;
    private final int nextDoorId;
    private final String requiredKey;
    private boolean open;

    public Door(Position position, DoorInfo info) {
        this.position = position;
        this.id = info.getId();
        this.type = info.getType();
        this.nextRoom = info.getNextRoom();
        this.nextDoorId = info.getNextDoorId();
        this.requiredKey = info.getKey();

        if (info.getType() == 'E') {     //DoorWay nao precisa de chave e porta fechada precisa
            this.open = true;
        } else {
            this.open = (info.getKey() == null);
        }
    }
    public int getId() {
        return id;
    }
    public String getNextRoom() {
        return nextRoom;
    }
    public int getNextDoorId() {
        return nextDoorId;
    }
    public String getRequiredKey() {
        return requiredKey;
    }
    public boolean isOpen() {
        return open;
    }
    public void open() {
        this.open = true;
    }
    @Override
    public Position getPosition() {
        return position;
    }
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
    @Override
    public String getName() {
        if (type == 'E') return "DoorWay";
        return open ? "DoorOpen" : "DoorClosed";
    }
}