package pt.upskill.projeto1.objects.enemy;

import pt.upskill.projeto1.game.Room.Room;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

public class Thief extends Enemy{

    public Thief(Position position){
        super(position, EnemyType.THIEF);
    }

    public static Enemy create(Position position){
        return new Thief(position);
    }

    @Override
    public void moveEnemy(Room room, Hero hero){
        //movimentar na diagonal
    }


}