package ga.melara.mcord;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import static ga.melara.mcord.Bot.jda;
import static ga.melara.mcord.MCord.modLogger;

public class DiscordEvent extends ListenerAdapter {

    private static final String SERVER_ID = Config.getString("BOT", "SERVER_ID");
    private static final String CHANNEL_ID = Config.getString("BOT", "CHANNEL_ID");

    public static void startMessage() {
        if (jda == null) return;
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if (channel == null) return;
        channel.sendMessage(":white_check_mark: サーバーが起動しました！").queue();
    }

    public static void stopMessage() {
        if (jda == null) return;
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if (channel == null) return;
        channel.sendMessage(":no_entry: サーバーが停止しました！").queue();
    }


    private MinecraftServer server;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (jda == null) return;
        String user = e.getMessage().getMember().getNickname();
        String content = e.getMessage().getContentDisplay();
        server = FMLCommonHandler.instance().getMinecraftServerInstance();

        modLogger.info(String.format("<%s> %s", user, content));

        modLogger.info(server.getPlayerList().getFormattedListOfPlayers(false));

        if (e.getGuild().getId().equals(SERVER_ID) && e.getChannel().getId().equals(CHANNEL_ID)) {
            if (!e.getAuthor().equals(jda.getSelfUser())) {
                String message = String.format("<%s> %s", user, content);
                server.getPlayerList().sendMessage(new TextComponentString(message));
            }
        }
    }
}
