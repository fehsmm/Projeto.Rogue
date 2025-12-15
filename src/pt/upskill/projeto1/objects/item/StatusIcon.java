package pt.upskill.projeto1.objects.item;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class StatusIcon implements ImageTile {
    private final String name;
    private Position position;

    public StatusIcon(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String getName() { return name; }

    @Override
    public Position getPosition() { return position; }

    @Override
    public void setPosition(Position position) { this.position = position; }
}