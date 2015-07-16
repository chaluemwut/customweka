package com.kku.lab.opt;

public class TestGreedy1 {

	public static void main(String[] args) {
		int amount = 60;
		int coin[] = { 20, 5, 1 };
		int num;
		for (int i = 0; i < coin.length; i++) {
			num = amount / coin[i];
			System.out.println(num + " $ " + coin[i]);
			amount -= num * coin[i];
		}
	}

}
