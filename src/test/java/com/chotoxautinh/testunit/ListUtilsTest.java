package com.chotoxautinh.testunit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.chotoxautinh.game.model.HighScore;
import com.chotoxautinh.util.ListUtils;

public class ListUtilsTest {

	private static Scanner input;

	public static void main(String[] args) {
		List<HighScore> list = new ArrayList<HighScore>();
		for (int i = 0; i < 12; i++) {
			input = new Scanner(System.in);
			System.out.println("Nhap ho ten va diem nguoi choi thu " + (i + 1));
			System.out.println("     Ho ten: ");
			String name = input.next();
			System.out.println("     Diem: ");
			int score = input.nextInt();
			HighScore player = new HighScore(name, score);
			System.out.println("Phan tu vua duoc chen vao vi tri: " + ListUtils.insert(list, player));
		}
		System.out.println("Danh sach diem cao nhat la: ");
		for (int j = 0; j < list.size(); j++) {
			System.out.println("Name: " + list.get(j).getName() + "   Score: " + list.get(j).getScore());
		}
	}
}
