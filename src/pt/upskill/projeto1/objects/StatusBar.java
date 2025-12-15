package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class StatusBar implements ImageTile {
    private final Position position;
    private final String name;

    public StatusBar(int x, String name) {
        this.position = new Position(x, 0);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public Position getPosition() {
        return position;
    }
    @Override
    public void setPosition(Position position) { }
}