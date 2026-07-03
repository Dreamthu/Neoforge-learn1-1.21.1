package net.traum.learn1mod.item.custom;

import net.traum.learn1mod.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

public class ModArmorItem extends ArmorItem {
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            Map.of(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL,
                    List.of(new MobEffectInstance(MobEffects.JUMP, 20, 1, false, false),
                            new MobEffectInstance(MobEffects.GLOWING, 20, 1, false, false)));

    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player && !level.isClientSide()) {
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (var entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            if (hasCorrectArmorOn(entry.getKey(), player)) {
                addEffectToPlayer(player, entry.getValue());
            }
        }
    }

    private void addEffectToPlayer(Player player, List<MobEffectInstance> effects) {
        for (MobEffectInstance effect : effects) {
            if (!player.hasEffect(effect.getEffect())) {
                effects.forEach(e -> player.addEffect(new MobEffectInstance(e)));
                return;
            }
        }
    }

    private boolean hasCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (!(stack.getItem() instanceof ArmorItem armorItem)
                    || armorItem.getMaterial() != material) {
                return false;
            }
        }
        return true;
    }
}