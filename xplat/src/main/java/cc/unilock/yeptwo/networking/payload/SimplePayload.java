package cc.unilock.yeptwo.networking.payload;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SimplePayload(ResourceLocation id, FriendlyByteBuf buffer) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, SimplePayload> CODEC = CustomPacketPayload.codec(SimplePayload::write, SimplePayload::new);

    private SimplePayload(FriendlyByteBuf buf) {
        this(buf.readResourceLocation(), buf);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(id);
        buf.writeBytes(buffer.copy());
    }

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return new Type<>(id);
    }
}
