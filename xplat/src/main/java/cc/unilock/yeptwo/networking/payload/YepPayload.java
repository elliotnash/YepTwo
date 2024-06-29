package cc.unilock.yeptwo.networking.payload;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record YepPayload(FriendlyByteBuf buffer) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, YepPayload> CODEC = CustomPacketPayload.codec(YepPayload::write, YepPayload::new);
    public static final Type<YepPayload> TYPE = new Type<>(new ResourceLocation("yep", "generic"));

    public void write(FriendlyByteBuf buf) {
        buf.writeBytes(buffer.copy());
    }

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
