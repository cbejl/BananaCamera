package cbejl.bananacamera;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class StartCameraCommand implements CommandExecutor {
    private int cameraLocationIndex = 0;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (cameraLocationIndex < BananaCamera.getLocationsList().size() - 1) {
                    cameraLocationIndex += 1;
                } else {
                    cameraLocationIndex = 0;
                }
                
            }
        }.runTaskLater(BananaCamera.getInstance(),
                (int) BananaCamera.getLocationsList().get(cameraLocationIndex).get("time") * 20);

        return false;
    }
}
