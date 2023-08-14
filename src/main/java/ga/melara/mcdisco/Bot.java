package ga.melara.mcdisco;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import javax.xml.soap.Text;

public class Bot {
    protected static JDA jda = null;

    public void init(){
        jda = JDABuilder.createDefault(Config.getString("BOT", "BOT_TOKEN"), GatewayIntent.GUILD_MESSAGES)
                .setRawEventsEnabled(true)
                .setActivity(Activity.playing("Minecraft工業"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.addEventListener(new DiscordEvent());
    }
}
