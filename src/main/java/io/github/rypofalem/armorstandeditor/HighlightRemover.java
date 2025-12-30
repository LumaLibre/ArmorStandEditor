package io.github.rypofalem.armorstandeditor;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class HighlightRemover implements Listener {

    private final ArmorStandEditorPlugin INSTANCE = ArmorStandEditorPlugin.instance();

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        Entity[] entities = chunk.getEntities();


        Bukkit.getAsyncScheduler().runNow(INSTANCE, task -> {
            ArmorStand[] armorStands = Arrays.stream(entities)
                    .filter(it -> it instanceof ArmorStand)
                    .toArray(ArmorStand[]::new);
            for (ArmorStand armorStand : armorStands) {
                if (armorStand.hasPotionEffect(PotionEffectType.GLOWING)) {
                    Scheduler.runTask(INSTANCE, () -> {
                        armorStand.removePotionEffect(PotionEffectType.GLOWING);
                    });
                }
            }
        });

    }
}
