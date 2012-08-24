package org.intracube.api.elements;

import java.util.Random;


public class Calculations {

	public static int random(int min, int max){
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;

		return randomNum;
	}

	/////////////// Addition ///////////////////

	public int add(int...nums){
		int sum = 0;
		for (int i=0; i<nums.length; i++){
			sum += nums[i];
		}
		return sum;
	}

	public double addD(double...nums){
		double sum = 0.0;
		for (int i=0; i<nums.length; i++){
			sum += nums[i];
		}
		return sum;
	}

	/////////////// Multiply ///////////////////

	public int multiply(int...nums){
		int sum = 0;
		for (int i=0; i<nums.length; i++){
			sum *= nums[i];
		}
		return sum;
	}

	public double multiplyD(double...nums){
		double sum = 0.0;
		for (int i=0; i<nums.length; i++){
			sum += nums[i];
		}
		return sum;
	}

	/////////////// Average ///////////////////

	public int average(int...nums){
		int sum = 0;
		int length = nums.length;
		for (int i=0; i<nums.length; i++){
			sum += nums[i];
		}
		return (sum/length);
	}

	public double averageD(double...nums){
		double sum = 0.0;
		double length = nums.length;
		for (int i=0; i<nums.length; i++){
			sum += nums[i];
		}
		return (sum/length);
	}
}
