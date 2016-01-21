
package hector;

import java.io.File;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class Hector
{

	private EV3GyroSensor gyro;
	private SampleProvider angle;
	private float[] sample;

	public Hector()
	{

		this.gyro = new EV3GyroSensor(LocalEV3.get().getPort("S1"));
		this.angle = gyro.getAngleMode();
		this.sample = new float[angle.sampleSize()];

	}

	public void turn90Left() throws InterruptedException
	{

		reset();

		Motor.A.backward();
		Motor.D.forward();

		do
		{
			Thread.sleep(50);

		} while (getAngle() >= -3.0);

		stop();

	}

	public void turn90Right() throws InterruptedException
	{

		reset();

		Motor.D.backward();
		Motor.A.forward();

		do
		{

			Thread.sleep(50);

		} while (getAngle() <= 5.0);

		stop();

	}

	public void forward()
	{

		Motor.A.backward();
		Motor.D.backward();

	}

	public void backward()
	{

		Motor.A.forward();
		Motor.D.forward();

	}

	public void stop()
	{

		Motor.A.stop(true);
		Motor.D.stop();

	}

	public void talk() throws InterruptedException
	{

		File file = new File("./wall-e.wav");
		Sound.playSample(file,100);

	}

	private void reset() throws InterruptedException
	{

		gyro.reset();

		Thread.sleep(500);

		gyro.setCurrentMode(0);

	}

	private float getAngle()
	{

		angle.fetchSample(sample, 0);

		return sample[0];

	}

	private float getAverageAngle()
	{

		float average = 0;

		for (int i = 0; i < 10; ++i)
		{

			average = average + getAngle();

		}

		return (average / 10);

	}

}
