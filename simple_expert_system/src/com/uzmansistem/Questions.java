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
	private final String[] questions = new String[] { "Vücudu kýllar ile mi kaplý?", "Vücudu tüyler ile mi kaplý?",
			"Yumurtlayarak mý ürüyor?", "Yavrularýný süt ile mi besliyor?", "Uçabiliyor mu?",
			"Suda mý yaþýyor? (Aquatic)", "Etçil bir hayvan mý?", "Diþlere sahip mi?", "Omurgalý bir hayvan mý?",
			"Akciðerleri ile mi nefes alýyor? (Breathe)", "Zehirli bir hayvan mý?", "Yüzgeçlere sahip mi?",
			"Kaç adet bacaðý var?", "Kuyruðu var mý?", "Evcil bir hayvan mý (Domestic)?" };

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

		// Vücudu kýl kaplýysa memeli bir hayvandýr. (yunus hariç)
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasHair(_).\n");
			// Vücudu tüy ile kaplý deðildir.
			if (questionNumbers.remove("1")) {
				sb.append("hasFeather(_) :- false .\n");
			}
			// Yumurtlayarak üremez.
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_) :- false .\n");
			}
			// yavrularýn süt ile besler
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_).\n");
			}
			// Diþlere sahiiptir.
			if (questionNumbers.remove("7")) {
				sb.append("hasToothed(_).\n");
			}
			// Omurgalý bir hayvandýr.
			if (questionNumbers.remove("8")) {
				sb.append("hasBackbone(_).\n");
			}
			// Akciðerleri ile solunum yapar
			if (questionNumbers.remove("9")) {
				sb.append("hasBreathes(_).\n");
			}

		} // Vücudunda kýl yok ise Memeli bir hayvan deðildir
		else if (answer.equalsIgnoreCase("n")) {

			sb.append("hasHair(_) :- false .\n");
			// yavrularýn süt ile beslemez
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

		// Vücudu tüyler ile kaplýysa bir kuþtur.
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasFeather(_).\n");
			// Vücudu kýl ile kaplý deðildir.
			if (questionNumbers.remove("0")) {
				sb.append("hasHair(_) :- false .\n");
			}
			// Yumurtlayarak ürer
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_).\n");
			}
			// Yavrularýný süt ile beslemez
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_) :- false .\n");
			}
			// Diþleri yoktur
			if (questionNumbers.remove("7")) {
				sb.append("hasToothed(_) :- false .\n");
			}
			// Omurgalý bir hayvandýr.
			if (questionNumbers.remove("8")) {
				sb.append("hasBackbone(_).\n");
			}
			// Akciðerleri ile solunum yapar
			if (questionNumbers.remove("9")) {
				sb.append("hasBreathes(_).\n");
			}
			// Yüzgeçlere sahip deðildir.
			if (questionNumbers.remove("11")) {
				sb.append("hasFins(_) :- false .\n");
			}

		} // Vücudunda tüy yok ise bir kuþ deðildir.
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

		// Yumurtlayarak ürüyorsa memeli deðildir.
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasEggs(_).\n");
			// Vücudu kýllar ile kaplý deðildir.
			if (questionNumbers.remove("0")) {
				sb.append("hasHair(_) :- false .\n");
			}
			// yavrularýn süt ile beslemez
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_) :- false .\n");
			}

		} // Yumurtlayarak üremiyorsa
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasEggs(_) :- false .\n");
			// vücudu tüyler ile kaplý deðildir.
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

		// Yavrularýný süt ile besliyorsa memeli bir hayvandýr.
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasMilk(_).\n");

			// Vücudu tüy ile kaplý deðildir.
			if (questionNumbers.remove("1")) {
				sb.append("hasFeather(_) :- false .\n");
			}
			// Yumurtlayarak üremez.
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_) :- false .\n");
			}
			// Diþlere sahiptir.
			if (questionNumbers.remove("7")) {
				sb.append("hasToothed(_).\n");
			}
			// Omurgalý bir hayvandýr.
			if (questionNumbers.remove("8")) {
				sb.append("hasBackbone(_).\n");
			}
			// Akciðerleri ile solunum yapar
			if (questionNumbers.remove("9")) {
				sb.append("hasBreathes(_).\n");
			}

		} // Yavrularýný süt ile beslemiyor ise Memeli bir hayvan deðildir
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasMilk(_) :- false .\n");
			// Vücudu kýl ile kaplý deðildir.
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

		// Uçabiliyor ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasAirborne(_).\n");
			// Yüzemez
			if (questionNumbers.remove("5")) {
				sb.append("hasAquatic(_) :- false .\n");
			}
			// Yüzgeçlere sahip deðildir.
			if (questionNumbers.remove("11")) {
				sb.append("hasFins(_) :- false .\n");
			}

		} // Uçamýyor ise
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

		// Yüzebiliyor ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasAquatic(_).\n");
			// Vücudu kýllar ile kaplý deðildir
			if (questionNumbers.remove("0")) {
				sb.append("hasHair(_) :- false .\n");
			}
			// Uçamaz
			if (questionNumbers.remove("4")) {
				sb.append("hasAirborne(_) :- false .\n");
			}

		} // Yüzemiyor ise
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

		// Etçil bir hayvan ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasPredator(_).\n");

		} // Etçil bir hayvan deðil ise
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

		// Diþlere sahip ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasToothed(_).\n");

		} // Diþlere sahip deðil ise
		else if (answer.equalsIgnoreCase("n")) {
			sb.append("hasToothed(_) :- false .\n");
			// Yumurtlayarak ürer
			if (questionNumbers.remove("2")) {
				sb.append("hasEggs(_).\n");
			}
			// Yavrularýný süt ile beslemez
			if (questionNumbers.remove("3")) {
				sb.append("hasMilk(_) :- false .\n");
			}
			// Yüzgeçlere sahip deðildir.
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

		// Omurgalý bir hayvan ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasBackbone(_).\n");
		} // Omurgalý bir deðil hayvan ise
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

		// Akciðerleri ile nefes alýyor ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasBreathes(_).\n");

		} // Akciðerleri ile nefes almýyor ise
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

		} // Zehirli bir hayvan deðil ise
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

		// Yüzgeçlere sahip ise
		if (answer.equalsIgnoreCase("y")) {
			sb.append("hasFins(_).\n");
			// Yüzebilir
			if (questionNumbers.remove("5")) {
				sb.append("hasAquatic(_).\n");
			}

		} // Yüzemiyor ise
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

		// Bacaðý yok ise
		if (answer.equalsIgnoreCase("0")) {

			sb.append("hasLegs(0).\n");

		} // Bacaðý var ise
		else {
			String tmp = "hasLegs(" + answer + ").\n";
			sb.append(tmp);

		}

		return sb.toString();
	}

	private String q13(String answer) {
		questionNumbers.remove("13");
		StringBuilder sb = new StringBuilder();

		// Kuyruðu var ise
		if (answer.equalsIgnoreCase("y")) {

			sb.append("hasTail(_).\n");

		} // Kuyruðu yok ise
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
