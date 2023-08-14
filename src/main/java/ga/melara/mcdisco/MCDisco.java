package ga.melara.mcdisco;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

@Mod(modid = MCDisco.MODID, name = MCDisco.NAME, version = MCDisco.VERSION)
public class MCDisco
{
    public static final String MODID = "mcdisco";
    public static final String NAME = "MCDisco";
    public static final String VERSION = "1.12.2-1.0.0.1";

    public static Logger modLogger;

    private static JDA jda = null;
    private static String BOT_TOKEN = "";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Config.writeConfig("BOT", "BOT TOKEN", "example token");
        BOT_TOKEN = Config.getString("BOT", BOT_TOKEN);

        try {
            jda = JDABuilder.createDefault(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES)
                    .setRawEventsEnabled(true)
                    .setActivity(Activity.playing("Crafting"))
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
