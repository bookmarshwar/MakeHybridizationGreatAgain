package com.folk.MakeHybridizationGreatAgain.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

public class SkinSaver {
    public static void saveCurrentPlayerSkinToDesktop() {
        AbstractClientPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            System.out.println("玩家对象为空，无法获取皮肤");
            return;
        }

        BufferedImage skin = getPlayerSkin(player);
        if (skin == null) {
            System.out.println("未能获取到皮肤");
            return;
        }

        File desktop = new File(System.getProperty("user.home"), "Desktop");
        File outputFile = new File(desktop, "player_skin.png");

        try {
            ImageIO.write(skin, "png", outputFile);
            System.out.println("皮肤已保存到: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("保存皮肤失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 获取皮肤（优先内存，再缓存）
    private static BufferedImage getPlayerSkin(AbstractClientPlayer player) {
        BufferedImage skin = getSkinFromMemory(player);
        if (skin == null) {
            skin = getSkinFromCache(player);
        }
        return skin;
    }

    // 从游戏内存获取皮肤（1.7.10 适配）
    private static BufferedImage getSkinFromMemory(AbstractClientPlayer player) {
        try {
            ResourceLocation skinLocation = player.getLocationSkin();
            Object texture = Minecraft.getMinecraft().getTextureManager().getTexture(skinLocation);
            if (texture instanceof net.minecraft.client.renderer.ThreadDownloadImageData) {
                Field bufferField = net.minecraft.client.renderer.ThreadDownloadImageData.class.getDeclaredField("bufferedImage");
                bufferField.setAccessible(true);
                return (BufferedImage) bufferField.get(texture);
            }
        } catch (Exception e) {
            System.out.println("从内存获取皮肤失败: " + e.getMessage());
        }
        return null;
    }

    // 从缓存目录获取皮肤
    private static BufferedImage getSkinFromCache(AbstractClientPlayer player) {
        try {
            String uuid = player.getUniqueID().toString().replace("-", "");
            String hash = DigestUtils.sha256Hex(uuid);
            File skinFile = new File(Minecraft.getMinecraft().mcDataDir, "assets/skins/4c/" + hash);
            if (skinFile.exists()) {
                return ImageIO.read(skinFile);
            }
        } catch (Exception e) {
            System.out.println("从缓存获取皮肤失败: " + e.getMessage());
        }
        return null;
    }
}
