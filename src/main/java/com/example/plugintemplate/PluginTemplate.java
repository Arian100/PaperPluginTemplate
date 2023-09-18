package com.example.plugintemplate;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@DefaultQualifier(NonNull.class)
public final class PluginTemplate extends JavaPlugin {

    private final Logger logger = getLogger();
    private final PluginManager pm = Bukkit.getPluginManager();
    private final CommandDispatcher<CommandSourceStack> dispatcher = DedicatedServer.getServer().getCommands().getDispatcher();
    private static PluginTemplate instance;

    public final String NAME = "Plugin Template";
    public final String VERSION = "1.0.0-SNAPSHOT";


    public static PluginTemplate get() {
        return instance;
    }

    @Override
    public void onLoad() {
        logger.log(Level.INFO, "Loading " + NAME + "...");
    }

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();

        instance = this;

        dispatcher.register(Commands.literal("test").requires(source -> source.hasPermission(1))
            .then(Commands.argument("players", EntityArgument.players())
                .executes(ctx -> {
                    final Collection<ServerPlayer> players = EntityArgument.getPlayers(ctx, "players");
                    players.forEach(sp -> {
                        sp.sendSystemMessage(Component.literal("Test123").withStyle(style -> {
                            return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.nullToEmpty("Hello World!")));
                        }));
                        sp.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 1.0F));
                    });

                    return players.size();
                })));

        logger.log(Level.INFO, "Started " + NAME + " in " + (System.currentTimeMillis() -time) + " ms");
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, "Disabled " + NAME);
    }
}
