package pt.upskill.projeto1.objects.item;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Item implements ImageTile {
    protected Position position;

    public Item(Position position) {
        this.position = position;
    }

    public abstract void applyEffect(Hero hero);// o que acontece quando o herói pega um item

    public abstract int getPoints();

    public static Item createItem(char c, Position p) {
        switch (c) {
            case 'h':
                return new Hammer(p);
            case 'm':
                return new GoodMeat(p);
            default:
                return null;
        }
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
    public abstract String getName();

}
