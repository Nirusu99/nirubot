package nirusu.nirubot.command.fun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.entities.MessageEmbed;
import nirusu.nirubot.command.CommandContext;
import nirusu.nirubot.command.ICommand;
import nirusu.nirubot.core.GuildManager;
import nirusu.nirubot.util.RandomHttpClient;

public class Mock implements ICommand {

    @Override
    public void execute(CommandContext ctx) {

        ctx.getArgs();

        List<String> args = ctx.getArgs();

        if (args.size() < 2) {
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < args.size(); i++) {
            builder.append(args.get(i)).append(" ");
        }

        String message = builder.substring(0, builder.length() - 1);

        List<Byte> nums;
        try {
            nums = RandomHttpClient.getRandomBit(message.length());
        } catch (IOException e) {
            ctx.reply(e.getMessage());
            return;
        }
        Iterator<Byte> it = nums.iterator();
        builder = new StringBuilder();
        for (char ch : message.toCharArray()) {
            byte num = it.next();
            char c = num == 0 ? Character.toUpperCase(ch) : Character.toLowerCase(ch);
            builder.append(c);
        }

        ctx.reply(builder.toString());
    }



    @Override
    public MessageEmbed helpMessage(GuildManager gm) {
        return ICommand.createHelp("makes your message retarded", gm.prefix(), this);
    }

    @Override
    public List<String> alias() {
        return Arrays.asList();
    }
    
}
