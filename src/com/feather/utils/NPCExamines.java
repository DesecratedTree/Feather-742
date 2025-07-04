package com.feather.utils;

import com.feather.game.npc.NPC;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPCExamines {

    private final static HashMap<Integer, String> npcExamines = new HashMap<>();
    private final static String YAML_PATH = "data/npc_examines.yml"; // Path to the YAML file

    public static void init() {
        if (new File(YAML_PATH).exists())
            loadYamlNPCExamines();  // Load from YAML if the file exists
        else
            Logger.log("NPCExamines", "NPC examine data file not found.");
    }

    public static String getExamine(NPC npc) {
        String examine = npcExamines.get(npc.getId());
        if (examine != null)
            return examine;
        return "NPC ID: " + npc.getId() + " missing examine text. Please report this to a developer.";
    }

    private static void loadYamlNPCExamines() {
        try {
            Yaml yaml = new Yaml();
            // Load the list of maps (each map corresponds to one NPC's examine)
            List<Map<String, Object>> data = yaml.load(new FileReader(YAML_PATH));
            int loadedCount = 0; // Counter for loaded examines

            // Iterate through the list of maps
            for (Map<String, Object> entry : data) {
                // Extract the npcId and examine text
                int npcId = (Integer) entry.get("id");
                String examineText = (String) entry.get("examine");

                npcExamines.put(npcId, examineText);
                loadedCount++;
            }

            Logger.log("NPCExamines", "Loaded " + loadedCount + " NPC examines from YAML.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
