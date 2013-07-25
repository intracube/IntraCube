package org.intracube.api.elements;

import java.util.Random;


public class Calculations {

	/**
	 * returns a random integer between given min and max
	 * @param min int Min bound
	 * @param max int Max bound
	 * @return
	 */
	public int random(int min, int max){
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

	public double add(double...nums){
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

	public double multiply(double...nums){
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

	public double average(double...nums){
		double sum = 0.0;
		double length = nums.length;
		for (int i=0; i<nums.length; i++){
			sum += nums[i];
		}
		return (sum/length);
	}
}
