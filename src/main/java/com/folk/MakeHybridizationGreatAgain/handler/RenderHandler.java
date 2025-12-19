package com.folk.MakeHybridizationGreatAgain.handler;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

import com.folk.MakeHybridizationGreatAgain.api.IRender;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class RenderHandler {

    public static final RenderHandler INSTANCE = new RenderHandler();
    private final RenderBlocks renderBlocks = RenderBlocks.getInstance();

    private RenderHandler() {}

    public static void register() {
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (TileEntity te : event.context.tileEntities) {
            if (isTargetMyMachine(te)) {
                IMetaTileEntity mte = ((IGregTechTileEntity) te).getMetaTileEntity();
                ((IRender) mte).onRenderWorldLast(te, event);
            }
        }
    }
    // public static int getChunkX(ChunkCache cache) {
    // try { return (Integer) chunkXField.get(cache); } catch (Exception e) { return 0; }
    // }
    //
    // /**
    // * 获取 ChunkCache 的区块 Z 坐标
    // */
    // public static int getChunkZ(ChunkCache cache) {
    // try { return (Integer) chunkZField.get(cache); } catch (Exception e) { return 0; }
    // }
    // @SubscribeEvent
    // public void onRenderBlockPre(RenderWorldEvent.Pre event){
    //
    // World world = event.renderer.worldObj;
    // ChunkCache cache = event.chunkCache;
    //
    // System.out.println(String.format("[RenderEventHandler] Searching for TileEntities in chunk [%d, %d]",
    // getChunkX(cache), getChunkZ(cache)));
    //
    // Collection<TileEntity> tileEntitiesInChunk = getTileEntitiesInChunk(world, cache);
    //
    // // 【路标2】检查是否找到了TileEntity
    // if (tileEntitiesInChunk == null || tileEntitiesInChunk.isEmpty()) {
    // System.out.println("[RenderEventHandler] -> No TileEntities found in this chunk.");
    //
    // }
    // for (TileEntity te : tileEntitiesInChunk) {
    // if (isTargetMyMachine(te)) {
    // System.out.println(String.format("[RenderEventHandler] -> Found a target machine at [%d, %d, %d]!", te.xCoord,
    // te.yCoord, te.zCoord));
    // // 计算头部坐标
    // int headX = te.xCoord;
    // int headY = te.yCoord + 1;
    // int headZ = te.zCoord;
    //
    // // 调用 Hijacker 方法执行修改
    // // Hijacker 内部会自己处理坐标获取和边界检查
    // removeBlockInWorld(world, headX, headY, headZ);
    // }
    // }
    // }
    // public static void removeBlockInWorld(World world, int x, int y, int z) {
    // boolean success = world.setBlockToAir(x, y, z);
    // System.out.println(String.format("[RenderHijacker] DIAGNOSIS: Attempted to set block to air in world at [%d, %d,
    // %d]. Success: %s", x, y, z, success));
    // }
    // public static void forceRerenderChunk(int x, int y, int z) {
    // try {
    // // 1. 获取 Minecraft 的主渲染器 RenderGlobal
    // RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
    //
    // // 2. 获取 RenderGlobal 中存储的所有区块渲染器
    // // 在 1.8 中，这个字段叫 worldRenderers，但它是一个数组，不是列表！
    // Field worldRenderersField = RenderGlobal.class.getDeclaredField("worldRenderers");
    // worldRenderersField.setAccessible(true);
    //
    // // 【关键修正】这里强制转换为数组 WorldRenderer[]
    // WorldRenderer[] worldRenderers = (WorldRenderer[]) worldRenderersField.get(renderGlobal);
    //
    // // 3. 遍历所有渲染器（数组），找到包含我们目标方块的哪一个
    // for (WorldRenderer renderer : worldRenderers) {
    // if (renderer == null) {
    // continue; // 安全检查
    // }
    //
    // // 获取这个渲染器所负责的区块坐标
    // Field posXField = renderer.getClass().getDeclaredField("posX");
    // Field posYField = renderer.getClass().getDeclaredField("posY");
    // Field posZField = renderer.getClass().getDeclaredField("posZ");
    // posXField.setAccessible(true);
    // posYField.setAccessible(true);
    // posZField.setAccessible(true);
    //
    // int rendererPosX = (Integer) posXField.get(renderer);
    // int rendererPosY = (Integer) posYField.get(renderer);
    // int rendererPosZ = (Integer) posZField.get(renderer);
    //
    // // 检查目标坐标是否在这个渲染器负责的范围内
    // if (x >= rendererPosX && x < rendererPosX + 16 &&
    // y >= rendererPosY && y < rendererPosY + 16 &&
    // z >= rendererPosZ && z < rendererPosZ + 16) {
    //
    // // 找到了！调用 markDirty() 方法标记它需要重绘
    // Method markDirtyMethod = renderer.getClass().getDeclaredMethod("markDirty");
    // markDirtyMethod.setAccessible(true);
    // markDirtyMethod.invoke(renderer);
    //
    // System.out.println("[RenderHijacker] SUCCESS: Forced chunk at [" + rendererPosX + ", " + rendererPosY + ", " +
    // rendererPosZ + "] to rerender.");
    // break; // 找到了就退出
    // }
    // }
    // } catch (Exception e) {
    // System.err.println("[RenderHijacker] ERROR: Failed to force chunk rerender.");
    // e.printStackTrace();
    // }
    // }

    // public static Collection<TileEntity> getTileEntitiesInChunk(World world, ChunkCache cache) {
    //
    // try {
    // int chunkX = (Integer) chunkXField.get(cache);
    // int chunkZ = (Integer) chunkZField.get(cache);
    // Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
    // if (chunk != null) {
    // // chunkTileEntityMap 是一个 Map，它的值就是 TileEntity
    // Map<?, ?> tileEntityMap = (Map<?, ?>) chunkTileEntityMapField.get(chunk);
    // return (Collection<TileEntity>) tileEntityMap.values();
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // // 如果出错或区块未加载，返回一个空集合
    // return Collections.emptyList();
    // }

    // private void renderRun(TileEntity te, RenderWorldLastEvent event) {
    // Minecraft mc = Minecraft.getMinecraft();
    // EntityPlayerSP player = mc.thePlayer;
    //
    //
    // double renderX = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
    // double renderY = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
    // double renderZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;
    //
    //
    // double baseX = te.xCoord;
    // double baseY = te.yCoord;
    // double baseZ = te.zCoord;
    // double centerX = te.xCoord + 0.5;
    // double centerY = te.yCoord + 1.5;
    // double centerZ = te.zCoord + 0.5;
    //
    // double radius = 2.0;
    //
    // long time = System.currentTimeMillis();
    // float angle = (time % 72000L) / 50.0F;
    // double radians = Math.toRadians(angle);
    // double worldX = centerX + radius * Math.cos(radians);
    // double worldZ = centerZ + radius * Math.sin(radians);
    // double worldY = centerY;
    //
    // Block blockToRender = Blocks.diamond_block;
    //
    //
    // mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
    //
    //
    // renderBlockAt(worldX, worldY, worldZ, blockToRender, renderX, renderY, renderZ);
    // }
    // private void renderBlockAt(double worldX, double worldY, double worldZ, Block blockToRender, double renderX,
    // double renderY, double renderZ) {
    //
    // double radius = 2.0;
    //
    // long time = System.currentTimeMillis();
    //
    // float angle = (time % 72000L) / 50.0F;
    // double radians = Math.toRadians(angle);
    // GL11.glPushMatrix();
    //
    // GL11.glTranslated(worldX - renderX, worldY - renderY, worldZ - renderZ);
    // GL11.glRotatef(angle * 2, 0.0F, 1.0F, 0.0F);
    //
    //
    // GL11.glScalef(0.5F, 0.5F, 0.5F);
    //
    // this.renderBlocks.renderBlockAsItem(blockToRender, 0, 1.0F);
    // GL11.glPopMatrix();
    // }

    // private static Field chunkArrayField;
    // private static Field chunkXField;
    // private static Field chunkZField;
    //
    // // --- 缓存 Chunk 的反射字段 ---
    // private static Field chunkStorageArraysField;
    // private static Field chunkTileEntityMapField;
    //
    // // --- 缓存 ExtendedBlockStorage 的反射字段 (【最终真相】) ---
    // private static Field blockLSBArrayField; // 存储低8位
    // private static Field blockMSBArrayField; // 存储高4位
    //
    // private static final Block TRANSPARENT_BLOCK = Blocks.air;
    // private static final int AIR_ID = Block.getIdFromBlock(TRANSPARENT_BLOCK); // 值为 0
    //
    // // 静态初始化块，一次性获取所有需要的字段
    // static {
    // try {
    // // ChunkCache 的字段
    // chunkArrayField = ChunkCache.class.getDeclaredField("chunkArray");
    // chunkArrayField.setAccessible(true);
    // chunkXField = ChunkCache.class.getDeclaredField("chunkX");
    // chunkXField.setAccessible(true);
    // chunkZField = ChunkCache.class.getDeclaredField("chunkZ");
    // chunkZField.setAccessible(true);
    //
    // // Chunk 的字段
    // chunkStorageArraysField = Chunk.class.getDeclaredField("storageArrays");
    // chunkStorageArraysField.setAccessible(true);
    // chunkTileEntityMapField = Chunk.class.getDeclaredField("chunkTileEntityMap");
    // chunkTileEntityMapField.setAccessible(true);
    //
    // // 【关键修正】ExtendedBlockStorage 的字段 (名称已确认)
    // blockLSBArrayField = ExtendedBlockStorage.class.getDeclaredField("blockLSBArray");
    // blockLSBArrayField.setAccessible(true);
    // blockMSBArrayField = ExtendedBlockStorage.class.getDeclaredField("blockMSBArray");
    // blockMSBArrayField.setAccessible(true);
    //
    // } catch (NoSuchFieldException e) {
    // // 如果还失败，那真的就是天意了
    // throw new RuntimeException("RenderHijacker failed to find reflection fields. All names are confirmed from your
    // source code.", e);
    // }
    // }
    //
    //
    //
    //
    // /**
    // * 【核心功能 - 最终版】在 ChunkCache 所代表的区域内，将指定坐标的方块替换为透明方块
    // */
    // public static void setBlockTransparentInCache(ChunkCache cache, int x, int y, int z) {
    // try {
    // // 1. 获取目标 Chunk 对象
    // Chunk[][] chunkArray = (Chunk[][]) chunkArrayField.get(cache);
    // int cacheChunkX = (Integer) chunkXField.get(cache);
    // int cacheChunkZ = (Integer) chunkZField.get(cache);
    // int indexX = (x >> 4) - cacheChunkX;
    // int indexZ = (z >> 4) - cacheChunkZ;
    //
    // if (indexX < 0 || indexX >= chunkArray.length || indexZ < 0 || indexZ >= chunkArray[indexX].length) {
    // return;
    // }
    // Chunk targetChunk = chunkArray[indexX][indexZ];
    // if (targetChunk == null) {
    // return;
    // }
    //
    // // 2. 获取目标 ExtendedBlockStorage 对象
    // ExtendedBlockStorage[] storageArrays = (ExtendedBlockStorage[]) chunkStorageArraysField.get(targetChunk);
    // int storageIndex = y >> 4;
    // if (storageIndex < 0 || storageIndex >= storageArrays.length || storageArrays[storageIndex] == null) {
    // return;
    // }
    // ExtendedBlockStorage storage = storageArrays[storageIndex];
    //
    // // 3. 【核心】获取 LSB 和 MSB 数组
    // byte[] blockLSBArray = (byte[]) blockLSBArrayField.get(storage);
    // NibbleArray blockMSBArray = (NibbleArray) blockMSBArrayField.get(storage);
    //
    // // 4. 计算在 16x16x16 立方体内的坐标和索引
    // int xInChunk = x & 15;
    // int yInChunk = y & 15;
    // int zInChunk = z & 15;
    // int arrayIndex = (yInChunk << 8) | (zInChunk << 4) | xInChunk;
    // int originalLSB = blockLSBArray[arrayIndex] & 255;
    // int originalMSB = (blockMSBArray != null) ? blockMSBArray.get(xInChunk, yInChunk, zInChunk) : 0;
    // int originalID = (originalMSB << 8) | originalLSB;
    // System.out.println(String.format("[RenderHijacker] Attempting to change block at [%d, %d, %d]. Original ID: %d",
    // x, y, z, originalID));
    //
    // // 5. 执行替换！
    // // 目标是空气方块 (ID=0), 所以 LSB=0, MSB=0
    // blockLSBArray[arrayIndex] = (byte) (AIR_ID & 255); // 设置低8位为0
    //
    // // 只有当MSB数组存在时才设置它
    // if (blockMSBArray != null) {
    // blockMSBArray.set(xInChunk, yInChunk, zInChunk, (AIR_ID >> 8) & 15); // 设置高4位为0
    // }
    // int newLSB = blockLSBArray[arrayIndex] & 255;
    // int newMSB = (blockMSBArray != null) ? blockMSBArray.get(xInChunk, yInChunk, zInChunk) : 0;
    // int newID = (newMSB << 8) | newLSB;
    // System.out.println(String.format("[RenderHijacker] Change successful. New ID: %d", newID));
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    private boolean isTargetMyMachine(TileEntity te) {
        if (!(te instanceof IGregTechTileEntity)) return false;
        IMetaTileEntity mte = ((IGregTechTileEntity) te).getMetaTileEntity();

        if (mte instanceof IRender) {

            return true;
        }
        return false;
    }
}
