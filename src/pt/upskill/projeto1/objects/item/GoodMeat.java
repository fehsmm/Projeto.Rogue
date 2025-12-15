package pt.upskill.projeto1.objects.item;


import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

public class GoodMeat extends Item {

    public GoodMeat(Position position) {
        super(position);
    }
    @Override
    public String getName() {
        return "GoodMeat";
    }
    @Override
    public void applyEffect(Hero hero) {
       hero.healthy(25); //Aumentar a vida e ter pontuação
    }
    @Override
    public int getPoints() {
        return 20;
    }
}
