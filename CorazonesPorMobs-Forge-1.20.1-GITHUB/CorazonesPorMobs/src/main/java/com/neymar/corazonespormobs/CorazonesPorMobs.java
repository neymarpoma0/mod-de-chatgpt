package com.neymar.corazonespormobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(CorazonesPorMobs.MOD_ID)
public class CorazonesPorMobs {
    public static final String MOD_ID = "corazonespormobs";
    private static final String BONUS_HEALTH_KEY = "corazonespormobs_bonus_health";
    private static final double DEFAULT_MAX_HEALTH = 20.0D;

    public CorazonesPorMobs() { }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class Events {
        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
            LivingEntity defeated = event.getEntity();
            if (defeated instanceof Player) return;
            if (defeated.level().isClientSide) return;

            AttributeInstance defeatedHealth = defeated.getAttribute(Attributes.MAX_HEALTH);
            AttributeInstance playerHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (defeatedHealth == null || playerHealth == null) return;

            // Minecraft health is measured in points: 2 points = 1 heart.
            double gainedHealth = Math.max(0.0D, defeatedHealth.getValue());
            if (gainedHealth <= 0.0D) return;

            CompoundTag data = player.getPersistentData();
            double bonus = data.getDouble(BONUS_HEALTH_KEY) + gainedHealth;
            data.putDouble(BONUS_HEALTH_KEY, bonus);
            applyBonus(player);

            // Also restore the same amount of current health, up to the new maximum.
            player.setHealth((float) Math.min(player.getMaxHealth(), player.getHealth() + gainedHealth));
        }

        @SubscribeEvent
        public static void onLogin(PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) applyBonus(player);
        }

        @SubscribeEvent
        public static void onRespawn(PlayerRespawnEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) applyBonus(player);
        }

        @SubscribeEvent
        public static void onClone(PlayerEvent.Clone event) {
            CompoundTag oldData = event.getOriginal().getPersistentData();
            CompoundTag newData = event.getEntity().getPersistentData();
            if (oldData.contains(BONUS_HEALTH_KEY)) {
                newData.putDouble(BONUS_HEALTH_KEY, oldData.getDouble(BONUS_HEALTH_KEY));
            }
        }

        private static void applyBonus(ServerPlayer player) {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth == null) return;
            double bonus = Math.max(0.0D, player.getPersistentData().getDouble(BONUS_HEALTH_KEY));
            maxHealth.setBaseValue(DEFAULT_MAX_HEALTH + bonus);
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }
}
