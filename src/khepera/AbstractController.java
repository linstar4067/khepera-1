package khepera;

import java.util.ArrayList;
import java.util.Collections;

import khepera.behaviour.Behaviour;
import khepera.managers.MovementManager;
import khepera.managers.SensorManager;
import edu.wsu.KheperaSimulator.RobotController;

public abstract class AbstractController extends RobotController {
  protected SensorManager sensorManager;
  protected MovementManager movementManager;
  private long startTime;
  private ArrayList<Behaviour> behaviours;
  private boolean initialized = false;
  
  public AbstractController() {
    startTime = System.currentTimeMillis();
    sensorManager = new SensorManager(this);
    movementManager = new MovementManager(this);
//    sensorManager = SensorManager.getInstance(this); // this method is overloaded, so we may leave the "this" keyword after this call.
    behaviours = new ArrayList<Behaviour>();
    addBehaviours();
    new Thread(sensorManager).start();
    initialized = true;
  }

  @Override
  public void doWork() throws Exception {
	  if (!initialized) return;
	  updateStatus();
    runBehaviour();
  }
  
  /**
   * This method should use the addBehaviour() function to add all wanted behaviors
   */
  protected abstract void addBehaviours();
  
  protected void addBehaviour(Behaviour b) {
	  behaviours.add(b);
	  Collections.sort(behaviours);
  }
  
  private void runBehaviour() {
	  if (behaviours.size() == 0) {
		  Logger.getInstance().error("No behaviours added to the controller...");
	  } 
	  
	  for(Behaviour b : behaviours) {
		  if (b.shouldRun()) {
			  b.doWork();
		  }
	  }
  }
  
  @Override
  public void close() throws Exception {
	  this.sensorManager.close(); // Necessary for stopping the SensorManager.run thread.
  }

  private void updateStatus() {
    Logger.getInstance().setStatus("Time passed: " + Double.toString((System.currentTimeMillis() - startTime) / 1000.), 0);
    // Logger.getInstance().setStatus("Speed: " + movementManager.getSpeed(), 6);
    Logger.getInstance().setStatus(String.format("Location: (%d, %d)", movementManager.getCurrentLocation().x, movementManager.getCurrentLocation().y), 7);
    Logger.getInstance().setStatus("Direction radian: " + movementManager.getDirectionInRadians(), 9);
    Logger.getInstance().setStatus("Direction degrees: " + movementManager.getDirectionInDegrees(), 8);
    // Logger.getInstance().setStatus("Direction h or v: " +
    // Boolean.toString(approximately(movementManager.getDirectionInRadians() % (Math.PI / 2), 0.,
    // 0.1)), 8);
  }
}
