package com.markusvat.SimpelBan;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import Commands.markusvat.CommandKICK;
public class Main extends JavaPlugin{
	public void onEnable(){
		System.out.println("Enabel");
		getCommand("kick").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				// TODO Auto-generated method stub
				String s ="[Server] du wurdest gekickt [Grund]:";
				if (arg.length>=2){
				for (int i=1;i!=arg.length;i++){
					s=s+" "+arg[i];
				}
				}else if(arg.length==1){
					s="[Server] du wurdest gekickt";
				}else{
					sender.sendMessage("Bitte Spieler namen zum kicken eingeben");
				  return true;	
				}
				Player kick;
				try{kick=Bukkit.getPlayer(arg[0]);}catch(Exception e){sender.sendMessage("Spieler ist nciht online");return true;}
				if (kick!=null){
				return new CommandKICK(kick, s).run();
				}else{
					sender.sendMessage("Spieler nicht gefunden");
					return true;
				}
			}
		});
		getCommand("ban").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					sender.sendMessage("Spieler namen eingeben (exakt ohne groﬂ und klein schreibung), alle Spieler mit teilen des namen des gebannten werden gekickt, aber nur der gebannte selber wird gebannt");
					return true;
				}else if(arg.length==1){
					List<String> a = getConfig().getStringList("Player");
					boolean banned = false;
					for(String s : a){
						if (s.split("%20")[0].equalsIgnoreCase(arg[0])){
							banned=true;
						}
					}
					
					if(banned==false){
						a.add(arg[0]+"%20"+"standart");
						getConfig().set("Player", a);
						getConfig().options().copyDefaults(true);
						saveConfig();
						Bukkit.broadcastMessage(arg[0]+" wurde gebannt");
						Player ban;
						try {ban=Bukkit.getPlayer(arg[0]);} catch (Exception e) {return true;}
						if (ban != null&&ban.getName().equalsIgnoreCase(arg[0])){
						return new CommandKICK(ban.getPlayer(), "[SERVER]: Du wurdest gebannt").run();
						}else{
							sender.sendMessage("Spieler ist nicht online wurde aber gebannt");
						}
					}else{
						sender.sendMessage(arg[0]+" wurde schon gebannt");
					}
				}else if(arg.length>=2){
					
					String s="";
					for (int i=1;i!=arg.length;i++){
						s=s+arg[i];
					}
					List<String> a = getConfig().getStringList("Player");
					boolean banned = false;
					for(String s3 : a){
						if (s3.split("%20")[0].equalsIgnoreCase(arg[0])){
							banned=true;
						}
					}
					if (banned==false){
					a.add(arg[0]+"%20"+s);
					getConfig().set("Player", a);
					getConfig().options().copyDefaults(true);
					saveConfig();
					Player ban;
					try {ban=Bukkit.getPlayer(arg[0]);} catch (Exception e) {return true;}
					if (ban != null&&ban.getName().equalsIgnoreCase(arg[0])){
					return new CommandKICK(ban.getPlayer(), s).run();
					}else{
						String s1="";
						for (int i=1;i!=arg.length;i++){
							s1=s1+arg[i];
						}
						sender.sendMessage("Spieler "+arg[0]+" ist nicht online wurde aber gebannt Grund: "+s1);
					}
				}else{
					sender.sendMessage(arg[0]+" wurde schon gebannt");
				}
				}
				return true;
			}		
		});
		getCommand("unban").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					sender.sendMessage("Spieler namen eingeben");
					return true;
				}else if(arg.length==1){
					boolean unban=false;
					List<String> a = getConfig().getStringList("Player");
					try{
					for (int i = 0; i!=a.size();i++){			
						String s = a.get(i).split("%20")[0];
						if (s.equalsIgnoreCase(arg[0])){
							a.remove(i);
							Bukkit.broadcastMessage(s+" wurde entbannt");
							unban=true;
							break;
						}
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					if (unban==false){
						sender.sendMessage("Speiler "+arg[0]+" war nicht gebannt");
					}
					getConfig().set("Player", a);
					getConfig().options().copyDefaults(true);
					saveConfig();
				}else if(arg.length>=2){
				sender.sendMessage("Spieler "+arg[0]+"nicht gefunden");
				}
				return true;
			}
		});
		getCommand("c").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
			for (int i=0;i<30;i++){
				sender.sendMessage("");
			}
			return true;
			}
		});
		getCommand("getbanned").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					List<String> a = getConfig().getStringList("Player");
					if (a.size()==0){
						sender.sendMessage("keine Spieler wurden gebannt");
					}else {
						sender.sendMessage("Von diesem Server gebannte Spieler");
						for (String s : a){
							if (s.split("%20")[1].toString().equalsIgnoreCase("standart")){
								sender.sendMessage(s.split("%20")[0]);
							}else{
								sender.sendMessage(s.split("%20")[0]+" Grund: "+s.split("%20")[1]);
							}
						}
					}
				}else{
					sender.sendMessage("zu viele argumente");
				}
				return true;
			}
		});
	getServer().getPluginManager().registerEvents(new Listener() { @EventHandler
		public void onPlayerLoginEvent(PlayerLoginEvent p) {
		String name=p.getPlayer().getName();
		List<String> a = getConfig().getStringList("Player");
		for (int i = 0;i!=a.size();i++){
		String banned=a.get(i).split("%20")[0];
			if (banned.equalsIgnoreCase(name)){
				//a.remove(i);
				if (a.get(i).split("%20")[1].equalsIgnoreCase("standart")){
					p.setKickMessage("[Server]: du bist gebannt checks endlich");
					p.getPlayer().kickPlayer("[Server]: du bist gebannt checks endlich");
					p.setResult(Result.KICK_BANNED);
				}else{
					p.setKickMessage("[Server]: du bist gebannt [Grund]:"+a.get(i).split("%20")[1]);
					p.getPlayer().kickPlayer("[Server]: du bist gebannt [Grund]:"+a.get(i).split("%20")[1]);
					p.setResult(Result.KICK_BANNED);
				}
			}
		}
	}}, this);
}
}
