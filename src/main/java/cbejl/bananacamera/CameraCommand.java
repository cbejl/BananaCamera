package cbejl.bananacamera;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CameraCommand implements CommandExecutor, TabCompleter {
    private BukkitTask task;
    private Player cameraPlayer;
    private int locationIndex = 0;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("bcamera.control") || !(commandSender instanceof Player)) {
            if (strings.length > 0) {
                if (strings[0].equals("run") && (task == null || task.isCancelled())) {
                    task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (BananaCamera.getInstance().getServer().getPlayer(BananaCamera.config.getString("cameraName")) != null) {
                                cameraPlayer = BananaCamera.getInstance().getServer()
                                        .getPlayer(BananaCamera.config.getString("cameraName"));
                            } else {
                                BananaCamera.getInstance().getLogger()
                                        .info(BananaCamera.getMessages().getString("camera.notfound"));
                                commandSender.sendMessage(Component.text(BananaCamera.getMessages().getString("camera.notfound")));
                                task.cancel();
                                return;
                            }

                            try {
                                assert cameraPlayer != null;

                                ArrayList rotation = (ArrayList) BananaCamera.getLocationsList().get(locationIndex).get("rotation");
                                ArrayList position = (ArrayList) BananaCamera.getLocationsList().get(locationIndex).get("xyz");
                                String world = (String) BananaCamera.getLocationsList().get(locationIndex).get("world");

                                cameraPlayer.setAllowFlight(true);
                                cameraPlayer.setGameMode(GameMode.CREATIVE);
                                cameraPlayer.setInvisible(true);
                                cameraPlayer.setFlying(true);
                                cameraPlayer.teleport(new Location(BananaCamera.getInstance().getServer().getWorld(world),
                                        ((Number)position.get(0)).doubleValue(), ((Number)position.get(1)).doubleValue(), ((Number)position.get(2)).doubleValue(),
                                        ((Number)rotation.get(0)).floatValue(), ((Number)rotation.get(1)).floatValue()));
                                cameraPlayer.setFlying(true);
                                cameraPlayer.showTitle(Title.title(
                                        Component.text((String) BananaCamera.getLocationsList().get(locationIndex).get("title")),
                                        Component.text((String) BananaCamera.getLocationsList().get(locationIndex).get("subtitle"))));
                                if (locationIndex < BananaCamera.getLocationsList().size() - 1) {
                                    locationIndex += 1;
                                } else {
                                    locationIndex = 0;
                                }

                            } catch (Exception e) {
                                BananaCamera.getInstance().getLogger()
                                        .info(BananaCamera.getMessages().getString("camera.notfound"));
                                task.cancel();
                                return;
                            }
                        }

                    }.runTaskTimer(BananaCamera.getInstance(), 0,
                            BananaCamera.config.getLong("period") * 20L);
                    BananaCamera.getInstance().getLogger().info(BananaCamera.getMessages().getString("camera.task.run"));
                    commandSender.sendMessage(Component.text(BananaCamera.getMessages().getString("camera.task.run")));
                    return true;
                } else if (strings[0].equals("run") && (task != null && !task.isCancelled())) {
                    BananaCamera.getInstance().getLogger().info(BananaCamera.getMessages().getString("camera.task.already.run"));
                    commandSender.sendMessage(Component.text(BananaCamera.getMessages().getString("camera.task.already.run")));
                    return false;
                } else if (strings[0].equals("stop")) {
                    if (task == null || task.isCancelled()) {
                        BananaCamera.getInstance().getLogger().info(BananaCamera.getMessages().getString("camera.task.already.stop"));
                        commandSender.sendMessage(Component.text(BananaCamera.getMessages().getString("camera.task.already.stop")));
                        return false;
                    } else if (task != null) {
                        task.cancel();
                        cameraPlayer.setAllowFlight(true);
                        cameraPlayer.setInvisible(false);
                        cameraPlayer.kick(Component.text(BananaCamera.getMessages().getString("camera.task.stop")));
                        BananaCamera.getInstance().getLogger().info(BananaCamera.getMessages().getString("camera.task.stop"));
                        commandSender.sendMessage(Component.text(BananaCamera.getMessages().getString("camera.task.stop")));
                        return true;
                    }
                } else if (strings[0].equals("reload")) {
                    BananaCamera.getInstance().createFiles();
                    BananaCamera.getInstance().getLogger().info(BananaCamera.getMessages().getString("camera.config.reload"));
                    commandSender.sendMessage(Component.text(BananaCamera.getMessages().getString("camera.config.reload")));
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ArrayList<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.add("run");
            list.add("stop");
            list.add("reload");
        }
        return list;
    }
}
