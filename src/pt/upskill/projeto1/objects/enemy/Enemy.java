package pt.upskill.projeto1.objects.enemy;

import pt.upskill.projeto1.game.Room.Room;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Enemy implements ImageTile {
    protected Position position;
    protected EnemyType type;
    protected int health;

    public Enemy(Position position, EnemyType type) {
        this.position = position;
        this.type = type;
        this.health = type.getMaxHealth();
    }

    public static Enemy createEnemy(char symbol, Position position) {
        EnemyType type = EnemyType.fromSymbol(symbol);
        if (type == null) return null;

        switch (type) {
            case BAT:
                return Bat.create(position);
            case SKELETON:
                return Skeleton.create(position);
            case BADGUY:
                return BadGuy.create(position);
            case THIEF:
                return Thief.create(position);
            default:
                return null;
        }
    }

    public abstract void moveEnemy(Room room, Hero hero);

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return type.getDamage();
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public boolean isDead() {
        return health <= 0;
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
        return type.getImageName();
    }
}