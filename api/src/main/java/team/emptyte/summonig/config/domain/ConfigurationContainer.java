package team.emptyte.summonig.config.domain;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public final class ConfigurationContainer<ConfigType extends ConfigurationFile> {
  private ConfigType configuration;
  private final ConfigurationLoader<?> loader;
  private final Class<ConfigType> clazz;

  public ConfigurationContainer(final @NotNull ConfigurationLoader<?> loader, final @NotNull Class<ConfigType> clazz) {
    this.loader = loader;
    this.clazz = clazz;

    try {
      this.configuration = this.load();
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }

  public @NotNull ConfigType configuration() {
    return this.configuration;
  }

  private @NotNull ConfigType load() throws ConfigurateException {
    final ConfigurationNode node = this.loader.load();
    final ConfigType config = node.get(this.clazz);
    if (config == null) {
      throw new ConfigurateException(node, "Failed to load configuration");
    }
    node.set(this.clazz, config);
    this.loader.save(node);
    return config;
  }

  public @NotNull CompletableFuture<Void> reload() {
    return CompletableFuture.runAsync(() -> {
      try {
        this.configuration = this.load();
      } catch (final Exception e) {
        throw new CompletionException(e);
      }
    });
  }

  public @NotNull CompletableFuture<Void> save() {
    return CompletableFuture.runAsync(() -> {
      try {
        final ConfigurationNode node = this.loader.createNode();
        node.set(this.clazz, this.configuration);
        this.loader.save(node);
      } catch (final Exception e) {
        throw new CompletionException(e);
      }
    });
  }
}
