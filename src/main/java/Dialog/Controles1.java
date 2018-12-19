/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import lenguaje.utils.ManejadorDeArchivos;
import motor.core.input.GamePad.Botones;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;

/**
 *
 * @author Alfonso
 */
public class Controles1 extends javax.swing.JDialog {

    /**
     * Creates new form Controles1
     */
    private Botones targetKey;
    private JTextField sender;
    private HashMap<Integer, Botones> currentKeys;
    private HashMap<Botones, Integer> revertKeys;
    private boolean getKey = false;
    private final ControllerPollThread controllerPollThread;
    private static final int maxControllerFieldValueLength = 9;
    
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public Controles1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        controllerPollThread = new ControllerPollThread();
        controllerPollThread.setName("Controller Poll Thread");
        controllerPollThread.setDaemon(true);
        controllerPollThread.start();
    }
    
    private class ControllerPollThread extends Thread {
        
    	final protected boolean exit = false;
        
        @Override
        public void run() {
            while (!exit) {
                var controller = getControlSeleccionado();
                if (controller != null && controller.poll()) {
                    var eventQueue = controller.getEventQueue();
                    var event = new Event();
                    while (eventQueue.getNextEvent(event)) {
                            onControllerEvent(event);
                    }
                }

                // Wait a little bit before polling again...
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // Ignore exception
                }
            }
        }
    }

    public ComboBoxModel hacerComboBoxModelControl() {
        MutableComboBoxModel comboBox = new DefaultComboBoxModel();
        var ce = ControllerEnvironment.getDefaultEnvironment();
        var controllers = ce.getControllers();
        for (var c : controllers) {
        	comboBox.addElement(c);
        }
        return comboBox;
    }
    
    private Controller getControlSeleccionado() {
        if (controllerBox != null) {
            var controllerIndex = controllerBox.getSelectedIndex();
            var ce = ControllerEnvironment.getDefaultEnvironment();
            var controllers = ce.getControllers();
            if (controllers != null && controllerIndex >= 0 && controllerIndex < controllers.length) {
                return controllers[controllerIndex];
            }
        }
        return null;
    }
    
    private void setControllerMapping(Botones targetKey, String identifierName, JTextField field) {
//    	currentController.put(targetKey, identifierName);
        field.setText(getControllerFieldText(identifierName));
        getKey = false;
    }
    
    private Component getControllerComponent(String identifierName) {
        var controller = getControlSeleccionado();
    	if (controller == null) {
    		return null;
    	}
        var components = controller.getComponents();
    	if (components == null) {
    		return null;
    	}
        for (var component : components) {
            if (identifierName.equals(component.getIdentifier().getName()))
                return component;
        }
    	return null;
    }
    
    private String getControllerFieldText(String identifierName) {
        var component = getControllerComponent(identifierName);
    	if (component == null) {
    		return identifierName;
    	}

        var name = component.getName();
    	if (name == null) {
    		// Use the Identifier name if the component has no name
    		name = identifierName;
    	} else if (name.length() > maxControllerFieldValueLength && identifierName.length() < name.length()) {
    		// Use the Identifier name if the component name is too long to fit
    		// into the display field
    		name = identifierName;
    	}
    	return name;
    }
    
    private void onControllerEvent(Event event) {
        if (!getKey) {
            return;
        }
        var component = event.getComponent();
        var value = event.getValue();
        var identifier = component.getIdentifier();
        var identifierName = identifier.getName();
        if (identifier instanceof Component.Identifier.Button && value == 1.f) {
            setControllerMapping(targetKey, identifierName, sender);
        } else if(identifier instanceof Component.Identifier.Key) {
            switch(targetKey){
                case A:
                    setControllerMapping(Botones.A, identifierName, cruz);
                    break;
                case B:
                    setControllerMapping(Botones.B, identifierName, cuadrado);
                    break;
                case L1:
                    setControllerMapping(Botones.L1, identifierName, l1);
                    break;
                case L2:
                    setControllerMapping(Botones.L2, identifierName, l2);
                    break;
                case R1:
                    setControllerMapping(Botones.R1, identifierName, r1);
                    break;
                case R2:
                    setControllerMapping(Botones.R2, identifierName, r2);
                    break;
            }
            System.out.println(identifierName);
        } else if (identifier == Component.Identifier.Axis.POV) {
            switch (targetKey) {
                case ABAJO:
                case ARRIBA:
                case IZQUIERDA:
                case DERECHA:
                    setControllerMapping(Botones.ABAJO, identifierName, pdAbajo);
                    setControllerMapping(Botones.ARRIBA, identifierName, pdArriba);
                    setControllerMapping(Botones.IZQUIERDA, identifierName, pdIzquierda);
                    setControllerMapping(Botones.DERECHA, identifierName, pdDerecha);
                    break;
                default:
//                    jpcsp.Controller.log.warn(String.format("Unknown Controller POV Event on %s(%s): %f for %s", component.getName(), identifier.getName(), value, targetKey.toString()));
                    break;
            }
        } else if (identifier instanceof Component.Identifier.Axis) {
            switch (targetKey) {
                case ABAJO:
                case ARRIBA:
                    setControllerMapping(Botones.ABAJO, identifierName, pdAbajo);
                    setControllerMapping(Botones.ARRIBA, identifierName, pdArriba);
                    break;
                case IZQUIERDA:
                case DERECHA:
                    setControllerMapping(Botones.IZQUIERDA, identifierName, pdIzquierda);
                    setControllerMapping(Botones.DERECHA, identifierName, pdDerecha);
                    break;
                case AIABAJO:
                case AIARRIBA:
                    setControllerMapping(Botones.AIABAJO, identifierName, siAbajo);
                    setControllerMapping(Botones.AIARRIBA, identifierName, siArriba);
                    break;
                case AIIZQUIERDA:
                case AIDERECHA:
                    setControllerMapping(Botones.AIIZQUIERDA, identifierName, siIzquierda);
                    setControllerMapping(Botones.AIDERECHA, identifierName, siDerecha);
                    break;
                default:
                    setControllerMapping(targetKey, identifierName, sender);
                    break;
            }
        } else {
            if (identifier instanceof Component.Identifier.Axis) {
//                jpcsp.Controller.log.debug(String.format("Unknown Controller Event in DeadZone on %s(%s): %f for %s", component.getName(), identifier.getName(), value, targetKey.toString()));
            } else {
//                jpcsp.Controller.log.warn(String.format("Unknown Controller Event on %s(%s): %f for %s", component.getName(), identifier.getName(), value, targetKey.toString()));
            }
        }
    }
    
    private void setKey(JTextField sender, Botones targetKey) {
        if (getKey) {
            this.sender.setText(KeyEvent.getKeyText(revertKeys.get(this.targetKey)));
        }
        sender.setText("Presione tecla");
        getKey = true;
        this.sender = sender;
        this.targetKey = targetKey;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        controllerBox = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pdAbajo = new javax.swing.JTextField();
        pdIzquierda = new javax.swing.JTextField();
        pdDerecha = new javax.swing.JTextField();
        pdArriba = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        siAbajo = new javax.swing.JTextField();
        siIzquierda = new javax.swing.JTextField();
        siDerecha = new javax.swing.JTextField();
        siArriba = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cuadrado = new javax.swing.JTextField();
        circulo = new javax.swing.JTextField();
        triangulo = new javax.swing.JTextField();
        cruz = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel(ManejadorDeArchivos.getInstance().loadIconJAR("/control.jpg"));
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        sdAbajo = new javax.swing.JTextField();
        sdIzquierda = new javax.swing.JTextField();
        sdDerecha = new javax.swing.JTextField();
        sdArriba = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        l2 = new javax.swing.JTextField();
        r2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        select = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        start = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        l1 = new javax.swing.JTextField();
        r1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton1 = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jButton2 = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configurar Entrada");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(null);
        setMinimumSize(new java.awt.Dimension(790, 500));
        setModal(true);
        setPreferredSize(new java.awt.Dimension(790, 500));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 603));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(810, 390));
        jPanel2.setPreferredSize(new java.awt.Dimension(810, 390));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dispositivo de entrada"));

        controllerBox.setModel(hacerComboBoxModelControl());

        var jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(controllerBox, 0, 308, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(controllerBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pad Digital"));

        jLabel1.setText("Arriba:");

        jLabel2.setText("Abajo:");

        jLabel3.setText("Izquierda:");

        jLabel4.setText("Derecha:");

        pdAbajo.setEditable(false);
        pdAbajo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pdAbajo.setHighlighter(null);
        pdAbajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        pdIzquierda.setEditable(false);
        pdIzquierda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pdIzquierda.setHighlighter(null);
        pdIzquierda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        pdDerecha.setEditable(false);
        pdDerecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pdDerecha.setHighlighter(null);
        pdDerecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        pdArriba.setEditable(false);
        pdArriba.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pdArriba.setHighlighter(null);
        pdArriba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        var jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pdAbajo, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(pdArriba)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pdDerecha)
                            .addComponent(pdIzquierda))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(pdArriba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pdAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pdIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pdDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Stick Izquierdo"));

        jLabel9.setText("Arriba:");

        jLabel10.setText("Abajo:");

        jLabel11.setText("Izquierda:");

        jLabel12.setText("Derecha:");

        siAbajo.setEditable(false);
        siAbajo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        siAbajo.setHighlighter(null);
        siAbajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        siIzquierda.setEditable(false);
        siIzquierda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        siIzquierda.setHighlighter(null);
        siIzquierda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        siDerecha.setEditable(false);
        siDerecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        siDerecha.setHighlighter(null);
        siDerecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        siArriba.setEditable(false);
        siArriba.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        siArriba.setHighlighter(null);
        siArriba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        var jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(siAbajo, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(siArriba)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(siDerecha)
                            .addComponent(siIzquierda))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(siArriba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(siAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(siIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(siDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Principal"));

        jLabel14.setText("Cruz:");

        jLabel15.setText("Cuadrado:");

        jLabel16.setText("Circulo:");

        jLabel17.setText("Triangulo:");

        cuadrado.setEditable(false);
        cuadrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cuadrado.setHighlighter(null);
        cuadrado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        circulo.setEditable(false);
        circulo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        circulo.setHighlighter(null);
        circulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        triangulo.setEditable(false);
        triangulo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        triangulo.setHighlighter(null);
        triangulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        cruz.setEditable(false);
        cruz.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cruz.setHighlighter(null);
        cruz.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        var jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(circulo, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(triangulo)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cruz)
                            .addComponent(cuadrado))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(cruz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(circulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(triangulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel13.setText("jLabel13");
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel7.add(jLabel13, java.awt.BorderLayout.LINE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Stick Derecho"));

        jLabel18.setText("Arriba:");

        jLabel19.setText("Abajo:");

        jLabel20.setText("Izquierda:");

        jLabel21.setText("Derecha:");

        sdAbajo.setEditable(false);
        sdAbajo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sdAbajo.setHighlighter(null);
        sdAbajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        sdIzquierda.setEditable(false);
        sdIzquierda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sdIzquierda.setHighlighter(null);
        sdIzquierda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        sdDerecha.setEditable(false);
        sdDerecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sdDerecha.setHighlighter(null);
        sdDerecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        sdArriba.setEditable(false);
        sdArriba.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sdArriba.setHighlighter(null);
        sdArriba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        var jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sdIzquierda, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(sdDerecha)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sdArriba)
                            .addComponent(sdAbajo))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(sdArriba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(sdAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(sdIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(sdDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel24.setText("L2:");

        l2.setEditable(false);
        l2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        l2.setHighlighter(null);
        l2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        r2.setEditable(false);
        r2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r2.setHighlighter(null);
        r2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        jLabel26.setText("Select:");

        select.setEditable(false);
        select.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        select.setHighlighter(null);
        select.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        jLabel27.setText("Start:");

        start.setEditable(false);
        start.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        start.setHighlighter(null);
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        jLabel28.setText("L1:");

        l1.setEditable(false);
        l1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        l1.setHighlighter(null);
        l1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        r1.setEditable(false);
        r1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r1.setHighlighter(null);
        r1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfMouseClicked(evt);
            }
        });

        jLabel29.setText("R1:");

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jButton23.setText("Configurar");
        jPanel10.add(jButton23);

        jButton24.setText("Cargar...");
        jPanel10.add(jButton24);

        jButton31.setText("Por defecto");
        jButton31.addActionListener(this::jButton31ActionPerformed);
        jPanel10.add(jButton31);

        jButton32.setText("Guardar...");
        jPanel10.add(jButton32);

        jLabel30.setText("R2:");

        var jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addGap(14, 14, 14)
                                        .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel30)
                                        .addGap(18, 18, 18)
                                        .addComponent(r2))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addGap(14, 14, 14)
                                        .addComponent(l1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel29)
                                        .addGap(18, 18, 18)
                                        .addComponent(r1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel28)
                                        .addComponent(l1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel29)
                                        .addComponent(r1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel24)
                                        .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(r2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel30)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27)
                                .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)))
                .addContainerGap())
        );

        jPanel6.getAccessibleContext().setAccessibleName("Stick Analogico");

        jTabbedPane1.addTab("Jugador 1", jPanel2);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setMinimumSize(new java.awt.Dimension(146, 33));
        jPanel1.setPreferredSize(new java.awt.Dimension(146, 33));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(filler1);

        jButton1.setText("Aceptar");
        jButton1.setDoubleBuffered(true);
        jPanel1.add(jButton1);
        jPanel1.add(filler2);

        jButton2.setText("Cancelar");
        jButton2.setDoubleBuffered(true);
        jPanel1.add(jButton2);
        jPanel1.add(filler3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jtfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfMouseClicked
        var source = (JTextField) evt.getSource();
        if(source == pdArriba) setKey(source, Botones.ARRIBA);
        else if(source == pdAbajo) setKey(source, Botones.ABAJO);
        else if(source == pdIzquierda) setKey(source, Botones.IZQUIERDA);
        else if(source == pdDerecha) setKey(source, Botones.DERECHA);
        else if(source == start) setKey(source, Botones.START);
        else if(source == r1) setKey(source, Botones.R1);
        else if(source == r2) setKey(source, Botones.R2);
        else if(source == l1) setKey(source, Botones.L1);
        else if(source == l2) setKey(source, Botones.L2);
        else if(source == siAbajo) setKey(source, Botones.AIABAJO);
        else if(source == siArriba) setKey(source, Botones.AIARRIBA);
        else if(source == siIzquierda) setKey(source, Botones.AIIZQUIERDA);
        else if(source == siDerecha) setKey(source, Botones.AIDERECHA);
        else if(source == sdAbajo) setKey(source, Botones.ADABAJO);
        else if(source == sdArriba) setKey(source, Botones.ADARRIBA);
        else if(source == sdDerecha) setKey(source, Botones.ADDERECHA);
        else if(source == sdIzquierda) setKey(source, Botones.ADIZQUIERDA);
        else if(source == cuadrado) setKey(source, Botones.B);
        else if(source == cruz) setKey(source, Botones.A);
        else if(source == triangulo) setKey(source, Botones.ADDERECHA);
        else if(source == circulo) setKey(source, Botones.ADDERECHA);
        else if(source == select) setKey(source, Botones.SELECT);
    }//GEN-LAST:event_jtfMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (var info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Controles1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            var dialog = new Controles1(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField circulo;
    private javax.swing.JComboBox controllerBox;
    private javax.swing.JTextField cruz;
    private javax.swing.JTextField cuadrado;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField l1;
    private javax.swing.JTextField l2;
    private javax.swing.JTextField pdAbajo;
    private javax.swing.JTextField pdArriba;
    private javax.swing.JTextField pdDerecha;
    private javax.swing.JTextField pdIzquierda;
    private javax.swing.JTextField r1;
    private javax.swing.JTextField r2;
    private javax.swing.JTextField sdAbajo;
    private javax.swing.JTextField sdArriba;
    private javax.swing.JTextField sdDerecha;
    private javax.swing.JTextField sdIzquierda;
    private javax.swing.JTextField select;
    private javax.swing.JTextField siAbajo;
    private javax.swing.JTextField siArriba;
    private javax.swing.JTextField siDerecha;
    private javax.swing.JTextField siIzquierda;
    private javax.swing.JTextField start;
    private javax.swing.JTextField triangulo;
    // End of variables declaration//GEN-END:variables
}
