package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Key implements ImageTile {
    private Position position;
    private final String keyName;

    public Key(Position position, String keyName) {
        this.position = position;
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
    public int getPoints() {
        return 4;
    }

    @Override public Position getPosition() {
        return position;
    }
    @Override public void setPosition(Position position) {
        this.position = position;
    }
    @Override public String getName() {
        return "Key";
    }
}
