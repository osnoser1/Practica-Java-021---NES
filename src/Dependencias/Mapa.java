/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

/**
 *
 * @author hp
 */
public class Mapa {
    private String mapa[][];
    private short x,y;
    public static final short COLUMNAS=31,FILAS=13;
    private static String objeto="";
 
    public Mapa(){
        mapa=new String[FILAS][COLUMNAS];
        x=0; y=0;
        for(int i=0; i<FILAS; i++)
            for(int j=0; j<COLUMNAS; j++)
                if(i%2!=1&&j%2!=1||i==0||i==12||j==0||j==30)
                    mapa[i][j]="A";
                else
                    mapa[i][j]="V";
    }

    public void setMapa(String[][] mapa) {
        this.mapa = mapa;
    }

    public String[][] getMapa() {
        return mapa;
    }

    public static void setObjeto(String objeto){
        Mapa.objeto=objeto; 
    }

    public static String getObjeto(){
        return objeto;
    }
    public void setObjetoMapa(short x, short y){
       // if(x==0||y==0||x>=Mapa.FILAS||y>=Mapa.COLUMNAS)
        this.x=x;
        this.y=y;
        mapa[x][y]=objeto;
    }
    public void setObjetoMapa(short x, short y,String Objeto){
        if(x<=0||y<=0||x>=Mapa.FILAS||y>=Mapa.COLUMNAS)
            return;
        mapa[x][y]=Objeto;
    }

    public String getObjetoMapa(){
        return mapa[x][y];
    }
    public String getObjetoMapa(short x,short y){
        return mapa[x][y];
    }

    public void borrarMapa(){
        for(int i=0; i<FILAS; i++)
            for(int j=0; j<COLUMNAS; j++)
                if(i%2!=1&&j%2!=1||i==0||i==12||j==0||j==30)
                    mapa[i][j]="A";
                else
                    mapa[i][j]="V";
    }
    public void mostrarMapa(){
        for(int i=0; i<FILAS; i++){
            for(int j=0; j<COLUMNAS; j++)
              System.out.print(mapa[i][j]+" ");
            System.out.println();
        }
        System.out.println();
    }

    public String getObjetoMapa(int i, int j) {
        if(i<0||j<0||i>=Mapa.FILAS||j>=Mapa.COLUMNAS)
            return "A";
        return mapa[i][j];
    }
}
