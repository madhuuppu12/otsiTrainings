package org.array.sorting;

import java.util.Scanner;

public class Sort {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Size of Array");
		int size = sc.nextInt();
		int[] i = new int[size];
		System.out.println("Enter data");
		for (int j = 0; j < i.length; j++) {
			i[j] = sc.nextInt();
		}
		new Sort().makeSort(i); 
		for (int j = 0; j < i.length; j++) {
			System.out.println(i[j]);
		}
	}

	public void makeSort(int[] i) {
		for (int j = 0; j < i.length; j++) {
			for (int j2 = j + 1; j2 < i.length; j2++) {
				if (i[j] >= i[j2]) {
					int value = i[j];
					i[j] = i[j2];
					i[j2] = value;
				}
			}
		}
	}
}
