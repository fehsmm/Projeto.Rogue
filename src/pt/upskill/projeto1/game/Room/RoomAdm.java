package pt.upskill.projeto1.game.Room;


import pt.upskill.projeto1.game.DoorInfo;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Door;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoomAdm {
    private String currentRoomFile = "rooms/room0.txt";
    private final Map<String, Room> roomCache = new HashMap<>();
    private List<DoorInfo> doorInfo = new ArrayList<>();
    private List<String> keyNames = new ArrayList<>();

    public Room loadCurrentRoom() throws FileNotFoundException {
        return loadRoom(currentRoomFile);
    }

    private Room loadRoom(String file) throws FileNotFoundException {
        System.out.println("LOAD ROOM: " + file + " cached=" + roomCache.containsKey(file));
        if (roomCache.containsKey(file)) {
            return roomCache.get(file);
        }
        ReaderRoom readerRoom = ReaderRoom.readerRoom(file);
        this.doorInfo = readerRoom.doorInfo;
        this.keyNames = readerRoom.keyNames;
        Room room = new Room(this, file); //Cria só uma vez a Room
        roomCache.put(file, room);
        return room;
    }

    public Room changeRoom(Door door, Hero hero) throws FileNotFoundException {//para carregar todas as salas sem precisar trocar no Engine
        String nextFile = "rooms/" + door.getNextRoom();//Muda para proxima sala
        currentRoomFile = nextFile;
        Room nextRoom = loadRoom(nextFile);
        nextRoom.setHero(hero);
        Door startDoor = nextRoom.getDoorById(door.getNextDoorId());
        if (startDoor != null) {
            hero.setPosition(startDoor.getPosition());
        }
        return nextRoom;
    }

    public Door createDoor(char symbol, Position p) {
        if (!Character.isDigit(symbol)) return null;
        int id = symbol - '0';
        DoorInfo info = null;
        for (DoorInfo d : doorInfo) {
            if (d.getId() == id) {
                info = d;
                break;
            }
        }
        if (info == null) return null;
        return new Door(p, info);
    }

    public String takeNextKeyName() {
        if (keyNames.isEmpty()) return null;
        return keyNames.remove(0);
    }
}