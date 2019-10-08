package projects.projet.nodes.timers;

import projects.projet.nodes.nodeImplementations.DFSNode;
import sinalgo.nodes.timers.Timer;

public class waitTimer extends Timer {	
	public void fire() {
		DFSNode n= (DFSNode) this.node;
		n.envoi();
	}

}
