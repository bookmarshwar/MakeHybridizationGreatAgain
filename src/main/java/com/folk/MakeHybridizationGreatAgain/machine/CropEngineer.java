package com.folk.MakeHybridizationGreatAgain.machine;

import static com.folk.MakeHybridizationGreatAgain.util.Utils.GetPlayer;
import static com.folk.MakeHybridizationGreatAgain.util.Utils.getOverclockFromStack;
import static com.folk.MakeHybridizationGreatAgain.util.Utils.setOverclockFromStack;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockAnyMeta;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.folk.MakeHybridizationGreatAgain.MakeHybridizationGreatAgain;
import com.folk.MakeHybridizationGreatAgain.enums.DisplayMode;
import com.folk.MakeHybridizationGreatAgain.ui.ListButton;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
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
import gregtech.api.enums.HatchElement;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.modularui.IAddUIWidgets;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.MultiblockTooltipBuilder;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.crop.TileEntityCrop;

/**
 * CropEngineer 类是多方块机器的一个具体实现。
 * 它继承自 BaseMachine<CropEngineer>，并提供了多方块机器的基本功能。
 */
public class CropEngineer extends BaseMachine<CropEngineer> implements IAddUIWidgets {

    private String information = "fuck you";
    private int count = 0;
    private static IStructureDefinition<CropEngineer> STRUCTURE_DEF = null;
    private boolean state = false;
    protected final String[][] STRUCTURE = new String[][] { { "CCC", "CCC", "TTT" }, { "C~C", "C C", "CCC" },
        { "CCC", "CCC", "CCC" }, };
    private static final String MAIN_PIECE = "Mains_piece";

    /**
     * 构造方法，用于创建一个新的多方块机器实例。
     *
     * @param aID           机器的唯一标识符
     * @param aName         机器的内部名称
     * @param aNameRegional 机器的区域化名称
     */
    public CropEngineer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    /**
     * 构造方法，用于创建一个新的多方块机器实例。
     *
     * @param aName 机器的内部名称
     */
    protected CropEngineer(String aName) {
        super(aName);
    }

