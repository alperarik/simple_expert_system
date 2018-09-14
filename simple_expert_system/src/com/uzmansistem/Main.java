package com.uzmansistem;

import GUI.MainFrame;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFrame;
import org.jpl7.Query;

/**
 *
 * @author AlperARIK
 */
public class Main {

	private static MainFrame frame = null;
	private static ArrayList<String> animalList;

	public static void main(String[] args) {

		createGUI();
		deleteFile();
		CreatePrologFile cpf = new CreatePrologFile();
		cpf.createPL();
		animalList = cpf.getAnimalList();

	}

	// Creates GUI
	private static void createGUI() {

		frame = new MainFrame();
		frame.setTitle("Expert System");
		frame.setVisible(true);
		// frame.setBounds(500, 200, 400, 300);
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(0, 153, 153));
	}

	// Runs consult and query operations on prolog and returns result
	public static ArrayList<String> runProlog() {

		// Path of .pl file
		String location = CreatePrologFile.path + "/Animal.pl";
		String t1 = "consult('" + location + "')";
		Query q1 = new Query(t1);
		System.out.println(t1 + " " + (q1.hasSolution() ? "succeeded" : "failed"));

		// Creating query
		String t2 = "animal(_, R).";
		Query q2 = new Query(t4);

		System.out.println("each solution of " + t2);
		ArrayList<String> result = new ArrayList();

		while (q2.hasMoreSolutions()) {
			Map s4 = q2.nextSolution();
			int index = Integer.parseInt(s4.get("R").toString());
			if (result.isEmpty()) {
				result.add(animalList.get(index).toUpperCase());
			} else {
				boolean check = result.contains(animalList.get(index));
				if (!check) {
					result.add(animalList.get(index).toUpperCase());
				}
			}
		}

		if (result.isEmpty()) {
			System.out.println("There is no matching animal!");
			result.add("There is no matching animal!");
			return result;
		}

		System.out.println();
		result.stream().forEach((s) -> {
			System.out.println(s.toUpperCase());
		});

		return result;
	}

	// Deletes previous .pl files
	public static void deleteFile() {
		try {
			File file = new File(CreatePrologFile.path + "/Animal.pl");

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
