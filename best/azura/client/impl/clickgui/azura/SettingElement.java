package best.azura.client.impl.clickgui.azura;

import best.azura.client.api.value.Value;

public class SettingElement {

    public int x, y, width, height;
    public boolean hovered = false, visible, prev;
    public double animation = 0, visibilityAnimation = 0;
    public long visibilityStart = 0;
    public Value<?> value;

    public SettingElement(int x, int y, int width, int height, Value<?> value) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.value = value;
        this.visible = value.checkDependency();
        visibilityStart = System.currentTimeMillis();
    }

    public void checkVisibility() {
        visible = value.checkDependency();
        if(visible != prev) {
            visibilityStart = System.currentTimeMillis();
        }
        prev = visible;
    }

    public void render(int mouseX, int mouseY) {
        if(!visible) {
            float anim = Math.min(1, (System.currentTimeMillis()-visibilityStart)/500f);
            visibilityAnimation = -1 * Math.pow(anim-1, 6) + 1;
            visibilityAnimation = 1 - visibilityAnimation;
        } else {
            float anim = Math.min(1, (System.currentTimeMillis()-visibilityStart)/500f);
            visibilityAnimation = -1 * Math.pow(anim-1, 6) + 1;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {}
    public void mouseReleased(int mouseX, int mouseY) {}
    public void keyTyped(char typed, int keyCode) {}

}
