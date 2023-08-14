package ga.melara.mcord;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MCord.MODID, name = MCord.NAME, version = MCord.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
public class MCord {
    public static final String MODID = "mcord";
    public static final String NAME = "MC-ord";
    public static final String VERSION = "1.12.2-1.0.0.0";

    public static Logger modLogger;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modLogger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Config.init();
        Bot bot = new Bot();
        bot.init();
    }

    @EventHandler
    public void start(FMLServerStartedEvent e) {
        DiscordEvent.startMessage();
    }

    @EventHandler
    public void stop(FMLServerStoppingEvent e) {
        DiscordEvent.stopMessage();
    }
}
