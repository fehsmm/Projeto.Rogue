package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.Room.Room;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;
import pt.upskill.projeto1.objects.item.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hero implements ImageTile {
    private Position position;
    private int atualHealth = 150;
    private int maxHealth = 150;
    private int damage = 30;
    private final Set<String> keys = new HashSet<>();
    private final List<Item> inventory = new ArrayList<>(3);

    public Hero(Position position) {
        this.position = position;
        inventory.add(null);
    }
    public boolean movePrincess(Direction direction, Room room) {
        Vector2D vector = direction.asVector();
        Position next = position.plus(vector);
        if (room.isWall(next) || room.hasEnemy(next)) {
            return false;
        }
        this.position = next;
        return true;
    }

    public int getAtualHealth(){
        return atualHealth;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public void healthy(int amount){
        atualHealth += amount;
        if(atualHealth > maxHealth) atualHealth = maxHealth;
    }
    public void takeDamage(int amount){
        atualHealth -= amount;
        if(atualHealth < 0) atualHealth = 0; //morreu
    }
    public int getDamage() {
        return damage;
    }
    public void addKey(String keyName) {
        keys.add(keyName);
    }
    public boolean hasKey(String keyName) {
        return keys.contains(keyName);
    }

    public List<Item> getInventory() {
        return inventory;
    }
    public boolean tryPick(Item item) {
        for (int i = 0; i < 3; i++) {
            if (inventory.get(i) == null) {
                inventory.set(i, item);
                return true;
            }
        }
        return false;
    }
    public Item drop(int slotIndex) {
        if (slotIndex < 0 || slotIndex >= 3) return null;
        Item item = inventory.get(slotIndex);
        if (item == null) return null;
        inventory.set(slotIndex, null);
        return item;
    }
    public boolean isDead() {
        return atualHealth <= 0;
    }

    @Override
    public String getName() {
        return "Hero";
    }
    @Override
    public Position getPosition() {
        return position;
    }
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
/*
O herói deve poder atacar adversários à distância desde que estejam na mesma linha ou coluna
 */