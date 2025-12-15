package pt.upskill.projeto1.objects.enemy;

import pt.upskill.projeto1.game.Room.Room;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

public class Skeleton extends Enemy {

    public Skeleton(Position position) {
        super(position, EnemyType.SKELETON);
    }

    public static Enemy create(Position position) {
        return new Skeleton(position);
    }

    @Override
    public void moveEnemy(Room room, Hero hero) {
        Position heroPosition = hero.getPosition();
        int dx = heroPosition.getX() - position.getX();
        int dy = heroPosition.getY() - position.getY();

        if (Math.abs(dx) + Math.abs(dy) == 1) {
            hero.takeDamage(type.getDamage());
            this.takeDamage(hero.getDamage());
            return;
        }
        int mx = 0;
        int my = 0;
        if (Math.abs(dx) >= Math.abs(dy)) {
            mx = Integer.compare(dx, 0); // -1, 0, 1
        } else {
            my = Integer.compare(dy, 0);
        }
        Position next = new Position(position.getX() + mx, position.getY() + my);

        if (room.isWall(next) || room.hasEnemy(next) || next.equals(heroPosition)) {
            return;
        }
        this.position = next;
    }

}


