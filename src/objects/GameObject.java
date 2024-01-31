package objects;

import java.awt.geom.Rectangle2D;

public class GameObject {

    protected int x, y, objType;
    protected Rectangle2D.Float hitBox;
    protected boolean doAnimation, active=true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject() {
        
    }

}

