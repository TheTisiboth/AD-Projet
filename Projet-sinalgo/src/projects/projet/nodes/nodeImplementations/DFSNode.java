package projects.projet.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;

import projects.projet.nodes.messages.DFSMessage;
import projects.projet.nodes.timers.initTimer;
import projects.projet.nodes.timers.waitTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.Tools;

public class DFSNode extends Node {
	private static int N = new Random().nextInt(15 + nbNoeuds()) + nbNoeuds();
	private Path path;
	private Path etatVoisins[];
	// ´etat du noeud initialis´e au hasard,
	// mais dans son domaine de d´efinition

	private boolean EstRacine() {
		return ID == 1;
	}

	private int getIndex(Node n) {
		int j = 0;
		for (Edge e : this.outgoingConnections) {
			if (e.endNode.ID == n.ID)
				return j;
			j++;
		}
		return 0;
	}

	// getVoisin retourne l’ID correspondant au num´ero ‘‘indice’’
	// si l’indice n’existe pas, on retourne l’identit´e du noeud
	private Node getVoisin(int indice) {
		if (indice >= this.nbVoisins() || indice < 0)
			return this;
		Iterator<Edge> iter = this.outgoingConnections.iterator();
		for (int j = 0; j < indice; j++)
			iter.next();
		return iter.next().endNode;
	}
	
	private int nbVoisins() {
		if (this.outgoingConnections == null)
			return 0;
		return this.outgoingConnections.size();
	}

	public void preStep() {
	}

	// ATTENTION lorsque init est appel´e,
	// les liens de communications n’existent pas encore
	// il faut attendre une unit´e de temps,
	// avant que les connections soient r´ealis´ees
	// nous utilisons donc un minuteur pour d´eclencher
	// la v´eritable initialisation ‘‘start’’ apr`es 1 unit´e de temps
	public void init() {
		(new initTimer()).startRelative(1, this);
	}

	// Un noeud envoie r´eguli`erement son ´etat `a ses voisins
	public void envoi() {
		
		// TODO pas faire broadcast, mais un send a chacun
		for (int i = 0; i < this.outgoingConnections.size(); i++) {
			DFSNode n = (DFSNode) getVoisin(i);
			send(new DFSMessage(path, i), n);
			
		} 
		// waitTimer appelle de nouveau envoi
		// dans 20 unit´es de temps
		(new waitTimer()).startRelative(20, this);
	}

	public void start() {
		path = new Path(N);
		(new waitTimer()).startRelative(20, this);
	}

	// handleMessages est appel´ee r´eguli`erement mˆeme si
	// aucun message n’a ´et´e rec¸u
	// la fonction ci-dessous g`ere la r´eception de messages
	// elle est appel´ee r´eguli`erement mˆeme si
	// aucun message n’a ´et´e rec¸u
	public void handleMessages(Inbox inbox) {

		// on parcourt la liste de tous les messages rec¸us
		while (inbox.hasNext()) {
			Message m = inbox.next();
			if (m instanceof DFSMessage) {
				DFSMessage msg = (DFSMessage) m;
				
			}
		}
		// l’´etat des voisins peut avoir chang´e
		// on recalcule donc l’´etat du noeud
	}

	// la couleur du noeud d´epend de son ´etat
	private Color Couleur() {

		return Color.yellow;

	}

	// affichage du noeud
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		this.setColor(this.Couleur());
		String text = "" + this.ID;
		super.drawNodeAsDiskWithText(g, pt, highlight, text, 20, Color.black);
	}

	public void neighborhoodChange() {
		if (this.nbVoisins() > 0) {
//				this.Voisins = new EtatVoisin[this.nbVoisins()];
//				for (int i = 0; i < this.nbVoisins(); i++)
//					this.Voisins[i] = new EtatVoisin();
		}
	}

	public void postStep() {
	}

	public void checkRequirements() throws WrongConfigurationException {
	}

	protected Node Successeur() {
		int succ = (this.ID + 1) % nbNoeuds();
		for (Edge e : this.outgoingConnections)
			if (e.endNode.ID == succ)
				return e.endNode;
		return null;
	}

	protected Node Predecesseur() {
		int succ = this.ID - 1;
		if (succ == 0) {
			succ = nbNoeuds();
		}
		for (Edge e : this.outgoingConnections)
			if (e.endNode.ID == succ)
				return e.endNode;
		return null;
	}

	protected static int nbNoeuds() {
		return Tools.getNodeList().size();
	}

}
