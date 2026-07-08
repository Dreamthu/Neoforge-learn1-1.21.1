package net.traum.learn1mod.sound;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.JukeboxSong;

public class ModJukeboxSongs {
    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        context.register(ModSounds.BAR_BRAWL_KEY,
                new JukeboxSong(ModSounds.BAR_BRAWL,
                        Component.translatable("jukebox_song.learn1mod.bar_brawl"),
                        180f,   // 歌曲时长（秒）
                        15));   // 红石比较器输出强度 (0-15)
    }
}
