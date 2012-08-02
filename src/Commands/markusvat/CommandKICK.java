package Commands.markusvat;

import org.bukkit.entity.Player;

public class CommandKICK {
	Player p;
	String s;
	
	public CommandKICK(Player p, String s){
		this.p=p;
		this.s=s;
	}
	public boolean run(){
		p.kickPlayer(s);
		System.out.println("kicked");
		return true;
	}
}
