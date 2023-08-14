package ga.melara.mcdisco;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.awt.*;
import java.io.File;
import java.util.regex.Pattern;

import static ga.melara.mcdisco.Bot.jda;
import static ga.melara.mcdisco.MCDisco.modLogger;
import static net.minecraft.entity.player.EntityPlayer.getOfflineUUID;

@Mod.EventBusSubscriber
public class MCChatEvent {

    private static final String SERVER_ID = Config.getString("BOT", "SERVER_ID");
    private static final String CHANNEL_ID = Config.getString("BOT", "CHANNEL_ID");

    @SubscribeEvent
    public static void onChat(ServerChatEvent e) {
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        String message = String.format("<%s> %s", e.getPlayer().getDisplayName().getUnformattedText(), e.getMessage());
        channel.sendMessage(message).queue();
    }

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent e) {
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        EmbedBuilder eb = new EmbedBuilder();

        if (e.getAdvancement() != null && e.getAdvancement().getDisplay() != null && e.getAdvancement().getDisplay().shouldAnnounceToChat()) {
            String title = e.getAdvancement().getDisplay().getTitle().getUnformattedText();
            eb.setTitle(String.format("%sは%s を達成した！", e.getEntityPlayer().getDisplayName().getUnformattedText(), title));
        }
        eb.setColor(Color.YELLOW);
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent e) {
        if (e.getEntityLiving() instanceof EntityPlayer) {
            TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(String.format("%sが死んでしまった",e.getEntityLiving().getDisplayName().getUnformattedText()));
            eb.setDescription(String.format("死因：%s", e.getSource().damageType));
            eb.setColor(Color.DARK_GRAY);
            channel.sendMessageEmbeds(eb.build()).queue();
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent e) {
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(String.format("%sがログインしました", e.player.getDisplayName().getUnformattedText()));
        eb.setColor(Color.GREEN);
        eb.setThumbnail(String.format("https://crafatar.com/avatars/%s?size=48", e.player.getUniqueID()));
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent e) {
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(String.format("%sがログアウトしました", e.player.getDisplayName().getUnformattedText()));
        eb.setColor(Color.RED);
        eb.setThumbnail(String.format("https://crafatar.com/avatars/%s?size=48", e.player.getUniqueID()));
        channel.sendMessageEmbeds(eb.build()).queue();
    }
}
