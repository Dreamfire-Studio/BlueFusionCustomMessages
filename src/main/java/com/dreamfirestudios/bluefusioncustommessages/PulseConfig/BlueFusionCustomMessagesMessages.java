package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamConfig.Abstract.StaticEnumPulseConfig;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.Messages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.Permissions;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@PulseAutoRegister
public class BlueFusionCustomMessagesMessages extends StaticEnumPulseConfig<BlueFusionCustomMessagesMessages, Messages, String> {
    @Override
    public JavaPlugin mainClass() {return BlueFusionCustomMessages.GetBlueFusionCustomMessages();}
    @Override
    protected Class<Messages> getKeyClass() { return Messages.class;}
    @Override
    protected Class<String> getValueClass() {return String.class; }
    @Override
    protected String getDefaultValueFor(Messages craftLegendsCoreMessage) { return craftLegendsCoreMessage.message; }

    public void SendMessageToBroadcast(Messages message, Object... objects){
        var storedMessage = getDefaultValueFor(message);
        if(storedMessage.isEmpty()) return;
        var cleanedMessage = com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage.formatMessage(String.format(storedMessage, objects));
        com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireChat.BroadcastMessage(PlainTextComponentSerializer.plainText().serialize(cleanedMessage));
    }

    public void SendMessageToPlayerPermission(Messages message, Permissions nexusCorePermission, Object... objects){
        BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
            for(var player : Bukkit.getOnlinePlayers()){
                if(!craftLegendsCorePermissions.DoesPlayerHavePermission(nexusCorePermission, player, false)) continue;
                SendMessageToPlayer(message, player, objects);
            }
        });
    }

    public void SendMessageToPlayer(Messages message, Player player, Object... objects){
        if(player == null || message == null) return;
        var storedMessage = getDefaultValueFor(message);
        if(storedMessage.isEmpty()) return;
        var cleanedMessage = com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage.formatMessage(String.format(storedMessage, objects), player);
        com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireChat.SendMessageToPlayer(player, PlainTextComponentSerializer.plainText().serialize(cleanedMessage));
    }

    public void SendMessageToContext(Messages message, Player player, ConversationContext conversationContext, Object... objects){
        if(player == null || message == null) return;
        var storedMessage = getDefaultValueFor(message);
        if(storedMessage.isEmpty()) return;
        var cleanedMessage = com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage.formatMessage(String.format(storedMessage, objects), player);
        conversationContext.getForWhom().sendRawMessage(PlainTextComponentSerializer.plainText().serialize(cleanedMessage));
    }

    public void SendMessageToConsole(Messages message, Object... objects){
        if(message == null) return;
        var storedMessage = getDefaultValueFor(message);
        if(storedMessage.isEmpty()) return;
        var cleanedMessage = com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage.formatMessage(String.format(storedMessage, objects));
        com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireChat.SendMessageToConsole(PlainTextComponentSerializer.plainText().serialize(cleanedMessage));
    }
}
