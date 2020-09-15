package nirusu.nirubot.command.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import nirusu.nirubot.Nirubot;
import nirusu.nirubot.command.CommandContext;
import nirusu.nirubot.command.ICommand;
import nirusu.nirubot.core.GuildManager;
import nirusu.nirubot.util.arknight.Operator;
import nirusu.nirubot.util.arknight.TagCombination;

public class Recruitment implements ICommand {

    @Override
    public void execute(CommandContext ctx) {
        List<String> args = ctx.getArgs();

        if (args.size() >  15 || args.size() < 2) {
            return;
        }

        List<String> tags = new ArrayList<>();
        StringBuilder b = new StringBuilder();

        for (int i = 1; i < args.size(); i++) {
            tags.add(args.get(i));
            b.append(args.get(i));
            if (i + 1  < args.size()) {
                b.append(" ");
            }
        }

        List<TagCombination> all = nirusu.nirubot.util.arknight.Recruitment
        .getRecruitment().calculate(tags, b.toString());

        Collections.reverse(all);

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Nirubot.getColor()).setTitle("All combinations:");
        StringBuilder builder = new StringBuilder();

        for (TagCombination cb : all) {

            if (cb.toString().length() > 2000) {

                if (builder.length() != 0) {
                    emb.setDescription(builder.toString());
                    ctx.reply(emb.build());
                    builder = new StringBuilder();
                    emb = new EmbedBuilder();
                    emb.setColor(Nirubot.getColor());
                }

                Iterator<String> i = cb.toStringAsList().iterator();
                StringBuilder tmp = new StringBuilder();

                if (i.hasNext()) {
                    tmp.append(i.next());
                }

                while (i.hasNext()) {
                    String n = i.next();
                    if (tmp.length() + n.length() > 1800) {
                        emb.setDescription(tmp.substring(0, tmp.length() - 1));
                        ctx.reply(emb.build());
                        tmp = new StringBuilder();
                        emb = new EmbedBuilder();
                        emb.setColor(Nirubot.getColor());
                    }
                    tmp.append(n).append(" ");
                }

                if (tmp.length() > 0) {
                    emb.setDescription(tmp.substring(0, tmp.length() - 1));
                    ctx.reply(emb.build());
                    tmp = new StringBuilder();
                    emb = new EmbedBuilder();
                    emb.setColor(Nirubot.getColor());

                }

            } else {
                if (builder.length() + cb.toString().length() > 1800) {
                    emb.setDescription(builder.toString());
                    ctx.reply(emb.build());
                    builder = new StringBuilder();
                    emb = new EmbedBuilder();
                    emb.setColor(Nirubot.getColor());
                }
                builder.append(cb).append("\n\n");
            }
        }
        if (builder.length() > 0) {
            emb.setDescription(builder.toString());
            ctx.reply(emb.build());
        }
    }

    @Override
    public List<String> alias() {
        return Arrays.asList("rec");
    }

    @Override
    public MessageEmbed helpMessage(GuildManager gm) {
        return ICommand.createHelp("Gets the best possible combinations for up to 6 tags. Tags are:\n" + Operator.getAllTagsAsString(), gm.prefix(), this);
    }

}
