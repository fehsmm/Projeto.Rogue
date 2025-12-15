package pt.upskill.projeto1.game;

import pt.upskill.projeto1.game.Room.Room;
import pt.upskill.projeto1.game.Room.RoomAdm;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Door;
import pt.upskill.projeto1.objects.Key;
import pt.upskill.projeto1.objects.StatusBar;
import pt.upskill.projeto1.objects.enemy.Enemy;
import pt.upskill.projeto1.objects.item.GoodMeat;
import pt.upskill.projeto1.objects.item.Item;
import pt.upskill.projeto1.objects.item.StatusIcon;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Engine {
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    private RoomAdm roomAdm = new RoomAdm();
    private Room room;
    private Hero hero;
    private int score = 0; // Pontuação do jogo(NÃO é por sala)

    public void init() {
        try {
            room = roomAdm.loadCurrentRoom();
            hero = room.getHero();//Mostra o Hero q foi desenhado no Room

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar", e);
        }
        gui.setEngine(this);
        gui.newImages(room.getTiles());
        updateStatusBar();
        gui.setStatus("LET'S BORA!");
        gui.go();
        gui.update();
    }

    public void notify(int keyPressed) {
        int slot = -1;
        if (keyPressed == KeyEvent.VK_1 || keyPressed == KeyEvent.VK_NUMPAD1) slot = 0;
        else if (keyPressed == KeyEvent.VK_2 || keyPressed == KeyEvent.VK_NUMPAD2) slot = 1;
        else if (keyPressed == KeyEvent.VK_3 || keyPressed == KeyEvent.VK_NUMPAD3) slot = 2;
        if (slot != -1) {
            Item dropped = hero.drop(slot);
            if (dropped != null) {
                dropped.setPosition(hero.getPosition());
                room.addItem(dropped);
                gui.setStatus("Deixou pra trás o: " + dropped.getName());
            } else {
                gui.setStatus("Slot " + (slot + 1) + " vazio.");
            }
            gui.clearImages();
            gui.newImages(room.getTiles());
            updateStatusBar();
            gui.update();
            return;
        }
        Direction direction = null;
        if (keyPressed == KeyEvent.VK_DOWN) direction = Direction.DOWN;
        if (keyPressed == KeyEvent.VK_UP) direction = Direction.UP;
        if (keyPressed == KeyEvent.VK_LEFT) direction = Direction.LEFT;
        if (keyPressed == KeyEvent.VK_RIGHT) direction = Direction.RIGHT;
        if (direction == null) return; // outra tecla qualquer nao mexe
        boolean moved = hero.movePrincess(direction, room); //HERO
        if (!moved) {
            gui.setStatus("Let's bora encontrar a saída!");
        }
        Item it = room.getItemIn(hero.getPosition()); //ITEM
        if (it != null) {
            if (it instanceof GoodMeat) {
                it.applyEffect(hero);
                room.removeItem(it);
                score += it.getPoints();
                gui.setStatus("Huuuummm Chocolate ebaaa!! (+" + it.getPoints() + "). Vida: " + hero.getAtualHealth()
                        + hero.getInventory().get(0) + hero.getInventory().get(1) + hero.getInventory().get(2));
            } else {
                if (hero.tryPick(it)) {
                    room.removeItem(it);
                    score += it.getPoints();
                    gui.setStatus("Pegou o " + it.getName() + " (+" + it.getPoints() + "). Score: " + score);
                } else {
                    gui.setStatus("Inventário cheio. Score: " + score);
                }
            }
        } else {
            gui.setStatus("Score: " + score);
        }
        ImageTile picked = null; //KEY
        for (ImageTile t : room.getTiles()) {
            if (t instanceof Key && t.getPosition().equals(hero.getPosition())) {
                Key k = (Key) t;
                hero.addKey(k.getKeyName());
                picked = t;
                gui.setStatus("Huurruulll achou a chave: " + k.getKeyName());
                break;
            }
        }
        if (picked != null) {
            room.getTiles().remove(picked);
        }
        Door door = room.getDoorIn(hero.getPosition()); //DOOR
        if (door != null) {
            String required = door.getRequiredKey();
            if (required != null && !door.isOpen()) {
                if (hero.hasKey(required)) {
                    door.open();
                    gui.setStatus("Isso aí abriu a porta! " + required + "!");
                } else {
                    gui.setStatus("Sem chave não dá pra abrir né: " + required);
                    updateStatusBar();
                    gui.update();
                    return;
                }
            }
            try {
                room.removeHeroFromTiles(hero);
                room = roomAdm.changeRoom(door, hero);
                hero = room.getHero();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                gui.setStatus("Erro ao trocar de sala!");
                updateStatusBar();
                gui.update();
                return;
            }
        }
        for (Enemy e : room.getEnemy()) { //ENEMY
            e.moveEnemy(room, hero);
        }
        List<Enemy> dead = new ArrayList<>();
        for (Enemy e : room.getEnemy()) {
            if (e.isDead()) dead.add(e);
        }
        for (Enemy e : dead) {
            room.removeEnemy(e);
            room.getTiles().remove(e);
        }
        if (hero.isDead()) {
            updateStatusBar(); // mostra vida no 0 antes da msg
            gui.setStatus("GAME OVER. O inimigo te pegou");
            gui.update();
            gui.showMessage("Game Over", "Pontuação final: " + score);
            return;
        }
        gui.clearImages();
        gui.newImages(room.getTiles());
        updateStatusBar();
        gui.update();
    }

    public void updateStatusBar() {
        List<Item> inv = hero.getInventory();
        while (inv.size() < 3) inv.add(null);
        List<ImageTile> status = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            status.add(new StatusIcon("Black", new Position(x, 0)));
        }
        int hearts = 5;
        int filled = (int) Math.round((hero.getAtualHealth() / (double) hero.getMaxHealth()) * hearts);
        for (int i = 0; i < hearts; i++) {
            String img = (i < filled) ? "Green" : "Red";
            status.add(new StatusIcon(img, new Position(i, 0)));
        }
        int startX = 7;
        for (int i = 0; i < 3; i++) {
            Item invItem = inv.get(i);
            if (invItem != null) {
                status.add(new StatusIcon(invItem.getName(), new Position(startX + i, 0)));
            }
        }
        gui.clearStatus();
        gui.newStatusImages(status);
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.init();
    }
}