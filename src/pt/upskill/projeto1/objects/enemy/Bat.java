package pt.upskill.projeto1.objects.enemy;


import pt.upskill.projeto1.game.Room.Room;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.Random;

public class Bat extends Enemy {
    private static final Random random = new Random();

    public Bat(Position position) {
        super(position, EnemyType.BAT);
    }

    public static Bat create(Position position) {
        return new Bat(position);
    }

    @Override
    public void moveEnemy(Room room, Hero hero) { // Bat anda aleatório
        Position heroPosition = hero.getPosition();

        int dx = heroPosition.getX() - position.getX();
        int dy = heroPosition.getY() - position.getY();

        if (Math.abs(dx) + Math.abs(dy) == 1) {
            hero.takeDamage(getDamage());
            return;
        }
        Direction[] directions = Direction.values();
        Direction direction = directions[random.nextInt(directions.length)];
        Vector2D vector = direction.asVector();
        Position next = position.plus(vector);

        if (room.isWall(next) || next.equals(heroPosition) || room.hasEnemy(next)) {
            return;
        }

        this.position = next;
    }
}
/*
Os adversários movimentam-se aleatoriamente quando estão “longe” do herói, e convergem para o herói
quando estão “perto”.
Thief - Esta personagem deve ter uma movimentação especifica sendo semelhante à peça de xadrez do bispo,
ou seja, a sua movimentação deve ser feita apenas na diagonal.
 */