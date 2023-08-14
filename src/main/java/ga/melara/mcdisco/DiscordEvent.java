package ga.melara.mcdisco;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static ga.melara.mcdisco.Bot.jda;
import static ga.melara.mcdisco.MCDisco.modLogger;

@Mod.EventBusSubscriber
public class DiscordEvent extends ListenerAdapter {

    private static final String SERVER_ID = Config.getString("BOT", "SERVER_ID");
    private static final String CHANNEL_ID = Config.getString("BOT", "CHANNEL_ID");

    public static void startMessage(){
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if(channel == null) return;
        channel.sendMessage(":white_check_mark: サーバーが起動しました！").queue();
    }

    public static void stopMessage(){
        TextChannel channel = jda.getTextChannelById(CHANNEL_ID);
        if(channel == null) return;
        channel.sendMessage(":no_entry: サーバーが停止しました！").queue();
    }


    private MinecraftServer server;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
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
