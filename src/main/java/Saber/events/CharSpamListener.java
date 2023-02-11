package Saber.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import Saber.CharSpamnt;
import Saber.Utils.Shortener;

public class CharSpamListener implements Listener {

    private CharSpamnt plugin;

    public CharSpamListener (CharSpamnt plug){
        plugin = plug;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        //Pull config booleans
        boolean block = plugin.getConfig().getBoolean("blockCharSpam");
        boolean shorten = plugin.getConfig().getBoolean("shortenCharSpam");

        if ((block || shorten) &&
                event.isAsynchronous() &&
                !event.getPlayer().hasPermission("charspamnt.bypass")) {

            //Get the player's message and shorten any charspam
            String message = event.getMessage();
            int max = plugin.getConfig().getInt("maxCharacters");
            String ShortMessage = Shortener.ShortenCharSpam(message, max);

            //Check if any charspam was shortened
            if (message.length() != ShortMessage.length()){
                if (block){
                    //Stop the message
                    event.setCancelled(true);

                    //Send message to player informing of block
                    if (plugin.getConfig().getBoolean("sendMessageOnBlock")) {
                        String blockmessage = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("msgOnBlock"));
                        event.getPlayer().sendMessage(blockmessage);
                    }

                    //Send message to players with charspamnt.notify permission about block
                    if (plugin.getConfig().getBoolean("notifyOnBlock")) {
                        String notifymessage = plugin.getConfig().getString("notifyMsgOnBlock");

                        //Add player name and blocked message to notification
                        // {0} - Player Name
                        // {1} - Blocked Message
                        notifymessage = notifymessage.replace("{0}",event.getPlayer().getName());
                        notifymessage = notifymessage.replace("{1}",message);
                        notifymessage = ChatColor.translateAlternateColorCodes('&', notifymessage);
                        Bukkit.broadcast(notifymessage, "charspamnt.notify");
                    }
                }
                else if (shorten){
                    //Change message to shortened version
                    event.setMessage(ShortMessage);

                    //Send message to player informing of shorten
                    if (plugin.getConfig().getBoolean("sendMessageOnShorten")) {
                        String shortenmessage = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("msgOnShorten"));
                        event.getPlayer().sendMessage(shortenmessage);
                    }

                    //Send message to players with charspamnt.notify permission
                    if (plugin.getConfig().getBoolean("notifyOnShorten")) {
                        String notifymessage = plugin.getConfig().getString("notifyMsgShorten");

                        //Add player name and original message to notification
                        // {0} - Player Name
                        // {1} - Original Message
                        notifymessage = notifymessage.replace("{0}",event.getPlayer().getName());
                        notifymessage = notifymessage.replace("{1}",message);
                        notifymessage = ChatColor.translateAlternateColorCodes('&', notifymessage);
                        Bukkit.broadcast(notifymessage, "charspamnt.notify");
                    }

                }
            }

        }

    }

}
