package team.emptyte.summonig.config.domain;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@SuppressWarnings("ALL")
public final class ConfigurationConfigurationFile implements ConfigurationFile {
  public int executorThreads = 2;
}
