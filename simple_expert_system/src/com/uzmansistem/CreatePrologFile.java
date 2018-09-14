package com.uzmansistem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreatePrologFile {

	private ArrayList<String> animalList;
	public static String path;

	// features of animals
	private final String[] features = new String[] { "hasHair", "hasFeather", "hasEggs", "hasMilk", "hasAirborne",
			"hasAquatic", "hasPredator", "hasToothed", "hasBackbone", "hasBreathes", "hasVenomous", "hasFins",
			"hasLegs", "hasTail", "hasDomestic" };

	public CreatePrologFile() {

		animalList = new ArrayList<>();
		String currPath = this.getClass().getClassLoader().getResource("").getPath();
		String pathArr[] = currPath.split("/build/classes/");
		path = pathArr[0];
		System.out.println(currPath);
	}

	// Creates prolog facts and rules from animalList.txt dataset
	public void createPL() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {

			String currentLine;
			br = new BufferedReader(new FileReader(path + "/animalList.txt"));
			bw = new BufferedWriter(new FileWriter(path + "/Animal.pl"));

			while ((currentLine = br.readLine()) != null) {
				String[] attributes = currentLine.split(",");
				animalList.add(attributes[0]);

				bw.append(attributes[0] + "(X) :- ");

				for (int i = 1; i <= 12; i++) {
					if (attributes[i].equalsIgnoreCase("1")) {
						bw.append(features[i - 1] + "(X), ");
					} else {
						bw.append("not(" + features[i - 1] + "(X)), ");
					}
				}
				bw.append(features[12] + "(" + attributes[13] + "), ");

				for (int i = 14; i <= 14; i++) {
					if (attributes[i].equalsIgnoreCase("1")) {
						bw.append(features[i - 1] + "(X), ");
					} else {
						bw.append("not(" + features[i - 1] + "(X)), ");
					}
				}

				int i = 15;
				if (attributes[i].equalsIgnoreCase("1")) {
					bw.append(features[i - 1] + "(X).");
				} else {
					bw.append("not(" + features[i - 1] + "(X)).");
				}

				bw.newLine();
			}

			for (int j = 0; j < animalList.size(); j++) {
				bw.write("animal(X, R) :- " + animalList.get(j) + "(X), R is " + j + " .");
				bw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getAnimalList() {
		return animalList;
	}
}
