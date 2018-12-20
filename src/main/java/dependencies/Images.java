/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dependencies;

import java.awt.Image;
import engine.core.java.resources.ImageResource;
import static engine.core.java.resources.ImageResource.*;

/**
 *
 * @author estudiante
 */
public class Images {

    public static final Image LOGO;
    public static final Image POINTER;
    public static Image BACKGROUND;
    public static final Image FLOOR;
    public static final Image STEEL_BLOCK;
    public static final Image BOMB;
    public static final Image FIRE;
    public static final Image BOMBERMAN;
    public static final Image BALLOOM;
    public static final Image DOLL;
    public static final Image KONDORIA;
    public static final Image MINVO;
    public static final Image ONEAL;
    public static final Image PASS;
    public static final Image OVAPI;
    public static final Image PONTAN;
    public static final Image BRICK;
    public static final Image SPECIAL_BRICK;

    static {
        final var location = new Location();
        final var ir = new ImageResource();
        LOGO = ir.load(location.LOGO, VOLATILE);
        STEEL_BLOCK = ir.load(location.STEEL, VOLATILE);
        FLOOR = ir.load(location.FLOOR, VOLATILE);
        SPECIAL_BRICK = ir.load(location.SPECIAL_BRICK, VOLATILE);
        BRICK = ir.load(location.BRICK, VOLATILE);
        POINTER = ir.load(location.POINTER, VOLATILE);
        BOMB = ir.load(location.BOMB, VOLATILE);
        FIRE = ir.load(location.FIRE, VOLATILE);
        BOMBERMAN = ir.load(location.BOMBERMAN, VOLATILE);
        BALLOOM = ir.load(location.BALLOOM, VOLATILE);
        DOLL = ir.load(location.DOLL, VOLATILE);
        KONDORIA = ir.load(location.KONDORIA, VOLATILE);
        MINVO = ir.load(location.MINVO, VOLATILE);
        ONEAL = ir.load(location.ONEAL, VOLATILE);
        OVAPI = ir.load(location.OVAPI, VOLATILE);
        PASS = ir.load(location.PASS, VOLATILE);
        PONTAN = ir.load(location.PONTAN, VOLATILE);
    }

}
