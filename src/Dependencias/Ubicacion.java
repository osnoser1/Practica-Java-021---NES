/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dependencias;
import java.util.ArrayList;
/**
 *
 * @author Alfonso Andrés
 */
public final class Ubicacion {
    
    
    public static String TITLE_SCREEN = "/Title_Screen.wav";
    public static String LEVEL_START = "/Level_Start.wav";
    public static String STAGE_THEME = "/Stage_Theme.wav";
    public static String FIND_THE_DOOR = "/Find_The_Door.wav";
    public static final String LOGO = "/Ambiente/Logo.png";
    public static final String APUNTADOR = "/Ambiente/Apuntador.png";
    public static String PISO = "/Ambiente/Piso.png";
    public static String FONDO = "/Ambiente/Fondo.png";
    public static String ACERO = "/Ambiente/Bloque_Solido.png";
    public static final String PRESENTACION = "/Presentacion.wav";
    public static final String BONUS_STAGE = "/Bonus_Stage.wav";
    public static final String STAGE = "/Stage.wav";
    public static final String GAME_OVER = "/Game_Over.wav";
    public static final String INVINCIBILITY_THEME = "/Invincibility_Theme.wav";
    public static final String JUST_DIED = "/Just_Died.wav";
    public static final String ENDING_THEME = "/Ending_Theme.wav";
    public static final String LEVEL_COMPLETE = "/Level_Complete.wav";
    public static final String BOMB_PLANT = "/Sound Effects/bombplant.wav";
    public static final String EXPLOSION_2 = "/Sound Effects/explosion2.wav";
    public static final String KICK = "/Sound Effects/kick.wav";
    public static final String POWER_UP_2 = "/Sound Effects/powerup2.wav";
    public static final String EXPLOSION_1 = "/Sound Effects/explosion.wav";
    public static ArrayList<String> BOMB;
    public static ArrayList<String> BOMBERMAN_1;
    public static ArrayList<String> LADRILLO;
    public static ArrayList<String> BALLOOM_1;
    public static ArrayList<String> ONEAL_1;
    public static ArrayList<String> DOLL_1;
    public static ArrayList<String> MINVO_1;
    public static ArrayList<String> KONDORIA_1;
    public static ArrayList<String> OVAPI_1;
    public static ArrayList<String> PASS_1;
    public static ArrayList<String> PONTAN_0;
    public static ArrayList<String> Muerte;
    public static String DEATH = "/Sound Effects/death.wav";
    public static String RIGHT = "/Sound Effects/right.wav";
    public static String LEFT = "/Sound Effects/left.wav";
    public static String UP = "/Sound Effects/up.wav";
    public static String DOWN = "/Sound Effects/down.wav";
    public static String PAUSE = "/Sound Effects/pause.wav";
    
    public Ubicacion(){
        initComponent();
       
    }
    public void initComponent(){
        BOMB=new ArrayList<>();
        BOMBERMAN_1=new ArrayList<>();
        LADRILLO=new ArrayList<>();
        BALLOOM_1=new ArrayList<>();
        ONEAL_1=new ArrayList<>();
        DOLL_1=new ArrayList<>();
        MINVO_1=new ArrayList<>();
        KONDORIA_1=new ArrayList<>();
        OVAPI_1=new ArrayList<>();
        PASS_1=new ArrayList<>();
        PONTAN_0=new ArrayList<>();
        Muerte=new ArrayList<>();
        
        BOMBERMAN_1=getJugador("Bomberman",12);
        
        BOMB=getAmbiente("Bomba",3);
        LADRILLO=getAmbiente("Bloque",7);
        
        
        BALLOOM_1=getEnemigo("Ballom");
        ONEAL_1=getEnemigo("Oneal");
        DOLL_1=getEnemigo("Doll");
        MINVO_1=getEnemigo("Minvo");
        KONDORIA_1=getEnemigo("Kondoria");
        OVAPI_1=getEnemigo("Ovapi");
        PASS_1=getEnemigo("Pass");
        PONTAN_0=getEnemigo("Pontan",13);
        Muerte=getEnemigo("Muerte",4);
    }
    

    
    public static ArrayList<String> getEnemigo(String Nombre){
        ArrayList<String> Enemigo=new ArrayList<>();
        try{
            for(int i=1;i<=7;i++) {
                Enemigo.add("/Enemigos/"+Nombre+"/"+Nombre+i+".png");
            }
        }catch(Exception e){}
        return Enemigo;
    }
    public static ArrayList<String> getEnemigo(String Nombre,int a){
        ArrayList<String> Enemigo=new ArrayList<>();
        try{
            for(int i=1;i<=a;i++) {
                Enemigo.add("/Enemigos/"+Nombre+"/"+Nombre+i+".png");
            }
        }catch(Exception e){}
        return Enemigo;
    }
    public static ArrayList<String> getJugador(String Nombre,int a){
        ArrayList<String> Enemigo=new ArrayList<>();
        try{
            for(int i=1;i<=a;i++) {
                Enemigo.add("/Jugadores/"+Nombre+"/"+Nombre+i+".png");
            }
        }catch(Exception e){}
        return Enemigo;
    }
    public static ArrayList<String> getAmbiente(String Nombre,int a){
        ArrayList<String> Enemigo=new ArrayList<>();
        try{
            for(int i=1;i<=a;i++) {
                Enemigo.add("/Ambiente/"+Nombre+"/"+Nombre+i+".png");
            }
        }catch(Exception e){}
        return Enemigo;
    }
    
}
