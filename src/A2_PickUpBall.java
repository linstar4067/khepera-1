import etc.AbstractController;

public class A2_PickUpBall extends AbstractController {

	boolean started = false;
	boolean done = false;

	@Override
	public void doWork() throws Exception {
		if (done)
			return;
		findBall();
	}

	private void pickUpBall() {
		setGripperState(GRIP_OPEN);
		sleep(500);
		setArmState(ARM_DOWN);
		sleep(500);
		setGripperState(GRIP_CLOSED);
		sleep(500);
		setArmState(ARM_UP);
		sleep(500);
	}

	private void findBall() {
		if (!closeToSomething())
			forward(20);
		else {
			System.out.println("found something!");
			rotateToward();
			while (getMaxSensorValue() < 900) {
				if (getAverageDistance(SENSOR_FRONTL) > 700 && getAverageDistance(SENSOR_FRONTR) > 700) {
					pickUpBall();
					returnToBase();
					break;
				}
				forward(100);
				rotateToward();
				sleep(1);
			}
			stop();
			sleep(500);
			pickUpBall();

			returnToBase();
		}
	}

	private void rotateToward() {
		while (!approximately(getAverageDistance(SENSOR_FRONTL), getAverageDistance(SENSOR_FRONTR), 10) || getAverageDistance(SENSOR_FRONTL) < 15
				|| getAverageDistance(SENSOR_FRONTR) < 15) {
			rotate(-1);
			sleep(1);
		}
	}

	private void returnToBase() {

		// return to base
		long distanceTraveled = getLeftWheelPosition();
		rotate(180);
		forward(distanceTraveled);
		rotate(180);
		done = true;
	}

	@Override
	public void close() throws Exception {
	}

}
