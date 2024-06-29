package cc.unilock.yeptwo.networking.payload;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record YepPayload(String msg) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, YepPayload> CODEC = CustomPacketPayload.codec(YepPayload::write, YepPayload::new);
    public static final Type<YepPayload> TYPE = new Type<>(ResourceLocation.tryBuild("yep", "generic"));

    public YepPayload(FriendlyByteBuf buf) {
        this(buf.readUtf());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(msg);
    }

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
