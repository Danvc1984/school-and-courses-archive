import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.DefaultListModel;

public class FrmLectoresEscritores extends javax.swing.JFrame {

    public FrmLectoresEscritores() {
        initComponents();
    }
    
    Planificador planificador;
    Thread hiloPlanificador;
    int contador;
    public ReentrantLock lock;
    public String datos;
    
    public void actualizarTablas() { new Thread(() -> {
        try {
            
            Thread.sleep(200);
            DefaultListModel<String> listaEsperando = new DefaultListModel<>();
            DefaultListModel<String> listaAdentro = new DefaultListModel<>();
            boolean cambio = false;
            
            for(Map.Entry<Integer, Proceso> proceso : planificador.procesos.entrySet()) {
                
                String quien = proceso.getValue().getClass().equals(Lector.class) ? "Lector" : "Escritor";
                if(cambio) listaEsperando.addElement(quien);
                else listaAdentro.addElement(quien);
                
                if(quien.equals("Escritor")) cambio = true;
            }
            
            synchronized(lstEsperando) {
                lstEsperando.setModel(listaEsperando);
            }
            synchronized(lstAdentro) {
                lstAdentro.setModel(listaAdentro);
            }
            
        } catch (InterruptedException ex) { }
    }).start(); }

    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnLector = new javax.swing.JButton();
        btnEscritor = new javax.swing.JButton();
        txtDatos = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lectores y escritores");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane1.setViewportView(lstEsperando);

        jLabel1.setText("Tiempo dentro (seg):");

        jSlider1.setMaximum(5000);
        jSlider1.setValue(3000);

        jScrollPane2.setViewportView(lstAdentro);

        jLabel2.setText("Esperando");
        jLabel2.setToolTipText("");

        jLabel3.setText("Adentro");

        btnLector.setText("Lector");
        btnLector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLectorActionPerformed(evt);
            }
        });

        btnEscritor.setText("Escritor");
        btnEscritor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscritorActionPerformed(evt);
            }
        });

        txtDatos.setText("Un cuento de hadas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLector)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEscritor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDatos))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLector)
                    .addComponent(btnEscritor)
                    .addComponent(txtDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnLectorActionPerformed(java.awt.event.ActionEvent evt) {
        planificador.agregarProceso(new Lector(this, ++contador, 50, jSlider1.getValue()));
        actualizarTablas();
    }
    private void btnEscritorActionPerformed(java.awt.event.ActionEvent evt) {
        planificador.agregarProceso(new Escritor(this, ++contador, 50, jSlider1.getValue()));
        actualizarTablas();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        
        lock = new ReentrantLock();
        planificador = new Planificador();
        hiloPlanificador = new Thread(planificador);
        hiloPlanificador.start();
        contador = 0;
        datos = txtDatos.getText();
    }
    public static void main(String args[]) {
      
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLectoresEscritores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new FrmLectoresEscritores().setVisible(true);
        });
    }

    
    private javax.swing.JButton btnEscritor;
    private javax.swing.JButton btnLector;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    private final javax.swing.JList<String> lstAdentro = new javax.swing.JList<>();
    private final javax.swing.JList<String> lstEsperando = new javax.swing.JList<>();
    public javax.swing.JTextField txtDatos;
}
