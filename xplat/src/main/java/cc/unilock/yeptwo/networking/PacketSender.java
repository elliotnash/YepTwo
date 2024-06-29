package cc.unilock.yeptwo.networking;

import java.nio.charset.StandardCharsets;

import cc.unilock.yeptwo.YepTwo;
import cc.unilock.yeptwo.networking.payload.SimplePayload;
import io.netty.buffer.Unpooled;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

public class PacketSender {
    private static final ResourceLocation YEP_GENERIC = new ResourceLocation("yep", "generic");

    // id : username : displayname : advType : title : description
    private static final String YEP_ADV_FORMAT = "%s␞%s␟%s␟%s␟%s␟%s";
    // id : username : displayname : message
    private static final String YEP_DEATH_FORMAT = "%s␞%s␟%s␟%s";

    private static final ResourceLocation YEP_ADVANCEMENT = new ResourceLocation("yep", "advancement");
    private static final ResourceLocation YEP_DEATH = new ResourceLocation("yep", "death");

    private static final String YEP_ADV_DEFAULT = "DEFAULT";
    private static final String YEP_ADV_GOAL = "GOAL";
    private static final String YEP_ADV_TASK = "TASK";
    private static final String YEP_ADV_CHALLENGE = "CHALLENGE";


    public static void sendAdvancementMessage(Player player, AdvancementHolder advancement) {
        if (player instanceof ServerPlayer spe) {
            var display = advancement.value().display().orElse(null);

            if (spe.getAdvancements().getOrStartProgress(advancement).isDone()
                    && display != null
                    && display.shouldAnnounceChat()
                    && spe.level().getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS)
            ) {
                var username = spe.getName().getString();
                var displayname = spe.getDisplayName().getString();
                var title = display.getTitle().getString();
                var description = display.getDescription().getString();

                String advType = switch (display.getType()) {
                    case CHALLENGE -> YEP_ADV_CHALLENGE;
                    case GOAL -> YEP_ADV_GOAL;
                    case TASK -> YEP_ADV_TASK;
                    default -> YEP_ADV_DEFAULT;
                };

                String msg = String.format(YEP_ADV_FORMAT, YEP_ADVANCEMENT, username, displayname, advType, title, description);
                sendMessage(spe, msg);
            }
        }
    }

    public static void sendDeathMessage(Entity entity, DamageSource source) {
        if (entity instanceof ServerPlayer spe) {
            var username = spe.getName().getString();
            var displayname = spe.getDisplayName().getString();
            var message = source.getLocalizedDeathMessage(spe).getString();

            String msg = String.format(YEP_DEATH_FORMAT, YEP_DEATH, username, displayname, message);
            sendMessage(spe, msg);
        }
    }

    private static void sendMessage(ServerPlayer player, String msg) {
        YepTwo.LOGGER.debug("Sending message \""+msg+"\" for player \""+player.getName().getString()+"\"");

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBytes(msg.getBytes(StandardCharsets.UTF_8));
        player.connection.send(new ClientboundCustomPayloadPacket(new SimplePayload(YEP_GENERIC, buf)));
    }
}
