/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package motor.core.input;

import java.util.ArrayList;
import java.util.HashMap;
import lenguaje.utils.Pareja;
import motor.core.input.GamePad.Botones;
import motor.core.input.Teclado.EstadoTecla;
import static motor.core.input.Teclado.EstadoTecla.PRESIONADA;

/**
 * 
 * @author AlfonsoAndres
 */
public abstract class KeyboardController implements IGamePadController {

    private final HashMap<Integer, Botones> mapper;
    private final ArrayList<Pareja<Botones, Boolean>> buffer;

    protected KeyboardController(HashMap<Integer, Botones> mapper) {
        if(mapper == null) {
            throw new NullPointerException("Los controles no pueden ser nulos.");
        }
        this.mapper = mapper;
        this.buffer = new ArrayList<>();
        Teclado.getInstance().keyChangedSubscribe(this::keyChanged);
    }

    @Override
    public synchronized void update(GamePad g) {
        if(buffer.isEmpty()){
            return;
        }
        buffer.forEach((pareja) -> {
            g.setPress(pareja.getPrimero(), pareja.getSegundo());
        });
        buffer.clear();
    }

    private Void keyChanged(int keyCode, EstadoTecla estadoTecla) {
        if(mapper.containsKey(keyCode)) {
            synchronized(buffer) {
                buffer.add(Pareja.de(mapper.get(keyCode), estadoTecla == PRESIONADA));
            }
        }
        return null;
    }
    
}
