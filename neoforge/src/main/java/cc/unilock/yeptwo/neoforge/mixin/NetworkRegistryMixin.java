package cc.unilock.yeptwo.neoforge.mixin;

import cc.unilock.yeptwo.networking.payload.YepPayload;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerCommonPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetworkRegistry.class, remap = false)
public class NetworkRegistryMixin {
	@Inject(method = "checkPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/protocol/common/ServerCommonPacketListener;)V", at = @At(value = "FIELD", target = "Lnet/neoforged/neoforge/network/registration/NetworkRegistry;BUILTIN_PAYLOADS:Ljava/util/Map;", opcode = Opcodes.GETSTATIC), cancellable = true)
	private static void checkPacket(Packet<?> packet, ServerCommonPacketListener listener, CallbackInfo ci, @Local ResourceLocation id) {
		if (YepPayload.ID.equals(id)) {
			ci.cancel();
		}
	}
}
