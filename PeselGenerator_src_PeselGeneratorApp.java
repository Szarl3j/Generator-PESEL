import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PeselGeneratorApp extends JFrame {

    private JTextField yearField, monthField, dayField, peselField;
    private JRadioButton maleRadioButton, femaleRadioButton;

    public PeselGeneratorApp() {
        setTitle("PESEL Generator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));

        mainPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        mainPanel.add(yearField);

        mainPanel.add(new JLabel("Month:"));
        monthField = new JTextField();
        mainPanel.add(monthField);

        mainPanel.add(new JLabel("Day:"));
        dayField = new JTextField();
        mainPanel.add(dayField);

        mainPanel.add(new JLabel("Gender:"));
        ButtonGroup genderGroup = new ButtonGroup();
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        mainPanel.add(maleRadioButton);
        mainPanel.add(femaleRadioButton);

        mainPanel.add(new JLabel("Generated PESEL:"));
        peselField = new JTextField();
        peselField.setEditable(false);
        mainPanel.add(peselField);

        JButton generateButton = new JButton("Generate PESEL");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePesel();
            }
        });
        mainPanel.add(generateButton);

        add(mainPanel);
    }

    private void generatePesel() {
        String yearText = yearField.getText();
        String monthText = monthField.getText();
        String dayText = dayField.getText();

        if (!yearText.isEmpty() && !monthText.isEmpty() && !dayText.isEmpty() &&
                (maleRadioButton.isSelected() || femaleRadioButton.isSelected())) {
            int year = Integer.parseInt(yearText);
            int month = Integer.parseInt(monthText);
            int day = Integer.parseInt(dayText);

            char gender = maleRadioButton.isSelected() ? 'M' : 'F';

            String pesel = generatePesel(year, month, day, gender);
            peselField.setText(pesel);
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields and select gender.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generatePesel(int year, int month, int day, char gender) {
        // Generowanie numeru PESEL na podstawie dostarczonych informacji w zadaniu
        StringBuilder pesel = new StringBuilder();

        // Rok
        pesel.append(String.format("%02d", year % 100));

        // Miesiąc
        if (year >= 1800 && year <= 1899) {
            month += 80;
        } else if (year >= 2000 && year <= 2099) {
            month += 20;
        } else if (year >= 2100 && year <= 2199) {
            month += 40;
        } else if (year >= 2200 && year <= 2299) {
            month += 60;
        }
        pesel.append(String.format("%02d", month));

        // Dzień
        pesel.append(String.format("%02d", day));

        // Losowe liczby
        pesel.append((int) (Math.random() * 10));
        pesel.append((int) (Math.random() * 10));
        pesel.append((int) (Math.random() * 10));

        // Płeć
        if (gender == 'M') {
            pesel.append((int) (Math.random() * 5) * 2);
        } else {
            pesel.append((int) (Math.random() * 5) * 2 + 1);
        }

        // Suma kontrolna
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int controlSum = 0;
        for (int i = 0; i < 10; i++) {
            controlSum += Character.getNumericValue(pesel.charAt(i)) * weights[i];
        }
        controlSum %= 10;
        controlSum = 10 - controlSum;
        controlSum %= 10;
        pesel.append(controlSum);

        return pesel.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PeselGeneratorApp().setVisible(true);
            }
        });
    }
}
