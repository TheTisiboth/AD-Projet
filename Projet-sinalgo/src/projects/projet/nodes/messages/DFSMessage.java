package projects.projet.nodes.messages;

import projects.projet.nodes.nodeImplementations.Path;
import sinalgo.nodes.messages.Message;

public class DFSMessage extends Message {

	public Path path;

	public DFSMessage( Path path, int index){
		this.path = path.concat(index);
	}
	
	private DFSMessage( Path path){
		this.path = path;
	}

	@Override
	public Message clone() {
		// TODO Auto-generated method stub
		return new DFSMessage(path);
	}

}
