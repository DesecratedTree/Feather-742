package com.feather.utils;

import com.feather.cache.parser.NPCDefinitions;
import com.feather.game.World;
import com.feather.game.WorldTile;
import com.feather.game.npc.EntityDirection;
import com.feather.game.npc.NPC;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;


public final class NPCSpawns {

	static File npcSpawnsFile = new File("data/npc_spawns.yml");
	private static final Object lock = new Object();
	public static List<NPCSpawn> npcSpawns;

	static {
		npcSpawns = new ArrayList<NPCSpawn>();
		loadNPCSpawns(); // Load NPC spawns from the YML file at startup
	}

	public static List<NPCSpawn> getInstance() {
		return npcSpawns;
	}

	private static void addNPCSpawn(String username, int npcId, WorldTile tile, String direction) {
		npcSpawns.add(new NPCSpawn(username, npcId, tile, direction));
		save(); // Save to the YML file after adding a spawn
	}

	public static void save() {
		try {
			Yaml yaml = new Yaml();
			FileWriter writer = new FileWriter(npcSpawnsFile);
			yaml.dump(npcSpawns, writer); // Dump the list to the YML file
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<NPCSpawn> getSpawns() {
		return npcSpawns;
	}

	public static class NPCSpawn {
		public long time;
		public String username;
		public int npcId;
		public WorldTile tile;
		public String direction;  // Add this field to store direction

		public NPCSpawn(String username, int npcId, WorldTile tile, String direction) {
			this.time = System.currentTimeMillis();
			this.username = username;
			this.npcId = npcId;
			this.tile = tile;
			this.direction = direction;  // Set the direction
		}
	}


	public static boolean addUnsavedSpawn(String username, int id, WorldTile tile) {
		synchronized (lock) {
			World.spawnNPC(id, tile, -1, true);
			return true;
		}
	}

	public static boolean addSavedSpawn(String username, int id, WorldTile tile, String direction) {
		synchronized (lock) {
			addNPCSpawn(username, id, tile, direction);
			World.spawnNPC(id, tile, -1, true);
			return true;
		}
	}

	public static boolean removeSavedSpawn(WorldTile tile) {
		synchronized (lock) {
			for (NPCSpawn npcSpawn : npcSpawns) {
				if (npcSpawn.tile.matches(tile)) {
					npcSpawns.remove(npcSpawn);
					save(); // Save to the YML file after removal
					return true;
				}
			}
			return false;
		}
	}

	public static final void loadNPCSpawns() {
		int loadedCount = 0;

		// Load the NPC spawns from the npc_spawns.yml file
		if (!npcSpawnsFile.exists()) {
			return;
		}

		Yaml yaml = new Yaml();
		try {
			FileReader fileReader = new FileReader(npcSpawnsFile);
			// Load as a list of maps
			List<Map<String, Object>> npcSpawnsList = yaml.loadAs(fileReader, List.class);

			for (Map<String, Object> spawn : npcSpawnsList) {
				// Safely parse and convert the values to integers
				int npcId = Integer.parseInt(spawn.get("id").toString());
				int x = Integer.parseInt(spawn.get("x").toString());
				int y = Integer.parseInt(spawn.get("y").toString());
				int z = Integer.parseInt(spawn.get("z").toString());

				// Check if direction exists, otherwise default to "EAST"
				String direction = spawn.containsKey("direction") ? spawn.get("direction").toString() : "EAST";

				WorldTile tile = new WorldTile(x, y, z);
				// Create and add the NPCSpawn object to the list
				npcSpawns.add(new NPCSpawn(null, npcId, tile, direction)); // Assuming null username for now
				loadedCount++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static boolean addSpawn(String username, int id, WorldTile tile) throws Throwable {
		synchronized (lock) {
			File file = new File("data/npcs/spawns.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write("// " + NPCDefinitions.getNPCDefinitions(id).name + ", " + NPCDefinitions.getNPCDefinitions(id).combatLevel + ", added by: " + username);
			writer.newLine();
			writer.flush();
			writer.write(id + " - " + tile.getX() + " " + tile.getY() + " " + tile.getPlane());
			writer.newLine();
			writer.flush();
			writer.close();
			World.spawnNPC(id, tile, -1, true);
			return true;
		}
	}

	public static boolean addUnpackedSpawn(String username, int id, WorldTile tile) throws Throwable {
		synchronized (lock) {
			File file = new File("data/npcs/unpackedSpawns.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write("//" + NPCDefinitions.getNPCDefinitions(id).name + " spawned by " + username);
			writer.newLine();
			writer.flush();
			writer.write(id + " " + tile.getX() + " " + tile.getY() + " " + tile.getPlane());
			writer.newLine();
			writer.flush();
			writer.close();
			World.spawnNPC(id, tile, -1, true);
			return true;
		}
	}

	public static boolean removeSpawn(NPC npc) throws Throwable {
		synchronized (lock) {
			List<String> page = new ArrayList<>();
			File file = new File("data/npcs/spawns.txt");
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			boolean removed = false;
			int id = npc.getId();
			WorldTile tile = npc.getRespawnTile();
			while ((line = in.readLine()) != null) {
				if (line.equals(id + " - " + tile.getX() + " " + tile.getY() + " " + tile.getPlane())) {
					page.remove(page.get(page.size() - 1)); // description
					removed = true;
					continue;
				}
				page.add(line);
			}
			if (!removed)
				return false;
			file.delete();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (String l : page) {
				writer.write(l);
				writer.newLine();
				writer.flush();
			}
			npc.finish();
			return true;
		}
	}

	public static void spawnAllNPCs() {
		synchronized (lock) {
			int loadedCount = 0; // Initialize counter
			for (NPCSpawn npcSpawn : npcSpawns) {
				// Convert the string direction to the actual direction enum or constant as needed
				// For example, assuming EntityDirection is an enum
				EntityDirection direction = EntityDirection.valueOf(npcSpawn.direction.toUpperCase());

				// Spawn the NPC with the direction
				World.spawnNPC(npcSpawn.npcId, npcSpawn.tile, -1, true, direction);
				loadedCount++; // Increment the counter for each NPC spawned
			}

			// Log the count of NPCs that were spawned
			Logger.log("NPCSpawns", "Spawned " + loadedCount + " NPCs from the list.");
		}
	}

	public static void init() {
		// Call spawnAllNPCs to spawn all NPCs from the list
		spawnAllNPCs();
	}
}