package com.dreamfirestudios.bluefusioncustommessages.ServerCommand;

import com.dreamfirestudios.dreamCommand.Interface.PCMethod;
import com.dreamfirestudios.dreamCommand.Interface.PCSignature;
import com.dreamfirestudios.dreamCommand.ServerCommand;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.Enum.Messages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesMessages;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashMap;

@PulseAutoRegister
public class BlueFusionCustomMessagesServerCommand extends ServerCommand {

    public BlueFusionCustomMessagesServerCommand() {
        super("bluefusioncustommessagesserver", false);
    }

    @Override
    public void NoMethodFound(CommandSender commandSender, String s, String[] strings) {}
    @Override
    public String helpMenuPrefix(CommandSender commandSender) {return "";}
    @Override
    public LinkedHashMap<String, String> helpMenuFormat(CommandSender commandSender, LinkedHashMap<String, String> linkedHashMap) {return new LinkedHashMap<>();}
    @Override
    public String helpMenuSuffix(CommandSender commandSender) {return "";}

    @PCMethod
    @PCSignature({})
    public void BlueFusionCustomMessagesMethod(CommandSender commandSender){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, craftLegendsCoreConfig -> {
            if(!craftLegendsCoreConfig.systemEnabled) return;
            BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
                craftLegendsCoreMessages.SendMessageToConsole(Messages.SystemIsntEnabled);
            }, Throwable::printStackTrace);
        }, Throwable::printStackTrace);
    }

    @PCMethod
    @PCSignature({"enable"})
    public void BlueFusionCustomMessagesEnableMethod(CommandSender commandSender, boolean state){
        BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesEnableSystem(dreamCoreTestTemplateConfig -> {}, state);
        BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
            craftLegendsCoreMessages.SendMessageToConsole(state ? Messages.ConsoleEnabledSystem : Messages.ConsoleDisableSystem);
        }, Throwable::printStackTrace);
    }

    @PCMethod
    @PCSignature({"configs", "reset"})
    public void BlueFusionCustomMessagesConfigsResetMethod(CommandSender commandSender){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, craftLegendsCoreConfig -> {
            if(!craftLegendsCoreConfig.systemEnabled) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesResetConfigs();
            BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
                craftLegendsCoreMessages.SendMessageToConsole(Messages.PlayerResetConfig);
            }, Throwable::printStackTrace);
        }, Throwable::printStackTrace);
    }

    @PCMethod
    @PCSignature({"configs", "reload"})
    public void BlueFusionCustomMessagesConfigsReloadMethod(CommandSender commandSender){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, craftLegendsCoreConfig -> {
            if(!craftLegendsCoreConfig.systemEnabled) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesResetConfigs();
            BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
                craftLegendsCoreMessages.SendMessageToConsole(Messages.PlayerReloadedConfig);
            }, Throwable::printStackTrace);
        }, Throwable::printStackTrace);
    }
}
