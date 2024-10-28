package cc.unilock.yeptwo.fabric;

import cc.unilock.yeptwo.YepTwo;
import cc.unilock.yeptwo.networking.PacketSender;
import cc.unilock.yeptwo.networking.payload.YepPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class YepTwoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        YepTwo.init();
        ServerLivingEntityEvents.AFTER_DEATH.register(PacketSender::sendDeathMessage);
        PayloadTypeRegistry.playS2C().register(YepPayload.TYPE, YepPayload.CODEC);
    }
}
