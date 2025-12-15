package pt.upskill.projeto1.game.Room;

import pt.upskill.projeto1.game.DoorInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReaderRoom {
    final List<DoorInfo> doorInfo;
    final List<String> keyNames;

    public ReaderRoom(List<DoorInfo> doorInfo, List<String> keyNames) {
        this.doorInfo = doorInfo;
        this.keyNames = keyNames;
    }

    public static ReaderRoom readerRoom(String file) throws FileNotFoundException {
        List<DoorInfo> doorInfo = new ArrayList<>();
        List<String> keyNames = new ArrayList<>();
        Scanner sc = new Scanner(new File(file));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.startsWith("#")) {
                break;
            }
            String clean = line.substring(1).trim();
            if (clean.isEmpty())
                continue;
            String[] parts = clean.split("\\s+");
            if (parts[0].equals("k")) {
                if (parts.length >= 2) {
                    keyNames.add(parts[1]); // guarda "key1", "key2", etc.
                }
                continue;
            }
            if (parts.length < 4) {// linha estranha, ignora
                continue;
            }
            int id = Integer.parseInt(parts[0]);
            char type = parts[1].charAt(0);
            String nextRoom = parts[2];
            int nextDoorId = Integer.parseInt(parts[3]); // porta de saída na outra sala
            String key = (parts.length >= 5) ? parts[4] : null;

            doorInfo.add(new DoorInfo(id, type, nextRoom, nextDoorId, key));
        }
        sc.close();
        return new ReaderRoom(doorInfo, keyNames);
    }
}