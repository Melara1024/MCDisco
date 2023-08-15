package ga.melara.mcord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.regex.Pattern;

import static ga.melara.mcord.Bot.jda;

@Mod.EventBusSubscriber
public class MCChatEvent {

    private static final String SERVER_ID = Config.getString("BOT", "SERVER_ID");
    private static final String CHANNEL_ID = Config.getString("BOT", "CHANNEL_ID");

    @SubscribeEvent
    public static void onChat(ServerChatEvent e) {
        if (jda == null) return;
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if (channel == null) return;

        String user = removeFormatting(e.getPlayer().getDisplayName().getUnformattedText());
        String message = String.format("<%s> %s", user, e.getMessage());
        channel.sendMessage(message).queue();
    }

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent e) {
        if (jda == null) return;
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if (channel == null) return;
        EmbedBuilder eb = new EmbedBuilder();

        String user = removeFormatting(e.getEntityPlayer().getDisplayName().getUnformattedText());
        if (e.getAdvancement() != null && e.getAdvancement().getDisplay() != null && e.getAdvancement().getDisplay().shouldAnnounceToChat()) {
            String title = e.getAdvancement().getDisplay().getTitle().getUnformattedText();
            eb.setTitle(String.format("%sは%s を達成した！", user, title));
        }
        eb.setColor(Color.YELLOW);
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent e) {
        if (jda == null) return;
        if (e.getEntityLiving() instanceof EntityPlayer) {
            TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
            if (channel == null) return;

            String user = removeFormatting(e.getEntityLiving().getDisplayName().getUnformattedText());
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(String.format("%sは死んでしまった", user));
            eb.setDescription(String.format("死因：%s", e.getSource().damageType));
            eb.setColor(Color.DARK_GRAY);
            channel.sendMessageEmbeds(eb.build()).queue();
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent e) {
        try {
            if (jda == null) return;
            TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
            if (channel == null) return;

            String user = removeFormatting(e.player.getDisplayName().getUnformattedText());
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(String.format("%sがログインしました", user));
            eb.setColor(Color.GREEN);
            eb.setThumbnail(String.format("https://crafatar.com/avatars/%s?size=32", e.player.getUniqueID()));
            channel.sendMessageEmbeds(eb.build()).queue();
        } catch (NullPointerException exception) {

        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent e) {
        try {
            if (jda == null) return;
            TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
            if (channel == null) return;

            String user = removeFormatting(e.player.getDisplayName().getUnformattedText());
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(String.format("%sがログアウトしました", user));
            eb.setColor(Color.RED);
            eb.setThumbnail(String.format("https://crafatar.com/avatars/%s?size=32", e.player.getUniqueID()));
            channel.sendMessageEmbeds(eb.build()).queue();
        } catch (NullPointerException exception) {

        }
    }

    private static final Pattern FORMATTING_CODE_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");

    @Nonnull
    public static String removeFormatting(@Nonnull String text) {
        return FORMATTING_CODE_PATTERN.matcher(text).replaceAll("");
    }
}
