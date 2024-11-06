import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

class cbt extends JFrame implements ActionListener {
    JLabel l;
    JRadioButton rb[] = new JRadioButton[4]; // 4 options per question
    ButtonGroup bg;
    JButton b2, b3;
    int current = 0;
    int count = 0;

    // List to store questions and their respective options and correct answer
    List<Question> questions = new ArrayList<>();
    List<String> userAnswers = new ArrayList<>(); // To store the user's selected answers

    cbt(String s) {
        // About the frame
        super(s);
        this.setVisible(true);
        this.setSize(650, 350);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Label setup
        l = new JLabel();
        this.add(l);
        l.setBounds(50, 30, 1200, 40);

        // RadioButton and ButtonGroup setup
        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) { // 4 options per question
            rb[i] = new JRadioButton();
            this.add(rb[i]);
            bg.add(rb[i]);
        }
        for (int i = 0; i < 4; i++) {
            rb[i].setBounds(60, 80 + (20 * i), 300, 20);
        }

        // Buttons setup
        b2 = new JButton("Next", null);
        b3 = new JButton("Previous", null);
        this.add(b2);
        this.add(b3);
        b2.setBounds(260, 230, 100, 20);
        b3.setBounds(360, 230, 100, 20);

        // Action listeners for buttons
        b2.addActionListener(this);
        b3.addActionListener(this);

        // Load questions from CSV
        loadQuestions("questions.csv");

        // Initialize user answers with empty strings
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add(""); // Empty answer initially for each question
        }

        setQuestion();
    }

    // Action listener for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b2) { // Next button
            saveAnswer(); // Save the user's current answer

            if (check()) {
                count++;
            }
            current++;
            if (current >= questions.size()) { // Prevent index out of bounds
                current = questions.size() - 1; // Stay at last question
            }
            setQuestion();
        }

        if (e.getSource() == b3) { // Previous button
            saveAnswer(); // Save the user's current answer

            if (check()) {
                count--;
            }
            current--;
            if (current < 0) { // Prevent going below 0
                current = 0;
            }
            setQuestion();
        }

        if (e.getActionCommand().equals("Result")) { // Result button
            saveAnswer(); // Save the user's current answer

            // Display result message
            if (count < 4) {
                JOptionPane.showMessageDialog(this, "Correct answers= " + count + "\nFAIL");
            } else {
                JOptionPane.showMessageDialog(this, "Correct answers= " + count + "\nPASS");
            }
            System.exit(0);
        }
    }

    // Set the question and options dynamically
    void setQuestion() {
        // Clear previous options
        bg.clearSelection();

        // Check if there are more questions
        if (current < questions.size()) {
            Question question = questions.get(current);
            l.setText(question.getQuestion());

            // Set the options dynamically
            for (int i = 0; i < 4; i++) {
                rb[i].setText(question.getOptions()[i]);
            }

            // Restore the previously selected answer for the current question
            String userAnswer = userAnswers.get(current);
            for (int i = 0; i < 4; i++) {
                if (rb[i].getText().equals(userAnswer)) {
                    rb[i].setSelected(true);
                    break;
                }
            }

            // Adjust the button labels for the last question
            if (current == questions.size() - 1) {
                b2.setText("Result");
            } else {
                b2.setText("Next");
            }
        }
    }

    // Save the user's selected answer for the current question
    void saveAnswer() {
        for (int i = 0; i < 4; i++) {
            if (rb[i].isSelected()) {
                userAnswers.set(current, rb[i].getText());
                break;
            }
        }
    }

    // Check if the selected answer is correct for the current question
    boolean check() {
        if (current >= questions.size()) { // Prevent out of bounds
            return false;
        }
        Question question = questions.get(current);
        return userAnswers.get(current).equals(question.getCorrectAnswer());
    }

    // Load questions from a CSV file
    void loadQuestions(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String questionText = parts[0].trim();
                    String[] options = { parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim() };
                    String correctAnswer = parts[5].trim();
                    questions.add(new Question(questionText, options, correctAnswer));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading questions file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new cbt("Computer Based Test");
    }
}

class Question {
    private String question;
    private String[] options;
    private String correctAnswer;

    public Question(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}