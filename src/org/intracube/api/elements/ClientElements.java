package org.intracube.api.elements;

import org.intracube.api.input.InputResponder;
import org.intracube.api.sprite.Collision;
import org.intracube.client.*;
import org.intracube.config.Logger;

public interface ClientElements {
	static IntraCubeClient client = new IntraCubeClient();
	static Logger log = new Logger();
	static Calculations calc = new Calculations();
	static InputResponder input = new InputResponder();
	static Collision collision = new Collision();
}
