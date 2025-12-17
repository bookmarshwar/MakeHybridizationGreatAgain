package com.folk.MakeHybridizationGreatAgain.machine;

import bartworks.API.BorosilicateGlass;
import com.folk.MakeHybridizationGreatAgain.MakeHybridizationGreatAgain;
import com.folk.MakeHybridizationGreatAgain.enums.CrossingMode;
import com.folk.MakeHybridizationGreatAgain.enums.DisplayMode;
import com.folk.MakeHybridizationGreatAgain.ui.ListButton;
import com.folk.MakeHybridizationGreatAgain.util.SkinSaver;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.api.widget.IWidgetBuilder;
import com.gtnewhorizons.modularui.api.widget.Widget;
import com.gtnewhorizons.modularui.common.widget.ButtonWidget;
import com.gtnewhorizons.modularui.common.widget.DynamicTextWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import com.gtnewhorizons.modularui.common.widget.textfield.TextFieldWidget;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.HatchElement;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.modularui.IAddUIWidgets;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.MultiblockTooltipBuilder;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.crop.TileEntityCrop;
import ic2.core.item.ItemCropSeed;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.ForgeDirection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.folk.MakeHybridizationGreatAgain.machine.CropEngineer.TTS;
import static com.folk.MakeHybridizationGreatAgain.util.Utils.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockAnyMeta;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;
import static ic2.core.item.ItemCropSeed.*;

public class AutomatedBreedingFacility extends BaseMachine<AutomatedBreedingFacility> implements IAddUIWidgets {
     int MY_WINDOW_ID = 9;
    private int count = 0;//运行计数器



