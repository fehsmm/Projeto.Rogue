package pt.upskill.projeto1.game.Room;


import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.enemy.Enemy;
import pt.upskill.projeto1.objects.item.Item;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
    private RoomAdm roomAdm;

    private List<ImageTile> tiles = new ArrayList<>();
    private List<Enemy> enemy = new ArrayList<>();
    private List<Door> doors = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private Hero hero;

    public Room(RoomAdm roomAdm, String fileName) throws FileNotFoundException {
        this.roomAdm = roomAdm;
        createFloor(); //Primeiro cria o chao pra os trem nao desaparecer qndo mover
        loadFloor(fileName); //Depois coloca as paredes, Hero, enemy
    }
    public void removeHeroFromTiles(Hero hero) {
        tiles.remove(hero);
    }
    public void removeEnemy(Enemy e) {
        enemy.remove(e);
    }

    private void createFloor() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                tiles.add(new Floor(new Position(x, y)));
            }
        }
    }

    private void loadFloor(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        int y = 0;
        while (sc.hasNextLine() && y < 10) {//enquanto tiver linha pra ler e estiver dentro vai fazendo o loop
            String line = sc.nextLine();
            if (line.startsWith("#")) { //Ignora essas linhas q estao no room.txt
                continue;
            }
            for (int x = 0; x < line.length() && x < 10; x++) {
                char c = line.charAt(x);
                Position p = new Position(x, y);
                if (c == 'W') {
                    tiles.add(new Wall(p)); //Parede em cima do Floor
                }
                if (c == 'H') {
                    hero = new Hero(p);
                    tiles.add(hero);  //Princess em cima do Floor, espero q nao desaparece agora
                }Door d = roomAdm.createDoor(c, p); //Cria a porta
                if (d != null) {
                    doors.add(d);
                    tiles.add(d);
                    continue;
                }
                Enemy e = Enemy.createEnemy(c, p); //Cria o inimigo
                if (e != null) {
                    enemy.add(e);
                    tiles.add(e);
                    continue;
                }
                Item item = Item.createItem(c, p);
                if (item != null) {
                    items.add(item);
                    tiles.add(item);
                }
                if (c == 'k') {
                    String keyName = roomAdm.takeNextKeyName();
                    if (keyName != null) {
                        Key key = new Key(p, keyName);
                        tiles.add(key);
                    }
                    continue;
                }
            }
            y++;
        }
        sc.close();
    }
    public Item getItemIn(Position p) {
        for (Item it : items) {
            if (it.getPosition().equals(p)) return it;
        }
        return null;
    }
    public void removeItem(Item it) {
        items.remove(it);
        tiles.remove(it);
    }
    public void addItem(Item it) {
        items.add(it);
        tiles.add(it);
    }

    private boolean hasObjectAt(Position p, Class<?> clazz) { //Prof mandou fazer um generico
        for (ImageTile tile : tiles) {
            if ((clazz.isInstance(tile)) && tile.getPosition().equals(p)){//Vê se tem objeto dessas classes na posição p
                return true;                                               //Passa a varredura geral, pra tdo lado.
            }
        } return  false;
    }
    public boolean isWall(Position p) { return hasObjectAt(p, Wall.class); }
    public boolean hasEnemy(Position p) { return hasObjectAt(p, Enemy.class);}
    public boolean hasHero(Position p) { return hasObjectAt(p, Hero.class);}

    public Door getDoorById(int id) {
        for(Door d : doors){
            if(d.getId() == id){
                return d;
            }
        }return null;
    }
    public Door getDoorIn(Position p) {
        for (Door d : doors) {
            if (d.getPosition().equals(p)) {
                return d;
            }
        }
        return null;
    }
    public List<ImageTile> getTiles(){return tiles;}
    public Hero getHero(){
        return hero;
    }
    public void setHero(Hero hero){
        this.hero = hero;
        if(!tiles.contains(hero)) tiles.add(hero);
    }
    public List<Enemy> getEnemy(){
        return enemy;
    }

}
/*
DoorOpen = 0 - D
DoorClose = 1 - ?
DoorWay = 2 - E
 */