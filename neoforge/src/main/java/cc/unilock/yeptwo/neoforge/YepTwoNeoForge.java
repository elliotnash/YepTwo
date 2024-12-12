package cc.unilock.yeptwo.neoforge;

import cc.unilock.yeptwo.YepTwo;
import cc.unilock.yeptwo.networking.PacketSender;
import cc.unilock.yeptwo.networking.payload.YepPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(YepTwo.MOD_ID)
public class YepTwoNeoForge {
    public YepTwoNeoForge(IEventBus modBus) {
        YepTwo.init();
        NeoForge.EVENT_BUS.addListener(this::onAdvancement);
        NeoForge.EVENT_BUS.addListener(this::onDeath);
        modBus.addListener(this::onRegisterPayloadHandlers);
    }

    public void onAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
        PacketSender.sendAdvancementMessage(event.getEntity(), event.getAdvancement());
    }

    public void onDeath(LivingDeathEvent event) {
        PacketSender.sendDeathMessage(event.getEntity(), event.getSource());
    }

    public void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("0").optional().playToClient(YepPayload.TYPE, YepPayload.CODEC, (arg1, arg2) -> {});
    }
}