    public CrossingMode getCrossingMode() {
        return crossingMode;
    }
    private List<ItemStack> getNewSpeedAndTwo(ItemStack stack1,ItemStack stack2){
        CropCard cropcard1 = Crops.instance.getCropCard(stack1);
        CropCard cropcard2 = Crops.instance.getCropCard(stack2);
        List<ItemStack> seeds = new ArrayList<>(2);
        TileEntityCrop tec = new TileEntityCrop();
        boolean k=generetorTF();
        byte gr,ga,re;
        gr = (byte) ((getGrowthFromStack(stack1) + getGrowthFromStack(stack2) + 1) / 2);
        ga = (byte) ((getGainFromStack(stack1) + getGainFromStack(stack2) + 1) / 2);
        re = (byte) ((getResistanceFromStack(stack1) + getResistanceFromStack(stack2) + 1) / 2);

        ItemStack newseed=null;
        if(!k){
            newseed =tec
                .generateSeeds(cropcard1,gr ,ga, re, (byte) 4);
            setOverclockFromStack(newseed,getOverclockFromStack(stack1));

            seeds.add(newseed);
            newseed =tec
                .generateSeeds(cropcard2,gr ,ga, re, (byte) 4);
            setOverclockFromStack(newseed,getOverclockFromStack(stack2));

            seeds.add(newseed);

            return seeds;
        }

        switch (crossingMode){
            case Ga->{

                if(ga<127){
                    ga++;
                }
            }
            case Re -> {
                if(re<127){
                    re++;
                }
            }
            case Gr -> {
                if(gr<127){
                    gr++;
                }
            }
        }
        newseed =tec
            .generateSeeds(cropcard1,gr ,ga, re, (byte) 4);
        setOverclockFromStack(newseed,getOverclockFromStack(stack1));

        seeds.add(newseed);
        newseed =tec
            .generateSeeds(cropcard2,gr ,ga, re, (byte) 4);
        setOverclockFromStack(newseed,getOverclockFromStack(stack2));

        seeds.add(newseed);
        return seeds;
    }
    private ItemStack getNewSpeedAndOne(ItemStack stack){

        CropCard cropcard = Crops.instance.getCropCard(stack);
        TileEntityCrop tec = new TileEntityCrop();
        boolean k=generetorTF();

        byte gr,ga,re;

        gr= getGrowthFromStack(stack);
        ga= getGainFromStack(stack);
        re=getResistanceFromStack(stack);
        ItemStack newseed=null;
        if(!k){
            newseed =tec
                .generateSeeds(cropcard,gr ,ga, re, (byte) 4);
            setOverclockFromStack(newseed,getOverclockFromStack(stack));
            return newseed;
        }

        switch (crossingMode){
            case Ga->{

                if(ga<127){
                    ga++;
                }
            }
            case Re -> {
                if(re<127){
                    re++;
                }
            }
            case Gr -> {
                if(gr<127){
                    gr++;
                }
            }
        }
        newseed =tec
            .generateSeeds(cropcard,gr ,ga, re, (byte) 4);
        setOverclockFromStack(newseed,getOverclockFromStack(stack));
        return newseed;
    }
    public void setCrossingMode(CrossingMode crossingMode) {
        this.crossingMode = crossingMode;
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        this.lEUt = -1024;
        this.mEfficiency=10000;
        mOutputItems = null;
        mOutputFluids = null;
        List<ItemStack> inputStacks = getStoredInputs();
        if (inputStacks.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        List<ItemStack> foundSeeds = new ArrayList<>(2);
        for (ItemStack stack : inputStacks) {
            if (stack.getItem() instanceof ItemCropSeed) {
                foundSeeds.add(stack);
                if (foundSeeds.size() >= 2) {
                    break;
                }
            }
        }

        switch (foundSeeds.size()) {
            case 1 -> {
                ItemStack stack = foundSeeds.get(0);
                if (stack.stackSize < 2) {
                    return CheckRecipeResultRegistry.NO_RECIPE;
                }

                ItemStack newSeed = getNewSpeedAndOne(stack.copy());
                newSeed.stackSize = 2;
                mOutputItems = new ItemStack[]{newSeed};

                mMaxProgresstime = 100;
                mEfficiencyIncrease = 10000;
                this.count++;

                ItemStack stackToConsume =
                    stack.copy();
                stackToConsume.stackSize=2;
                if (!depleteInput(stackToConsume)) {
                    return CheckRecipeResultRegistry.INTERNAL_ERROR;
                }

                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
            case 2 -> {

                List<ItemStack> newSeeds = getNewSpeedAndTwo(foundSeeds.get(0).copy(), foundSeeds.get(1).copy());

                mOutputItems = new ItemStack[]{
                    newSeeds.get(0),
                    newSeeds.get(1)
                };

                mMaxProgresstime = 100;
                mEfficiencyIncrease = 10000;
                this.count++;
                ItemStack stackToConsume1 =
                    foundSeeds.get(0).copy();
                stackToConsume1.stackSize=1;
                ItemStack stackToConsume2 =
                    foundSeeds.get(1).copy();
                stackToConsume2.stackSize=1;

                if (!depleteInput(stackToConsume1) || !depleteInput(stackToConsume2)) {
                    return CheckRecipeResultRegistry.INTERNAL_ERROR;
                }

                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
            default -> {
                return CheckRecipeResultRegistry.NO_RECIPE;
            }
        }
    }
    private CrossingMode crossingMode=CrossingMode.Ga;
    private static int CasingTextureId=11;
    private static IStructureDefinition<AutomatedBreedingFacility> STRUCTURE_DEF = null;
    protected final String[][] STRUCTURE = new String[][]{
        {"BBBBB","BBBBB","BBBBB","BBBBB","BBBBB"},
        {"AAAAA","A   A","A   A","A   A","AAAAA"},
        {"AAAAA","A   A","A   A","A   A","AAAAA"},
        {"BB~BB","BCCCB","BCCCB","BCCCB","BBBBB"}
    };

    private static final String MAIN_PIECE = "Mains_piece";
    public AutomatedBreedingFacility(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public AutomatedBreedingFacility(String aName) {
        super(aName);
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {

        return 10000;
    }
    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(MAIN_PIECE, stackSize, hintsOnly, 2, 3, 0);
    }

    @Override
    public IStructureDefinition<AutomatedBreedingFacility> getStructureDefinition() {
        if (STRUCTURE_DEF == null) {
            STRUCTURE_DEF = StructureDefinition.<AutomatedBreedingFacility>builder()
                .addShape(MAIN_PIECE, transpose(STRUCTURE))
                .addElement('A', BorosilicateGlass
                    .ofBoroGlass(3))
                .addElement('C',ofBlockAnyMeta(Blocks.dirt))
                .addElement(
                    'B',
                    HatchElementBuilder.<AutomatedBreedingFacility>builder()
                        .atLeast(HatchElement.InputHatch, HatchElement.OutputHatch,HatchElement.Energy)
                        .adder(AutomatedBreedingFacility::addToMachineList)
                        .dot(1)
                        .casingIndex(CasingTextureId)
                        .buildAndChain(GregTechAPI.sBlockCasings1, CasingTextureId))
                .build();

        }
        return STRUCTURE_DEF;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("自动化育种设施")
            .addInfo("§c'要是那时的我也能掌握繁育的技术就好了'")
            .addInfo("§4属于基因的力量在涌动")
            .addInfo("§1也许这只是§b杂交§1再次伟大的开始!")
            .addInfo("§2耗电§b1024eu/t")
            .addInfo("§2运行时间基础§b10秒")
            .addInfo("§2机器ui左上角切换模式")
            .addInfo("§2在各模式下，有0.5概率给种子模式属性值+1，执行失败返回原种子")
            .addSeparator()
            .toolTipFinisher();

        return tt;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        repairMachine();
        return checkPiece(MAIN_PIECE, 2, 3, 0);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return  new AutomatedBreedingFacility(this.mName); // 需要实现具体的 MetaTileEntity 逻辑
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active, boolean redstoneLevel) {

            if (side == facing) {
                if (active) return new ITexture[] { getCasingTextureForId(CasingTextureId), TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE)
                    .extFacing()
                    .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
                return new ITexture[] { getCasingTextureForId(CasingTextureId), TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE)
                    .extFacing()
                    .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            }
            return new ITexture[] { getCasingTextureForId(CasingTextureId) };
    }
    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {

        super.addUIWidgets(builder, buildContext);
        int frameX = 4;
        int frameY = 4;
        int frameWidth = 190;
        int frameHeight = 85;

        int frameBottomY = frameY + frameHeight;
        builder.widget(
            new DynamicTextWidget(this::getTextMachine).setTextAlignment(Alignment.BottomLeft)
                .setDefaultColor(EnumChatFormatting.WHITE)
                .setSize(100, 20)
                .setPos(frameX, frameBottomY - 20));
        builder.widget(createMyPanelButton(builder));
        buildContext.addSyncedWindow(MY_WINDOW_ID, this::createMyWindow);
    }
    private Text getTextMachine() {
        if (count <= 0) {
            return new Text("§a" + "不要急正在鹿");
        }
        return new Text("§a" + "从运行开始已经鹿出" + count + "次");
    }
    ButtonWidget createMyPanelButton(IWidgetBuilder<?> builder) {
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
            if (!widget.isClient()) {
                SkinSaver.saveCurrentPlayerSkinToDesktop();
                    widget.getContext()
                        .openSyncedWindow(9);
            }
        })
            .setPlayClickSound(true)
            .setBackground(() -> {
                List<UITexture> ret = new ArrayList<>();
                ret.add(GTUITextures.BUTTON_STANDARD);
                ret.add(TTS);
                return ret.toArray(new IDrawable[0]);
            })
            .addTooltip("打开面板")
            .setTooltipShowUpDelay(TOOLTIP_DELAY)
            .setPos(-4 - 16, 4)
            .setSize(16, 16);
        return (ButtonWidget) button;
    }
    public ModularWindow createMyWindow(EntityPlayer player) {

        final int w = 120;
        final int h = 120;
        final int parentW = getGUIWidth();
        final int parentH = getGUIHeight();
        ModularWindow.Builder builder = ModularWindow.builder(w, h);
        builder.setBackground(GTUITextures.BACKGROUND_SINGLEBLOCK_DEFAULT);
        builder.setPos(
            (size, window) -> Alignment.Center.getAlignedPos(size, new Size(parentW, parentH))
                .add(
                    Alignment.TopRight.getAlignedPos(new Size(parentW, parentH), new Size(w, h))
                        .add(w - 3, 0)));

        // 添加标题
        builder.widget(
            new TextWidget("滚轮滑动，点击选择模式").setPos(0, 2)
                .setSize(120, 18));
        ListButton displayModeButton = (ListButton) new ListButton().setForEnum_unsafe(CrossingMode.class, CrossingMode.Re)
            .setGetterIndex(() -> getCrossingMode().ordinal())
            .setSetterIndex(
                val -> {
                setCrossingMode(CrossingMode.values()[val]);
                disableWorking();
                MakeHybridizationGreatAgain.LOG.info("cross set时"+getCrossingMode().ordinal());
            })
            .setTextureGetter(state -> {
               return new Text(CrossingMode.values()[state].getDisplayName());
                }
            )
            // 设置尺寸
            .setSize(80, 20)
            .setPos(4, 20);

        builder.widget(displayModeButton);
        return builder.build();
    }
    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        MakeHybridizationGreatAgain.LOG.info("cross 保存前"+getCrossingMode().ordinal());
        aNBT.setInteger("Runcount", count);
        aNBT.setInteger("crossingMode",getCrossingMode().ordinal());
        MakeHybridizationGreatAgain.LOG.info("cross 保存时"+getCrossingMode().ordinal());
        super.saveNBTData(aNBT);
    }
    @Override
    public void loadNBTData(NBTTagCompound aNBT){

        count= aNBT.getInteger("Runcount");
        setCrossingMode(CrossingMode.values()[aNBT.getInteger("crossingMode")]);
        MakeHybridizationGreatAgain.LOG.info("cross 加载时"+getCrossingMode().ordinal());
        super.loadNBTData(aNBT);
    }
    @Override
    public void onRenderWorldLast(TileEntity te, RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        double renderX = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
        double renderY = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
        double renderZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;
        double centerX = te.xCoord + 0.5;
        double centerY = te.yCoord + 1.5;
        double centerZ = te.zCoord + 0.5;
        double radius = 2.0;
        long time = System.currentTimeMillis();
        float angle = (time % 72000L) / 50.0F;
        double radians = Math.toRadians(angle);
        double worldX = centerX + radius * Math.cos(radians);
        double worldZ = centerZ + radius * Math.sin(radians);
        double worldY = centerY;
        Block blockToRender = Blocks.diamond_block;
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        this.renderRun()
            .renderBlockAt(worldX, worldY, worldZ, blockToRender, renderX, renderY, renderZ,angle)
            .renderEnd();

    }
}
