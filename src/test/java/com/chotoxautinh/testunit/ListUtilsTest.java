package com.chotoxautinh.testunit;

import java.util.*;

import com.chotoxautinh.game.model.HighScore;
import com.chotoxautinh.util.ListUtils;

public class ListUtilsTest {
	
	private static Scanner input;

	public static void main(String[] args) {
		ArrayList<HighScore> list = new ArrayList<HighScore>();
		HashMap<List<HighScore>,Integer> map = new HashMap<List<HighScore>,Integer>();
		for (int i = 0; i < 12; i++) {
			input = new Scanner(System.in);
			System.out.println("Nhap ho ten va diem nguoi choi thu " + i+1);
			System.out.println("     Ho ten: ");
			String name = input.next();
			System.out.println("     Diem: ");
			int score = input.nextInt();
			System.out.println("Danh sach 10 nguoi diem cao nhat la: ");
			HighScore player = new HighScore(name,score);
			map = ListUtils.insert(list, player);
			for (int j = 0; j < list.size(); j++) {
				System.out.println("Name: " + list.get(j).getName() + "   Score: " + list.get(j).getScore());
			}
			System.out.println("Phan tu vua duoc chen vao vi tri: " + map.get(list));
		}
	}
}
