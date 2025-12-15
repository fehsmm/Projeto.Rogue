package pt.upskill.projeto1.objects.item;

import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

public class Hammer extends Item {

    public Hammer(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public void applyEffect(Hero hero) {
        //
    }
    @Override
    public int getPoints() {
        return 10;
    }
}