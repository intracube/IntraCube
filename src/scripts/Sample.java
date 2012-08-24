package scripts;

import java.awt.Graphics2D;

import org.intracube.api.manifest.ScriptManifest;
import org.intracube.script.Script;

@ScriptManifest(
		authors = {"author"},
		version = 1.0,
		description = "description",
		name = "name"
)

/**
 * Simple script template
 */
public class Sample extends Script{

	@Override
	public boolean onStart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int loop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
