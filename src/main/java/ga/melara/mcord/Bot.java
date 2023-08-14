package ga.melara.mcord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

import static ga.melara.mcord.MCord.modLogger;


public class Bot {
    protected static JDA jda = null;

    public void init(){
        try {
            jda = JDABuilder.createDefault(Config.getString("BOT", "BOT_TOKEN"), GatewayIntent.GUILD_MESSAGES)
                    .setRawEventsEnabled(true)
                    .setActivity(Activity.playing("Minecraft"))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
            jda.addEventListener(new DiscordEvent());
        } catch (IllegalArgumentException e) {
            modLogger.warn("Bot token is invalid.");
        } catch (LoginException e) {
            modLogger.warn("Bot token is invalid.");
        }
    }
}
