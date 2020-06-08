package com.skyface.taskmanager.java;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class MainForm extends JFrame {

    private static final Logger log = Logger.getLogger(MainForm.class.getName());
    private static final String tag = MainForm.class.getSimpleName();

    private JPanel panelContainer;
    private JPanel panelToolbar;
    private JPanel panelContent;
    private JButton btnMinimize;
    private JButton btnClose;
    private JLabel txtToolbar;
    public JButton button1;
    public JRadioButton radioButton1;
    public JRadioButton radioButton2;
    public JRadioButton radioButton3;
    public JRadioButton radioButton4;
    public JCheckBox checkBox1;
    public JCheckBox checkBox2;
    public JCheckBox checkBox3;
    public JCheckBox checkBox4;
    public JCheckBox checkBox5;
    public JCheckBox checkBox6;
    public JCheckBox checkBox7;

    private TrayIcon trayIcon;
    private SystemTray systemTray;
    private JPopupMenu popupMenu;

    private boolean ignore = true;
    private int pX, pY;

    public MainForm() {
//        initTheme();
        initFont();
        initSystemTray();
        initFrame();
        setupToolbar();
    }

    private void setupToolbar() {
        log.log(Level.INFO, "Setup Toolbar");

        panelToolbar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                pX = event.getX();
                pY = event.getY();
            }

            public void mouseDragged(MouseEvent event) {
                setLocation(getLocation().x + event.getX() - pX, getLocation().y + event.getY() - pY);
            }
        });

        panelToolbar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent event) {
                setLocation(getLocation().x + event.getX() - pX, getLocation().y + event.getY() - pY);
            }
        });

        btnMinimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                super.mouseEntered(event);
                btnMinimize.setBackground(new Color(0x2C3847));
                btnMinimize.setForeground(new Color(0xFFFFFF));
            }

            @Override
            public void mouseExited(MouseEvent event) {
                super.mouseExited(event);
                btnMinimize.setBackground(new Color(0x242F3D));
                btnMinimize.setForeground(new Color(0x576673));
            }
        });

        btnMinimize.addActionListener(action -> minimizeApp());

        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                super.mouseEntered(event);
                btnClose.setBackground(new Color(0xFF4F69));
                btnClose.setForeground(new Color(0xFFFFFF));
            }

            @Override
            public void mouseExited(MouseEvent event) {
                super.mouseExited(event);
                btnClose.setBackground(new Color(0x242F3D));
                btnClose.setForeground(new Color(0x576673));
            }
        });

        btnClose.addActionListener(action -> hideAppToTray());
    }

    private void showAppFromTray() {
        log.log(Level.INFO, "Show App From Tray");

        setVisible(true);
        popupMenu.setVisible(false);
        systemTray.remove(trayIcon);
    }

    private void hideAppToTray() {
        log.log(Level.INFO, "Hide App To Tray");

        try {
            setVisible(false);
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            e.printStackTrace();
        }
    }

    private void minimizeApp() {
        log.log(Level.INFO, "Minimize App");

        setExtendedState(JFrame.ICONIFIED);
    }

    private void closeApp() {
        log.log(Level.INFO, "Close App");

        System.exit(0);
    }

    private void initSystemTray() {
        log.log(Level.INFO, "Init Tray");

        systemTray = SystemTray.getSystemTray();

        popupMenu = new JPopupMenu();
        popupMenu.setBackground(new Color(0x17212B));
//        popupMenu.setBackground(new Color(0x00000000, true));
//        popupMenu.setBorder(new RoundedBorder());

        List<JMenuItem> listOfMenus = new ArrayList<>();
        listOfMenus.add(new JMenuItem("Развернуть", new ImageIcon(getClass().getResource("/images/menu_expand.png"))));
        listOfMenus.add(new JMenuItem("Логи", new ImageIcon(getClass().getResource("/images/menu_log.png"))));
        listOfMenus.add(new JMenuItem("Закрыть", new ImageIcon(getClass().getResource("/images/menu_close.png"))));

        listOfMenus.get(0).addActionListener(action -> showAppFromTray());
        listOfMenus.get(2).addActionListener(action -> closeApp());

        for (JMenuItem item : listOfMenus) {
            item.setBackground(new Color(0x17212B));
            item.setForeground(new Color(0xFFFFFF));

            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent event) {
                    super.mouseEntered(event);
                    item.setBackground(new Color(0x2C3847));
                }

                @Override
                public void mouseExited(MouseEvent event) {
                    super.mouseExited(event);
                    item.setBackground(new Color(0x17212B));
                }
            });
            popupMenu.add(item);
        }

        popupMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (ignore) {
                    ignore = false;
                } else {
                    popupMenu.setVisible(false);
                    ignore = true;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ignore = true;
            }
        });

        trayIcon = new TrayIcon(
                new ImageIcon(getClass().getResource("/images/logo_16x16.png")).getImage(),
                "Task Manager"
        );

        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showAppFromTray();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.setLocation(e.getX(), e.getY());
                    popupMenu.setVisible(true);
                }
            }
        });
    }

    private void initFrame() {
        log.log(Level.INFO, "Init JFrame");

        setContentPane(panelContainer);
        setLocationRelativeTo(panelContainer);
        setSize(400, 400);
        setUndecorated(true);
        setIconImage(new ImageIcon(getClass().getResource("/images/logo_32x32.png")).getImage());
        setVisible(true);
    }

    /**
     * TODO Feature?
     */
    private void initFont() {
        log.log(Level.INFO, "Init Font");

        InputStream inputStream = MainForm.class.getResourceAsStream("/fonts/JetBrainsMono-Medium.ttf");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            Font sizedFont = font.deriveFont(12f);

            Enumeration keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof FontUIResource)
                    UIManager.put(key, sizedFont);
            }
        } catch (FontFormatException | IOException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            e.printStackTrace();
        }
    }

    private void initTheme() {
        log.log(Level.INFO, "Init Theme");

        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainForm();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelContainer = new JPanel();
        panelContainer.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelContainer.setBackground(new Color(-15854047));
        panelContainer.setEnabled(true);
        panelToolbar = new JPanel();
        panelToolbar.setLayout(new GridLayoutManager(1, 3, new Insets(0, 8, 0, 0), -1, -1));
        panelToolbar.setBackground(new Color(-14405827));
        panelContainer.add(panelToolbar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panelToolbar.add(panel1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnClose = new JButton();
        btnClose.setBackground(new Color(-14405827));
        btnClose.setBorderPainted(false);
        btnClose.setFocusable(false);
        Font btnCloseFont = this.$$$getFont$$$("JetBrains Mono Medium", Font.PLAIN, 18, btnClose.getFont());
        if (btnCloseFont != null) btnClose.setFont(btnCloseFont);
        btnClose.setForeground(new Color(-11049357));
        btnClose.setText("×");
        panel1.add(btnClose, BorderLayout.CENTER);
        btnMinimize = new JButton();
        btnMinimize.setBackground(new Color(-14405827));
        btnMinimize.setBorderPainted(false);
        btnMinimize.setFocusable(false);
        Font btnMinimizeFont = this.$$$getFont$$$("SansSerif", Font.PLAIN, 18, btnMinimize.getFont());
        if (btnMinimizeFont != null) btnMinimize.setFont(btnMinimizeFont);
        btnMinimize.setForeground(new Color(-11049357));
        btnMinimize.setText("─");
        panel1.add(btnMinimize, BorderLayout.WEST);
        final Spacer spacer1 = new Spacer();
        panelToolbar.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        txtToolbar = new JLabel();
        txtToolbar.setForeground(new Color(-1));
        txtToolbar.setIcon(new ImageIcon(getClass().getResource("/images/logo_16x16.png")));
        txtToolbar.setText("Task Manager");
        panelToolbar.add(txtToolbar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelContent = new JPanel();
        panelContent.setLayout(new GridLayoutManager(4, 4, new Insets(8, 8, 8, 8), -1, -1));
        panelContent.setBackground(new Color(-15261397));
        panelContainer.add(panelContent, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        button1 = new JButton();
        button1.setText("Button");
        panelContent.add(button1, new GridConstraints(0, 3, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton1 = new JRadioButton();
        radioButton1.setText("RadioButton");
        panelContent.add(radioButton1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton2 = new JRadioButton();
        radioButton2.setText("RadioButton");
        panelContent.add(radioButton2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton3 = new JRadioButton();
        radioButton3.setText("RadioButton");
        panelContent.add(radioButton3, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton4 = new JRadioButton();
        radioButton4.setText("RadioButton");
        panelContent.add(radioButton4, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox1 = new JCheckBox();
        checkBox1.setText("CheckBox");
        panelContent.add(checkBox1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panelContent.add(scrollPane1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(panel2);
        checkBox2 = new JCheckBox();
        checkBox2.setText("CheckBox");
        panel2.add(checkBox2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox3 = new JCheckBox();
        checkBox3.setText("CheckBox");
        panel2.add(checkBox3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox4 = new JCheckBox();
        checkBox4.setText("CheckBox");
        panel2.add(checkBox4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox5 = new JCheckBox();
        checkBox5.setText("CheckBox");
        panel2.add(checkBox5, new GridConstraints(1, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox6 = new JCheckBox();
        checkBox6.setText("CheckBox");
        panel2.add(checkBox6, new GridConstraints(2, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox7 = new JCheckBox();
        checkBox7.setText("CheckBox");
        panel2.add(checkBox7, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelContainer;
    }

}
