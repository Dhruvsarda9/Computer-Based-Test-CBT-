import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class cbt extends JFrame implements ActionListener {
    JLabel l;
    JRadioButton rb[] = new JRadioButton[5];
    ButtonGroup bg;
    JButton b1, b2, b3;
    int current = 0;
    int bm[] = new int[11];
    int now, count = 0;

    cbt(String s) {
        // about the frame
        super(s);
        this.setVisible(true);
        this.setSize(650, 350);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // about the label
        l = new JLabel();
        this.add(l);
        l.setBounds(50, 30, 1200, 40);
        // about the radiobutton and buttongroup
        bg = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            rb[i] = new JRadioButton();
            this.add(rb[i]);
            bg.add(rb[i]);
        }
        for (int i = 0; i < 4; i++) {
            rb[i].setBounds(60, 80 + (20 * i), 300, 20);
        }
        // about the buttons
        b1 = new JButton("bookmark", null);
        b2 = new JButton("next", null);
        b3 = new JButton("previous", null);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        b1.setBounds(160, 230, 100, 20);
        b2.setBounds(260, 230, 100, 20);
        b3.setBounds(360, 230, 100, 20);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        set();
    }

    // about the action when button are clicked
    public void actionPerformed(ActionEvent e) {
        // next button
        if (e.getSource() == b2) {
            if (check()) {
                count++;
            }
            current++;
            set();
        }
        int x = current + 1;
        if (e.getActionCommand().equals("bookmark")) {
            // bookmark button
            JButton bk = new JButton("bookmark" + x);
            this.add(bk);
            bk.addActionListener(this);
            bk.setBounds(500, 60 + (20 * x), 125, 20);
            bm[x] = current;
            current++;
            x++;
            set();

        }
        if (current == 9) {
            // last slide
            b2.setEnabled(true);
            b1.setText("result");
        }

        // previous button
        if (e.getSource() == b3) {
            if (check()) {
                count--;
            }
            current--;
            set();
        }
        // working of bookmark
        for (int i = 0, y = 1; i < x; i++, y++) {
            if (e.getActionCommand().equals("bookmark" + y)) {
                if (check()) {
                    count++;
                }
                now = current;
                current = bm[y];
                set();
                // ((JButton)e.getSource()).setEnabled(false);
                current = now;
            }
        }
        if (e.getActionCommand().equals("result")) {
            // result button
            if (check()) {
                count = count + 1;
            }
            current++;
            if (count < 4) {
                JOptionPane.showMessageDialog(this, "Correct answers= " + count + "\n FAIL");
            } else {
                JOptionPane.showMessageDialog(this, "Correct answers= " + count + "\n PASS");
            }
            System.exit(0);
        }
    }

    void set() {
        // setting questions and options
        rb[4].setSelected(true);
        if (current == 0) {
            l.setText("Q1) what is your name?");
            rb[0].setText("dhruv");
            rb[1].setText("deep");
            rb[2].setText("rudraksh");
            rb[3].setText("vishwa");
        }
        if (current == 1) {
            l.setText("Q2) what is  your subject?");
            rb[0].setText("pp");
            rb[1].setText("ep");
            rb[2].setText("jp");
            rb[3].setText("dsap");
        }
        if (current == 2) {
            l.setText("Q3) what is correct syntax to output 'hello world' in java?");
            rb[0].setText("print('hello world')");
            rb[1].setText("System.out.println('hello world')");
            rb[2].setText("console.writeline('hello world')");
            rb[3].setText("nota");
        }
        if (current == 3) {
            l.setText("Q4)how do you write single line comment in java?");
            rb[0].setText("using /*");
            rb[1].setText("using #");
            rb[2].setText("using //");
            rb[3].setText("all");
        }
        if (current == 4) {
            l.setText("Q5)which statement is use to stop a loop?");
            rb[0].setText("return");
            rb[1].setText("exit");
            rb[2].setText("stop");
            rb[3].setText("break");
        }
        if (current == 5) {
            l.setText("Q6) which operator is used to multiply numbers?");
            rb[0].setText("#");
            rb[1].setText("*");
            rb[2].setText("X");
            rb[3].setText("%");
        }
        if (current == 6) {
            l.setText("Q7) which data type is used to create a variable that stores text?");
            rb[0].setText("Txt");
            rb[1].setText("string");
            rb[2].setText("MyString");
            rb[3].setText("String");
        }
        if (current == 7) {
            l.setText("Q8) array indexes start with?");
            rb[0].setText("-1");
            rb[1].setText("1");
            rb[2].setText("0");
            rb[3].setText("nota");
        }
        if (current == 8) {
            l.setText("Q9) what is size of float in java?");
            rb[0].setText("64");
            rb[1].setText("32");
            rb[2].setText("34");
            rb[3].setText("62");
        }
        if (current == 9) {
            l.setText("Q10) what is size of double in java?");
            rb[0].setText("64");
            rb[1].setText("32");
            rb[2].setText("34");
            rb[3].setText("62");
        }
    }

    boolean check() {
        // evaluation
        if (current == 0) {
            return rb[0].isSelected();
        }
        if (current == 1) {
            return rb[2].isSelected();
        }
        if (current == 2) {
            return rb[1].isSelected();
        }
        if (current == 3) {
            return rb[2].isSelected();
        }
        if (current == 4) {
            return rb[3].isSelected();
        }
        if (current == 5) {
            return rb[1].isSelected();
        }
        if (current == 6) {
            return rb[3].isSelected();
        }
        if (current == 7) {
            return rb[2].isSelected();
        }
        if (current == 8) {
            return rb[1].isSelected();
        }
        if (current == 9) {
            return rb[0].isSelected();
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        new cbt("COMPUTER BASED TEST");
    }
}
