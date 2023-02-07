package events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CharSpamListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        if (event.isAsynchronous() && !event.getPlayer().hasPermission("charspamnt.bypass")){
            String message = event.getMessage();
            char[] letters = message.toLowerCase().toCharArray();
            int count = 1;
            char last = letters[letters.length-1];

            for (int i = letters.length-2; i>=0; i--){
                if (letters[i] == last){
                    count++;
                }
                else{
                    count = 1;
                }

                if (count > 5) {
                    message = message.substring(0, i) + message.substring(i + 1);
                }
                last = letters[i];
            }
            event.setMessage(message);
        }
    }

}
