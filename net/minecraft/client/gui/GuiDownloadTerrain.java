package net.minecraft.client.gui;

import java.io.IOException;

import best.azura.client.impl.module.impl.other.ClientModule;
import best.azura.client.util.render.RenderUtil;
import best.azura.client.util.render.ShaderManager;
import best.azura.client.util.render.ShaderUtil;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class GuiDownloadTerrain extends GuiScreen
{
    private final NetHandlerPlayClient netHandlerPlayClient;
    private int progress;
    private final CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

    public GuiDownloadTerrain(NetHandlerPlayClient netHandler)
    {
        this.netHandlerPlayClient = netHandler;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.progress;

        if (this.progress % 20 == 0)
        {
            this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (this.customLoadingScreen != null)
        {
            this.customLoadingScreen.drawBackground(this.width, this.height);
        }
        else
        {
            this.drawBackground(0);
            final ShaderUtil shader = ShaderManager.shaderMap.get("Background");
            if (shader != null && ClientModule.backgroundShaders.getObject() && OpenGlHelper.shadersSupported && shader.isCompiledCorrectly())
                shader.render(mc, 0, 0);
            else {
                GlStateManager.resetColor();
                GlStateManager.color(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation("custom/background.png"));
                RenderUtil.INSTANCE.drawTexture(0, 0, width, height);
            }
        }

        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
