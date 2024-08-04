package team.emptyte.summonig.config.domain.type;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public enum ComponentTypeSerializer implements TypeSerializer<Component> {
  INSTANCE;

  @Override
  public Component deserialize(final @NotNull Type type, final @NotNull ConfigurationNode node) throws SerializationException {
    final String string = node.getString();
    if (string == null) {
      return Component.text("Missing component");
    }
    return MiniMessage.miniMessage().deserialize(string);
  }

  @Override
  public void serialize(final @NotNull Type type, final @Nullable Component obj, final @NotNull ConfigurationNode node) throws SerializationException {
    if (obj == null) {
      node.raw(null);
      return;
    }
    node.set(MiniMessage.miniMessage().serialize(obj));
  }
}
