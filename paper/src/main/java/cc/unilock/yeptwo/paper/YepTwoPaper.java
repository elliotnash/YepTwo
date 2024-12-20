package cc.unilock.yeptwo.paper;

import cc.unilock.yeptwo.YepTwo;
import cc.unilock.yeptwo.networking.PacketSender;
import cc.unilock.yeptwo.networking.payload.YepPayload;
import net.kyori.adventure.text.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//public class YepTwoPaper implements ModInitializer {
//    @Override
//    public void onInitialize() {
//        YepTwo.init();
//        ServerLivingEntityEvents.AFTER_DEATH.register(PacketSender::sendDeathMessage);
//        PayloadTypeRegistry.playS2C().register(YepPayload.TYPE, YepPayload.CODEC);
//    }
//}

public class YepTwoPaper extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        YepTwo.init();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Convert bukkit player to nms player
        Player playerEntity = event.getEntity();
        Method playerGetHandleMethod = playerEntity.getClass().getMethod("getHandle");
        Entity entity = (Entity) playerGetHandleMethod.invoke(playerEntity);

        org.bukkit.damage.DamageSource craftSource = event.getDamageSource();
        Method damageGetHandleMethod = craftSource.getClass().getMethod("getHandle");
        DamageSource source = (DamageSource) damageGetHandleMethod.invoke(craftSource);

        PacketSender.sendDeathMessage(entity, source);
    }

//    @EventHandler
//    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
//        PacketSender.sendAdvancementMessage((net.minecraft.world.entity.player.Player) event.getPlayer(), event.getAdvancement());
//    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }

}
