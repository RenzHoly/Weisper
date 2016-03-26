package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/16.
 */
public class FloatingBarEvent {
    private float x, y;
    private boolean visible = false;

    public FloatingBarEvent(float x, float y) {
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public FloatingBarEvent() {
        this.visible = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }
}
