package com.example.xray.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Mixin(Block.class)
public class MixinBlock {

    private static final Set<String> HIDDEN = new HashSet<>(Arrays.asList(
            "minecraft:stone",
            "minecraft:diorite",
            "minecraft:andesite",
            "minecraft:cobblestone"
    ));

    @Inject(method = "isOpaqueCube", at = @At("HEAD"), cancellable = true)
    private void xray$isOpaqueCube(IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (isHidden(state)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isFullCube", at = @At("HEAD"), cancellable = true)
    private void xray$isFullCube(IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (isHidden(state)) {
            cir.setReturnValue(false);
        }
    }

    private static boolean isHidden(IBlockState state) {
        Block block = state.getBlock();
        return block.getRegistryName() != null
                && HIDDEN.contains(block.getRegistryName().toString());
    }
}
