package com.uzmansistem;

import GUI.MainFrame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Questions {

	private MainFrame frame;

	// Questions to user (Turkish)
	private final String[] questions = new String[] { "V�cudu k�llar ile mi kapl�?", "V�cudu t�yler ile mi kapl�?",
			"Yumurtlayarak m� �r�yor?", "Yavrular�n� s�t ile mi besliyor?", "U�abiliyor mu?",
			"Suda m� ya��yor? (Aquatic)", "Et�il bir hayvan m�?", "Di�lere sahip mi?", "Omurgal� bir hayvan m�?",
			"Akci�erleri ile mi nefes al�yor? (Breathe)", "Zehirli bir hayvan m�?", "Y�zge�lere sahip mi?",
			"Ka� adet baca�� var?", "Kuyru�u var m�?", "Evcil bir hayvan m� (Domestic)?" };

	private ArrayList<String> questionNumbers;

	// Constructor
	public Questions() {

	}

	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}

	public void initQuestions() {

		questionNumbers = new ArrayList<>();

		for (int i = 0; i < questions.length; i++) {
			questionNumbers.add(String.valueOf(i));
		}

		askQuestion();
	}

	public void askQuestion() {

		Random random = new Random();

		if (questionNumbers.size() > 0) {
			// random number
			int number = random.nextInt(questionNumbers.size());
			// question number
			int qNumber = Integer.parseInt(questionNumbers.get(number));
			// sets question into frame
			frame.setQuestion(questions[qNumber], qNumber);

		} else {
			// Gets query result from prolog
			ArrayList<String> result = Main.runProlog();

			if (!result.get(0).equalsIgnoreCase("There is not matching animal!")) {
				int randPic = random.nextInt(result.size());
				String resource = "/pics/" + result.get(randPic).toLowerCase() + ".png";
				ImageIcon icon = new ImageIcon(this.getClass().getResource(resource));
				JLabel label = new JLabel(result.get(randPic), icon, JLabel.CENTER);
				int response = JOptionPane.showConfirmDialog(null, label, "Result", -1);
				result.remove(randPic);

				switch (response) {
				case JOptionPane.NO_OPTION:
					System.out.println("No button clicked");
					break;
				case JOptionPane.YES_OPTION:
					while (!result.isEmpty()) {
						randPic = random.nextInt(result.size());
						resource = "/pics/" + result.get(randPic).toLowerCase() + ".png";
						icon = new ImageIcon(this.getClass().getResource(resource));
						label = new JLabel(result.get(randPic), icon, JLabel.CENTER);
						response = JOptionPane.showConfirmDialog(null, label, "Result", -1);
						if (response != JOptionPane.YES_OPTION) {
							break;
						}
						result.remove(randPic);
					}
					break;
				case JOptionPane.CLOSED_OPTION:
					System.out.println("JOptionPane closed");
					break;
				}

			} else {
				String resource = "/pics/pikachu.png";
				ImageIcon icon = new ImageIcon(this.getClass().getResource(resource));
				JLabel label = new JLabel("There is not matching animal!", icon, JLabel.LEFT);
				JOptionPane.showMessageDialog(null, label, "Result", -1);
			}

			int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			switch (response) {
			case JOptionPane.NO_OPTION:
				System.out.println("No button clicked");
				frame.dispose();
				break;
			case JOptionPane.YES_OPTION:
				System.out.println("Yes button clicked");
				initQuestions();
				Main.deleteFile();
				CreatePrologFile cpf = new CreatePrologFile();
				cpf.createPL();
				break;
			case JOptionPane.CLOSED_OPTION:
				System.out.println("JOptionPane closed");
				frame.dispose();
				break;
			}

		}
	}

	public void answerQuestion(String answer, int qNumber) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(CreatePrologFile.path + "/Animal.pl", true));
			String batch;

			switch (qNumber) {
			case 0:
				batch = q0(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 1:
				batch = q1(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 2:
				batch = q2(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 3:
				batch = q3(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 4:
				batch = q4(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 5:
				batch = q5(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 6:
				batch = q6(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 7:
				batch = q7(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 8:
				batch = q8(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 9:
				batch = q9(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 10:
				batch = q10(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 11:
				batch = q11(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 12:
				batch = q12(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 13:
				batch = q13(answer);
				bw.append(batch);
				bw.newLine();
				break;
			case 14:
				batch = q14(answer);
				bw.append(batch);
				bw.newLine();
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String q0(String answer) {
		questionNumbers.remove("0");
		StringBuilder sb = new StringBuilder();

		// V�cudu k�l kapl�ysa memeli bir hayvand�r. (yunus hari�)
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasHair(_).\n");
			// V�cudu t�y ile kapl� de�ildir.
			if (questionNumbers.remove("1")) {
				sb.append("hasFeather(_) :- false .\n");
			}
			// Yumurtlayarak �remez.
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_) :- false .\n");
			}
			// yavrular�n s�t ile besler
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_).\n");
			}
			// Di�lere sahiiptir.
			if (questionNumbers.remove("7")) {
				sb.append("hasToothed(_).\n");
			}
			// Omurgal� bir hayvand�r.
			if (questionNumbers.remove("8")) {
				sb.append("hasBackbone(_).\n");
			}
			// Akci�erleri ile solunum yapar
			if (questionNumbers.remove("9")) {
				sb.append("hasBreathes(_).\n");
			}

		} // V�cudunda k�l yok ise Memeli bir hayvan de�ildir
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasHair(_) :- false .\n");
			// yavrular�n s�t ile beslemez
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_) :- false .\n");
			}

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("0");

		}

		return sb.toString();
	}

	private String q1(String answer) {
		questionNumbers.remove("1");
		StringBuilder sb = new StringBuilder();

		// V�cudu t�yler ile kapl�ysa bir ku�tur.
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasFeather(_).\n");
			// V�cudu k�l ile kapl� de�ildir.
			if (questionNumbers.remove("0")) {
				sb.append("hasHair(_) :- false .\n");
			}
			// Yumurtlayarak �rer
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_).\n");
			}
			// Yavrular�n� s�t ile beslemez
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_) :- false .\n");
			}
			// Di�leri yoktur
			if (questionNumbers.remove("7")) {
				sb.append("hasToothed(_) :- false .\n");
			}
			// Omurgal� bir hayvand�r.
			if (questionNumbers.remove("8")) {
				sb.append("hasBackbone(_).\n");
			}
			// Akci�erleri ile solunum yapar
			if (questionNumbers.remove("9")) {
				sb.append("hasBreathes(_).\n");
			}
			// Y�zge�lere sahip de�ildir.
			if (questionNumbers.remove("11")) {
				sb.append("hasFins(_) :- false .\n");
			}

		} // V�cudunda t�y yok ise bir ku� de�ildir.
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasFeather(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("1");
		}
		return sb.toString();
	}

	private String q2(String answer) {
		questionNumbers.remove("2");
		StringBuilder sb = new StringBuilder();

		// Yumurtlayarak �r�yorsa memeli de�ildir.
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasEggs(_).\n");
			// V�cudu k�llar ile kapl� de�ildir.
			if (questionNumbers.remove("0")) {
				sb.append("hasHair(_) :- false .\n");
			}
			// yavrular�n s�t ile beslemez
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_) :- false .\n");
			}

		} // Yumurtlayarak �remiyorsa
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasEggs(_) :- false .\n");
			// v�cudu t�yler ile kapl� de�ildir.
			if (questionNumbers.remove("1")) {
				sb.append("hasFeather(_) :- false .\n");
			}

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("2");
		}

		return sb.toString();
	}

	private String q3(String answer) {
		questionNumbers.remove("3");
		StringBuilder sb = new StringBuilder();

		// Yavrular�n� s�t ile besliyorsa memeli bir hayvand�r.
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasMilk(_).\n");

			// V�cudu t�y ile kapl� de�ildir.
			if (questionNumbers.remove("1")) {
				sb.append("hasFeather(_) :- false .\n");
			}
			// Yumurtlayarak �remez.
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_) :- false .\n");
			}
			// Di�lere sahiptir.
			if (questionNumbers.remove("7")) {
				sb.append("hasToothed(_).\n");
			}
			// Omurgal� bir hayvand�r.
			if (questionNumbers.remove("8")) {
				sb.append("hasBackbone(_).\n");
			}
			// Akci�erleri ile solunum yapar
			if (questionNumbers.remove("9")) {
				sb.append("hasBreathes(_).\n");
			}

		} // Yavrular�n� s�t ile beslemiyor ise Memeli bir hayvan de�ildir
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasMilk(_) :- false .\n");
			// V�cudu k�l ile kapl� de�ildir.
			if (questionNumbers.remove("0")) {
				sb.append("hasHair(_) :- false .\n");
			}

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("3");
		}
		return sb.toString();
	}

	private String q4(String answer) {
		questionNumbers.remove("4");
		StringBuilder sb = new StringBuilder();

		// U�abiliyor ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasAirborne(_).\n");
			// Y�zemez
			if (questionNumbers.remove("5")) {
				sb.append("hasAquatic(_) :- false .\n");
			}
			// Y�zge�lere sahip de�ildir.
			if (questionNumbers.remove("11")) {
				sb.append("hasFins(_) :- false .\n");
			}

		} // U�am�yor ise
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasAirborne(_) :- false .\n");
		} else {
			System.out.println("Write y or n");
			questionNumbers.add("4");
		}

		return sb.toString();
	}

	private String q5(String answer) {
		questionNumbers.remove("5");
		StringBuilder sb = new StringBuilder();

		// Y�zebiliyor ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasAquatic(_).\n");
			// V�cudu k�llar ile kapl� de�ildir
			if (questionNumbers.remove("0")) {
				sb.append("hasHair(_) :- false .\n");
			}
			// U�amaz
			if (questionNumbers.remove("4")) {
				sb.append("hasAirborne(_) :- false .\n");
			}

		} // Y�zemiyor ise
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasAquatic(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("5");
		}

		return sb.toString();
	}

	private String q6(String answer) {
		questionNumbers.remove("6");
		StringBuilder sb = new StringBuilder();

		// Et�il bir hayvan ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasPredator(_).\n");

		} // Et�il bir hayvan de�il ise
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasPredator(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("6");
		}

		return sb.toString();
	}

	private String q7(String answer) {
		questionNumbers.remove("7");
		StringBuilder sb = new StringBuilder();

		// Di�lere sahip ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasToothed(_).\n");

		} // Di�lere sahip de�il ise
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasToothed(_) :- false .\n");
			// Yumurtlayarak �rer
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_).\n");
			}
			// Yavrular�n� s�t ile beslemez
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_) :- false .\n");
			}
			// Y�zge�lere sahip de�ildir.
			if (questionNumbers.remove("11")) {
				sb.append("hasFins(_) :- false .\n");
			}

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("7");
		}

		return sb.toString();
	}

	private String q8(String answer) {
		questionNumbers.remove("8");
		StringBuilder sb = new StringBuilder();

		// Omurgal� bir hayvan ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasBackbone(_).\n");
		} // Omurgal� bir de�il hayvan ise
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasBackbone(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("8");
		}

		return sb.toString();
	}

	private String q9(String answer) {
		questionNumbers.remove("9");
		StringBuilder sb = new StringBuilder();

		// Akci�erleri ile nefes al�yor ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasBreathes(_).\n");

		} // Akci�erleri ile nefes alm�yor ise
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasBreathes(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("9");
		}

		return sb.toString();
	}

	private String q10(String answer) {
		questionNumbers.remove("10");
		StringBuilder sb = new StringBuilder();

		// Zehirli bir hayvan ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasVenomous(_).\n");

		} // Zehirli bir hayvan de�il ise
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasVenomous(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("10");
		}

		return sb.toString();
	}

	private String q11(String answer) {
		questionNumbers.remove("11");
		StringBuilder sb = new StringBuilder();

		// Y�zge�lere sahip ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasFins(_).\n");
			// Y�zebilir
			if (questionNumbers.remove("5")) {
				sb.append("hasAquatic(_).\n");
			}

		} // Y�zemiyor ise
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasFins(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("11");
		}

		return sb.toString();
	}

	private String q12(String answer) {
		questionNumbers.remove("12");
		StringBuilder sb = new StringBuilder();

		// Baca�� yok ise
		if (answer.equalsIgnoreCase("0")) {

			sb.append("hasLegs(0).\n");

		} // Baca�� var ise
		else {
			String tmp = "hasLegs(" + answer + ").\n";
			sb.append(tmp);

		}

		return sb.toString();
	}

	private String q13(String answer) {
		questionNumbers.remove("13");
		StringBuilder sb = new StringBuilder();

		// Kuyru�u var ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasTail(_).\n");

		} // Kuyru�u yok ise
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasTail(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("13");
		}

		return sb.toString();
	}

	private String q14(String answer) {
		questionNumbers.remove("14");
		StringBuilder sb = new StringBuilder();

		// Evcil bir hayvan ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasDomestic(_).\n");

		} // Evcil olmayan bir hayvan ise
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasDomestic(_) :- false .\n");

		} else {
			System.out.println("Write y or n");
			questionNumbers.add("14");
		}

		return sb.toString();
	}
}