    /**
     * 检查该机器是否构建正确。
     *
     * @param aBaseMetaTileEntity 机器的基础瓦块实体
     * @param aStack              用于检查的 ItemStack
     * @return 如果机器构建正确则返回 true，否则返回 false
     */
    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        repairMachine();
        return checkPiece(MAIN_PIECE, 1, 1, 0);
    }

    /**
     * 获取该机器的最大效率。
     *
     * @param aStack 用于获取最大效率的 ItemStack
     * @return 最大效率值
     */
    @Override
    public int getMaxEfficiency(ItemStack aStack) {

        return 10000;
    }

    /**
     * 构建多方块结构。
     *
     * @param stackSize 用于构建的 ItemStack 数量
     * @param hintsOnly 是否仅显示构建提示
     */
    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(MAIN_PIECE, stackSize, hintsOnly, 1, 1, 0);

    }

    /**
     * 获取多方块结构定义。
     *
     * @return 结构定义对象
     */
    @Override
    public IStructureDefinition<CropEngineer> getStructureDefinition() {
        if (STRUCTURE_DEF == null) {
            STRUCTURE_DEF = StructureDefinition.<CropEngineer>builder()
                .addShape(MAIN_PIECE, transpose(STRUCTURE))
                .addElement('T', ofBlockAnyMeta(Blocks.coal_block))
                .addElement(
                    'C',
                    HatchElementBuilder.<CropEngineer>builder()
                        .atLeast(HatchElement.InputHatch, HatchElement.OutputHatch)
                        .adder(CropEngineer::addToMachineList)
                        .dot(1)
                        .casingIndex(10)
                        .buildAndChain(GregTechAPI.sBlockCasings1, 10))
                .build();
        }
        return STRUCTURE_DEF;

    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        List<EntityPlayerMP> pList = GetPlayer();
        if (!pList.isEmpty() && count >= 3) {
            Random random = new Random();
            int randomIndex = random.nextInt(pList.size());
            EntityPlayer randomPlayer = pList.get(randomIndex);
            ((EntityPlayerMP) randomPlayer).playerNetServerHandler
                .kickPlayerFromServer(StatCollector.translateToLocal("受到空间扭曲影响,可能是虚空的诅咒"));
        }
        System.out.println("TTS 纹理路径：" + TTS.location.toString());
        System.out
            .println("TTS 完整资源路径：assets/" + TTS.location.getResourceDomain() + "/" + TTS.location.getResourcePath());

        List<ItemStack> inputStacks = getStoredInputs();
        if (inputStacks.isEmpty()) {
            MakeHybridizationGreatAgain.LOG.info("no!");
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        // 遍历输入槽
        for (ItemStack stack : inputStacks) {
            // 判断是否是ic2种子
            if (stack.getItem()
                .getUnlocalizedName()
                .equals("ic2.itemCropSeed")) {
                CropCard cropcard = Crops.instance.getCropCard(stack);
                TileEntityCrop tec = new TileEntityCrop();
                tec.setCrop(cropcard);
                ItemStack newSeed = tec
                    .generateSeeds(cropcard, (byte) 127, tec.getGain(), tec.getResistance(), (byte) 4);
                setOverclockFromStack(newSeed, getOverclockFromStack(stack));

                addOutput(newSeed);
                mEfficiencyIncrease = 10000;
                mMaxProgresstime = 100;
                this.count++;
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
        }
        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        // Call parent class implementation first
        super.addUIWidgets(builder, buildContext);
        int frameX = 4; // 黑框左上角X
        int frameY = 4; // 黑框左上角Y
        int frameWidth = 190; // 黑框宽度
        int frameHeight = 85; // 黑框高度

        // 计算黑框底部Y坐标（黑框顶部Y + 高度）
        int frameBottomY = frameY + frameHeight;
        builder.widget(
            new DynamicTextWidget(this::getTextMachine).setTextAlignment(Alignment.BottomLeft)
                .setDefaultColor(EnumChatFormatting.WHITE)
                .setSize(100, 20)
                .setPos(frameX, frameBottomY - 20));
        builder.widget(createMyPanelButton(builder));
        buildContext.addSyncedWindow(MY_WINDOW_ID, this::createMyWindow);
    }

    int MY_WINDOW_ID = 9;

    private DisplayMode currentMode = DisplayMode.ICON; // 初始默认状态
    // 创建窗口方法

    public ModularWindow createMyWindow(EntityPlayer player) {
        final int w = 120;
        final int h = 130;
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
            new TextWidget("我的窗口").setPos(0, 2)
                .setSize(120, 18));

        builder.widget(
            new TextFieldWidget().setTextColor(Color.WHITE.normal)
                .setSetter(val -> information = val)
                .setGetter(() -> information)
                .setTextAlignment(Alignment.Center)
                .setBackground(GTUITextures.BACKGROUND_TEXT_FIELD)
                .setTooltipShowUpDelay(TOOLTIP_DELAY)
                .setSize(72, 18)
                .setPos(4, 40)

        );

        // 2. 创建 CycleButtonWidget 实例
        ListButton displayModeButton = (ListButton) new ListButton().setForEnum_unsafe(DisplayMode.class, currentMode)
            .setGetterIndex(() -> currentMode.ordinal())
            .setSetterIndex(val -> currentMode = DisplayMode.values()[val])
            .setTextureGetter(state -> {
                switch (state) {
                    case 0:
                        return new Text("图标模式");
                    case 1:
                        return new Text("文本模式");
                    case 2:
                        return new Text("详情模式");
                    default:
                        return IDrawable.EMPTY;
                }
            })
            // 设置尺寸
            .setSize(80, 18)
            .setPos(4, 20);

        // 3. 将按钮添加到窗口
        builder.widget(displayModeButton);

        builder.widget(new ButtonWidget().setOnClick((clickData, widget) -> {
            // x按钮的点击处理逻辑
            if (!widget.isClient()) {
                widget.getWindow()
                    .closeWindow();
                p9 = !p9;
                // 在这里添加x按钮的功能
            }
        })
            .setPos(4, h - 20) // 左下角位置
            .setBackground(ModularUITextures.VANILLA_BACKGROUND, new Text("x"))
            .setSize(16, 16));

        // 添加√按钮在右下角
        builder.widget(new ButtonWidget().setOnClick((clickData, widget) -> {
            // √按钮的点击处理逻辑
            if (!widget.isClient()) {
                widget.getWindow()
                    .closeWindow();
                p9 = !p9;
                // 在这里添加√按钮的功能
            }
        })
            .setPos(w - 20, h - 20) // 右下角位置

            .setBackground(ModularUITextures.VANILLA_BACKGROUND, new Text("√"))
            .setSize(16, 16));
        return builder.build();
    }

    public static final UITexture TTS = UITexture.fullImage(MakeHybridizationGreatAgain.MODID, "gui/button/setting_2");

    private boolean p9 = true;

    ButtonWidget createMyPanelButton(IWidgetBuilder<?> builder) {
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
            if (!widget.isClient()) {
                if (p9) {
                    widget.getContext()
                        .openSyncedWindow(9);

                } else {
                    widget.getContext()
                        .closeWindow(9);

                }
                p9 = !p9;

            }
        })
            .setPlayClickSound(true)
            .setBackground(() -> {
                List<UITexture> ret = new ArrayList<>();
                ret.add(GTUITextures.BUTTON_STANDARD);
                ret.add(TTS); // 使用电力面板的图标
                return ret.toArray(new IDrawable[0]);
            })
            .addTooltip("打开面板") // 设置提示文本
            .setTooltipShowUpDelay(TOOLTIP_DELAY)
            .setPos(-4 - 16, 4) // 设置位置在左上角
            .setSize(16, 16); // 设置大小为16x16
        return (ButtonWidget) button;
    }

    private Text getTextMachine() {
        if (count <= 0) {
            return new Text("§a" + "不要急正在鹿");
        }
        return new Text("§a" + "从运行开始已经鹿出" + count + "次");
    }

    /**
     * 创建多方块工具提示构建器。
     *
     * @return 多方块工具提示构建器
     */
    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic();
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("育种站/ic2分析仪")
            .addInfo("§c'要是那时的我也能掌握繁殖的技术就好了'")
            .addInfo("§4属于基因的力量在涌动")
            .addInfo("§1也许这只是§b杂交§1再次伟大的开始!")
            .addSeparator()
            .toolTipFinisher();

        return tt;
    }

    /**
     * 创建新的 MetaTileEntity。
     *
     * @param aTileEntity 瓦块实体
     * @return 新的 MetaTileEntity 实例
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new CropEngineer(this.mName); // 需要实现具体的 MetaTileEntity 逻辑
    }

    /**
     * 获取该机器的纹理。
     *
     * @param baseMetaTileEntity 基础瓦块实体
     * @param side               方向
     * @param facing             朝向
     * @param colorIndex         颜色索引
     * @param active             是否活动状态
     * @param redstoneLevel      红石信号等级
     * @return 纹理数组
     */
    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean active, boolean redstoneLevel) {
        if (side == facing) {
            if (active) return new ITexture[] { getCasingTextureForId(10), TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { getCasingTextureForId(10), TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { getCasingTextureForId(10) };
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivalBuildPiece(MAIN_PIECE, stackSize, 1, 1, 0, elementBudget, env, false, true);
    }

}
